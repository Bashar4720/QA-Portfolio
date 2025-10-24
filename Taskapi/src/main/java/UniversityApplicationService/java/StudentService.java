package UniversityApplicationService.java;

import UniversityApplication.Dto.StudentCreateDto;
import UniversityApplication.Dto.StudentDto;
import UniversityApplicationException.DuplicateResourceException;
import UniversityApplicationException.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private final List<StudentDto> students = new ArrayList<>();
    private Long nextId = 1L;

    /**
     * Create a new student from the provided DTO after ensuring email uniqueness.
     */
    public StudentDto createStudent(StudentCreateDto dto) {
        Objects.requireNonNull(dto, "StudentCreateDto must not be null");
        String email = normalize(dto.getEmail());
        ensureEmailNotExists(email, null);

        StudentDto student = new StudentDto();
        student.setId(nextId++);
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(email);
        student.setAge(dto.getAge());
        student.setGpa(dto.getGpa());

        students.add(student);
        return student;
    }

    public List<StudentDto> getAllStudents() {
        return new ArrayList<>(students);
    }

    public StudentDto getStudentById(Long id) {
        return students.stream()
                .filter(s -> Objects.equals(s.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id=" + id));
    }

    public StudentDto updateStudent(Long id, StudentCreateDto dto) {
        Objects.requireNonNull(dto, "StudentCreateDto must not be null");
        StudentDto existing = getStudentById(id);

        String email = normalize(dto.getEmail());
        ensureEmailNotExists(email, id);

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(email);
        existing.setAge(dto.getAge());
        existing.setGpa(dto.getGpa());
        return existing;
    }

    public void deleteStudent(Long id) {
        boolean removed = students.removeIf(s -> Objects.equals(s.getId(), id));
        if (!removed) {
            throw new ResourceNotFoundException("Student not found with id=" + id);
        }
    }

    public List<StudentDto> searchStudents(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllStudents();
        }
        String q = keyword.toLowerCase(Locale.ROOT);
        return students.stream()
                .filter(s -> containsIgnoreCase(s.getFirstName(), q)
                          || containsIgnoreCase(s.getLastName(), q)
                          || containsIgnoreCase(s.getEmail(), q))
                .collect(Collectors.toList());
    }

    public List<StudentDto> getStudentsByMinGpa(Double gpa) {
        Objects.requireNonNull(gpa, "gpa must not be null");
        return students.stream()
                .filter(s -> s.getGpa() != null && s.getGpa() >= gpa)
                .collect(Collectors.toList());
    }

    public void addCourseToStudent(Long studentId, Long courseId) {
        StudentDto s = getStudentById(studentId);
        s.getCourseIds().add(courseId);
    }

    public void removeCourseFromStudent(Long studentId, Long courseId) {
        StudentDto s = getStudentById(studentId);
        s.getCourseIds().remove(courseId);
    }

    public Set<Long> getStudentCourseIds(Long studentId) {
        StudentDto s = getStudentById(studentId);
        return new HashSet<>(s.getCourseIds());
    }

    private void ensureEmailNotExists(String email, Long excludingId) {
        boolean exists = students.stream()
                .anyMatch(s -> s.getEmail() != null
                             && s.getEmail().equalsIgnoreCase(email)
                             && (excludingId == null || !Objects.equals(s.getId(), excludingId)));
        if (exists) {
            throw new DuplicateResourceException("Email already exists: " + email);
        }
    }

    private static boolean containsIgnoreCase(String value, String qLower) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(qLower);
    }

    private static String normalize(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }
}
