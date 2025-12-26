package com.example.School_System.dto.mappers;

import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.entities.StudyProgramSubject;
import com.example.School_System.entities.Teacher;

public class TeacherMapper {

    public static void updateTeacherFromRequest(Teacher teacher, UpdateTeacherRequest request) {
        if (request.getFirstName() != null) {
            teacher.getUser().setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            teacher.getUser().setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            teacher.getUser().setEmail(request.getEmail());
        }

        if (request.getEmployeeId() != null) {
            teacher.setEmployeeId(request.getEmployeeId());
        }

        if (request.getAcademicTitle() != null) {
            teacher.setAcademicTitle(request.getAcademicTitle());
        }

        if (request.getQualification() != null) {
            teacher.setQualification(request.getQualification());
        }
    }

    public static String getFullStudyProgramSubjectName(StudyProgramSubject sps) {
        if (sps == null || sps.getSubject() == null || sps.getStudyProgram() == null) {
            throw new IllegalArgumentException("Invalid StudyProgramSubject: null subject or study program.");
        }

        String subjectName = sps.getSubject().getName();
        String programName = sps.getStudyProgram().getName();
        String degreeLevel = sps.getStudyProgram().getDegreeLevel().toString();

        return String.format("%s – %s (%s)", programName, subjectName, degreeLevel);
    }

}
