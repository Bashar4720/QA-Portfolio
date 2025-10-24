package UniversityApplication.Dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class CourseDto {

    private Long id;
    private String name;
    private String code;
    private Integer credits;
    private String description;
    private Long instructorId;
    private Set<Long> studentIds = new HashSet<>();
    
    public CourseDto() {
    }

    public CourseDto(Long id, String name, String code, Integer credits, String description, Long instructorId, Set<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.description = description;
        this.instructorId = instructorId;
        if (studentIds != null) {
            this.studentIds = new HashSet<>(studentIds);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = (studentIds == null) ? new HashSet<>() : new HashSet<>(studentIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(id, courseDto.id) &&
               Objects.equals(name, courseDto.name) &&
               Objects.equals(code, courseDto.code) &&
               Objects.equals(credits, courseDto.credits) &&
               Objects.equals(description, courseDto.description) &&
               Objects.equals(instructorId, courseDto.instructorId) &&
               Objects.equals(studentIds, courseDto.studentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, credits, description, instructorId, studentIds);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", code='" + code + '\'' +
               ", credits=" + credits +
               ", description='" + description + '\'' +
               ", instructorId=" + instructorId +
               ", studentIds=" + studentIds +
               '}';
    }
}
