package UniversityApplication.Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InstructorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Double salary;
    private List<Long> courseIds = new ArrayList<>();
    
    public InstructorDto() {}


    public InstructorDto(Long id, String firstName, String lastName, String email, String department, Double salary, List<Long> courseIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.salary = salary;
        if (courseIds != null) {
            this.courseIds = new ArrayList<>(courseIds);
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = (courseIds == null) ? new ArrayList<>() : new ArrayList<>(courseIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorDto that = (InstructorDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(firstName, that.firstName) &&
               Objects.equals(lastName, that.lastName) &&
               Objects.equals(email, that.email) &&
               Objects.equals(department, that.department) &&
               Objects.equals(salary, that.salary) &&
               Objects.equals(courseIds, that.courseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, department, salary, courseIds);
    }

    @Override
    public String toString() {
        return "InstructorDto{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", department='" + department + '\'' +
               ", salary=" + salary +
               ", courseIds=" + courseIds +
               '}';
    }
}
