package com.example.School_System.dto.schoolAdmin;

public class SchoolAdminDetailsResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String schoolName;

    public SchoolAdminDetailsResponse(String firstname, String lastname, String email, String phone, String schoolName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.schoolName = schoolName;
    }

}
