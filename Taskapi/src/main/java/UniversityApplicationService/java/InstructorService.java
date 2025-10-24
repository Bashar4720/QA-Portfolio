package UniversityApplicationService.java;

import UniversityApplication.Dto.InstructorCreateDto;
import UniversityApplication.Dto.InstructorDto;
import UniversityApplicationException.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class InstructorService {

    private final List<InstructorDto> instructors = new ArrayList<>();
    private Long nextId = 1L;

    // ======================= CRUD =======================

    public InstructorDto createInstructor(InstructorCreateDto dto) {
        Objects.requireNonNull(dto, "InstructorCreateDto must not be null");

        InstructorDto i = new InstructorDto();
        i.setId(nextId++);
        i.setFirstName(dto.getFirstName());
        i.setLastName(dto.getLastName());
        i.setEmail(dto.getEmail());
        i.setDepartment(dto.getDepartment());
        i.setSalary(dto.getSalary());

        instructors.add(i);
        return i;
    }

    public List<InstructorDto> getAllInstructors() {
        return new ArrayList<>(instructors);
    }

    public InstructorDto getInstructorById(Long id) {
        return instructors.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id=" + id));
    }

    public InstructorDto updateInstructor(Long id, InstructorCreateDto dto) {
        Objects.requireNonNull(dto, "InstructorCreateDto must not be null");
        InstructorDto existing = getInstructorById(id);

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setSalary(dto.getSalary());
        return existing;
    }

    public void deleteInstructor(Long id) {
        boolean removed = instructors.removeIf(i -> Objects.equals(i.getId(), id));
        if (!removed) {
            throw new ResourceNotFoundException("Instructor not found with id=" + id);
        }
    }

    // ======================= Queries =======================

    public List<InstructorDto> getInstructorsByDepartment(String department) {
        if (department == null || department.isBlank()) {
            return getAllInstructors();
        }
        String d = department.trim().toLowerCase(Locale.ROOT);
        return instructors.stream()
                .filter(i -> i.getDepartment() != null && i.getDepartment().trim().toLowerCase(Locale.ROOT).equals(d))
                .collect(Collectors.toList());
    }

    public List<InstructorDto> getInstructorsByMinSalary(Double salary) {
        Objects.requireNonNull(salary, "salary must not be null");
        return instructors.stream()
                .filter(i -> i.getSalary() != null && i.getSalary() >= salary)
                .collect(Collectors.toList());
    }

    // =================== Relationship helpers ===================

    public void addCourseToInstructor(Long instructorId, Long courseId) {
        InstructorDto i = getInstructorById(instructorId);
        List<Long> list = i.getCourseIds();
        if (!list.contains(courseId)) {
            list.add(courseId);
        }
    }

    public void removeCourseFromInstructor(Long instructorId, Long courseId) {
        InstructorDto i = getInstructorById(instructorId);
        i.getCourseIds().remove(courseId);
    }
}
