package UniversityApplication.Dto;

import jakarta.validation.constraints.*;

public class CourseCreateDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 10)
    private String code;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer credits;

    @Size(max = 500)
    private String description;

    private Long instructorId; 

    public CourseCreateDto() {}

    public CourseCreateDto(String name, String code, Integer credits, String description, Long instructorId) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.description = description;
        this.instructorId = instructorId;
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
}
