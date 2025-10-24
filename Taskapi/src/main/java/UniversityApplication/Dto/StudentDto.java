package UniversityApplication.Dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Double gpa;
    private Set<Long> courseIds = new HashSet<>();

    public StudentDto() {
        this.courseIds = new HashSet<>();
    }

    public StudentDto(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      Integer age,
                      Double gpa,
                      Set<Long> courseIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        if (courseIds != null) {
            this.courseIds = new HashSet<>(courseIds);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Set<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(Set<Long> courseIds) {
        this.courseIds = (courseIds == null) ? new HashSet<>() : new HashSet<>(courseIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(firstName, that.firstName) &&
               Objects.equals(lastName, that.lastName) &&
               Objects.equals(email, that.email) &&
               Objects.equals(age, that.age) &&
               Objects.equals(gpa, that.gpa) &&
               Objects.equals(courseIds, that.courseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, age, gpa, courseIds);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", age=" + age +
               ", gpa=" + gpa +
               ", courseIds=" + courseIds +
               '}';
    }
}
