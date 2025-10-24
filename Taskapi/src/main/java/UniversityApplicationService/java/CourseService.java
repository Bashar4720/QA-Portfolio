package UniversityApplicationService.java;

import UniversityApplication.Dto.CourseCreateDto;
import UniversityApplication.Dto.CourseDto;
import UniversityApplication.Dto.StudentDto;
import UniversityApplicationException.DuplicateResourceException;
import UniversityApplicationException.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {

    private final List<CourseDto> courses = new ArrayList<>();
    private Long nextId = 1L;

    private final StudentService studentService;
    private final InstructorService instructorService;

    public CourseService(StudentService studentService, InstructorService instructorService) {
        this.studentService = Objects.requireNonNull(studentService);
        this.instructorService = Objects.requireNonNull(instructorService);
    }

    // ======================= CRUD =======================

    public CourseDto createCourse(CourseCreateDto dto) {
        Objects.requireNonNull(dto, "CourseCreateDto must not be null");

        String code = dto.getCode();
        ensureCodeNotExists(code, null);

        Long instructorId = dto.getInstructorId();
        if (instructorId != null) {
            instructorService.getInstructorById(instructorId);
        }

        CourseDto c = new CourseDto();
        c.setId(nextId++);
        c.setName(dto.getName());
        c.setCode(code);
        c.setCredits(dto.getCredits());
        c.setDescription(dto.getDescription());
        c.setInstructorId(instructorId);

        courses.add(c);

        if (instructorId != null) {
            instructorService.addCourseToInstructor(instructorId, c.getId());
        }
        return c;
    }

    public List<CourseDto> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public CourseDto getCourseById(Long id) {
        return courses.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id=" + id));
    }

    public CourseDto updateCourse(Long id, CourseCreateDto dto) {
        Objects.requireNonNull(dto, "CourseCreateDto must not be null");
        CourseDto existing = getCourseById(id);

        ensureCodeNotExists(dto.getCode(), id);

        Long oldInstructorId = existing.getInstructorId();
        Long newInstructorId = dto.getInstructorId();

        if (newInstructorId != null) {
            instructorService.getInstructorById(newInstructorId); 
        }

        existing.setName(dto.getName());
        existing.setCode(dto.getCode());
        existing.setCredits(dto.getCredits());
        existing.setDescription(dto.getDescription());
        existing.setInstructorId(newInstructorId);

        if (!Objects.equals(oldInstructorId, newInstructorId)) {
            if (oldInstructorId != null) {
                instructorService.removeCourseFromInstructor(oldInstructorId, id);
            }
            if (newInstructorId != null) {
                instructorService.addCourseToInstructor(newInstructorId, id);
            }
        }
        return existing;
    }

    public void deleteCourse(Long id) {
        CourseDto c = getCourseById(id);

        for (Long studentId : new HashSet<>(c.getStudentIds())) {
            studentService.removeCourseFromStudent(studentId, id);
        }

        if (c.getInstructorId() != null) {
            instructorService.removeCourseFromInstructor(c.getInstructorId(), id);
        }

        boolean removed = courses.removeIf(cc -> Objects.equals(cc.getId(), id));
        if (!removed) {
            throw new ResourceNotFoundException("Course not found with id=" + id);
        }
    }

    // ======================= Search =======================

    public List<CourseDto> searchCourses(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllCourses();
        }
        String q = keyword.toLowerCase(Locale.ROOT);
        return courses.stream()
                .filter(c -> containsIgnoreCase(c.getName(), q)
                          || containsIgnoreCase(c.getCode(), q)
                          || containsIgnoreCase(c.getDescription(), q))
                .collect(Collectors.toList());
    }

    public List<CourseDto> getCoursesByCredits(Integer credits) {
        Objects.requireNonNull(credits, "credits must not be null");
        return courses.stream()
                .filter(c -> Objects.equals(c.getCredits(), credits))
                .collect(Collectors.toList());
    }

    public List<CourseDto> getCoursesByInstructor(Long instructorId) {
        Objects.requireNonNull(instructorId, "instructorId must not be null");
        return courses.stream()
                .filter(c -> Objects.equals(c.getInstructorId(), instructorId))
                .collect(Collectors.toList());
    }

    // ======================= Relationships =======================

    public CourseDto assignInstructor(Long courseId, Long instructorId) {
        CourseDto c = getCourseById(courseId);
        Long oldInstructorId = c.getInstructorId();

        instructorService.getInstructorById(instructorId);

        if (oldInstructorId != null) {
            instructorService.removeCourseFromInstructor(oldInstructorId, courseId);
        }
        
        instructorService.addCourseToInstructor(instructorId, courseId);
        c.setInstructorId(instructorId);
        return c;
    }

    public void enrollStudent(Long courseId, Long studentId) {
        CourseDto c = getCourseById(courseId);
        StudentDto s = studentService.getStudentById(studentId);

        if (c.getStudentIds().contains(studentId)) {
            throw new DuplicateResourceException("Student " + studentId + " already enrolled in course " + courseId);
        }
        c.getStudentIds().add(studentId);
        studentService.addCourseToStudent(studentId, courseId);
    }

    public void unenrollStudent(Long courseId, Long studentId) {
        CourseDto c = getCourseById(courseId);
        c.getStudentIds().remove(studentId);
        studentService.removeCourseFromStudent(studentId, courseId);
    }

    public List<StudentDto> getCourseStudents(Long courseId) {
        CourseDto c = getCourseById(courseId);
        List<StudentDto> result = new ArrayList<>();
        for (Long sid : c.getStudentIds()) {
            result.add(studentService.getStudentById(sid));
        }
        return result;
    }

    // ======================= helpers =======================

    private void ensureCodeNotExists(String code, Long excludingCourseId) {
        boolean exists = courses.stream()
                .anyMatch(c -> c.getCode() != null
                             && c.getCode().equalsIgnoreCase(code)
                             && (excludingCourseId == null || !Objects.equals(c.getId(), excludingCourseId)));
        if (exists) {
            throw new DuplicateResourceException("Course code already exists: " + code);
        }
    }

    private static boolean containsIgnoreCase(String value, String qLower) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(qLower);
    }
}
