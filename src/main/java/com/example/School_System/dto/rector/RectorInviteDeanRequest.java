package com.example.School_System.dto.rector;

import jakarta.validation.constraints.NotNull;

public class RectorInviteDeanRequest {
    @NotNull
    private Long facultyId;
    @NotNull
    private String deanEmail;

    @NotNull
    private String deanFullName;

    public String getDeanEmail(){
        return deanEmail;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public void setDeanEmail(String deanEmail) {
        this.deanEmail = deanEmail;
    }

    public String getDeanFullName() {
        return deanFullName;
    }

    public void setDeanFullName(String deanFullName) {
        this.deanFullName = deanFullName;
    }
}
