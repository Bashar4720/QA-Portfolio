package UniversityApplication.Dto;

import jakarta.validation.constraints.*;


public class StudentCreateDto {

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(16)
    @Max(120)
    private Integer age;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "4.0", inclusive = true)
    private Double gpa;
    

    public StudentCreateDto() {
    }
    
    public StudentCreateDto(String firstName, String lastName, String email, Integer age, Double gpa) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.age = age;
    	this.gpa = gpa;
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
    	}