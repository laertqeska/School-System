package com.example.School_System.dto.mappers;

import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.entities.Student;

public class StudentMapper {
    public static void updateStudentFromRequest(Student student, UpdateStudentRequest request) {
        if (request.getStudentId() != null) {
            student.setStudentId(request.getStudentId());
        }

        if (request.getEnrollmentDate() != null) {
            student.setEnrollmentDate(request.getEnrollmentDate());
        }

        if (request.getCurrentYear() != null) {
            student.setCurrentYear(request.getCurrentYear());
        }

        if (request.getCurrentSemester() != null) {
            student.setCurrentSemester(request.getCurrentSemester());
        }

        if (request.getStatus() != null) {
            student.setStatus(request.getStatus());
        }

        if (request.getPersonalNumber() != null) {
            student.setPersonalNumber(request.getPersonalNumber());
        }

        if (request.getDateOfBirth() != null) {
            student.setDateOfBirth(request.getDateOfBirth());
        }

        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }

        if (request.getAddress() != null) {
            student.setAddress(request.getAddress());
        }

        if (request.getFirstName() != null) {
            student.getUser().setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            student.getUser().setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            student.getUser().setPhone(request.getPhone());
        }
    }

}
