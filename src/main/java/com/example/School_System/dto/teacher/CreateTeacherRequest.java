package com.example.School_System.dto.teacher;

import com.example.School_System.entities.valueObjects.AcademicTitle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class CreateTeacherRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    @NotNull(message = "Academic title is required")
    private AcademicTitle academicTitle;

    @Size(max = 255, message = "Qualification must not exceed 255 characters")
    private String qualification;

    @Size(max = 50, message = "Employee ID must not exceed 50 characters")
    private String employeeId;

    private Boolean isActive = true;

    private Long departmentId;

    @NotNull(message = "Subject IDs are required")
    @Size(min = 1, message = "At least one subject must be assigned")
    private List<@Valid SubjectAssignment> subjectAssignments;

    public CreateTeacherRequest() {
    }

    public CreateTeacherRequest(String username, String email, String password, String firstName, String lastName, String phone, AcademicTitle academicTitle, String qualification, String employeeId, Boolean isActive, Long departmentId, List<SubjectAssignment> subjectAssignments) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.academicTitle = academicTitle;
        this.qualification = qualification;
        this.employeeId = employeeId;
        this.isActive = isActive;
        this.departmentId = departmentId;
        this.subjectAssignments = subjectAssignments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<SubjectAssignment> getSubjectAssignments() {
        return subjectAssignments;
    }

    public void setSubjectAssignments(List<SubjectAssignment> subjectAssignments) {
        this.subjectAssignments = subjectAssignments;
    }
}
