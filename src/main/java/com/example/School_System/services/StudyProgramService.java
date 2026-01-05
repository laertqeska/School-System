package com.example.School_System.services;

import com.example.School_System.dto.studyProgram.CreateStudyProgramRequest;
import com.example.School_System.dto.studyProgram.PaginatedStudyProgramResponse;
import com.example.School_System.dto.studyProgram.StudyProgramModel;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.DegreeLevel;
import com.example.School_System.repositories.DepartmentRepository;
import com.example.School_System.repositories.SchoolAdminRepository;
import com.example.School_System.repositories.StudyProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final DepartmentRepository departmentRepository;


    public StudyProgramService(StudyProgramRepository studyProgramRepository, SchoolAdminRepository schoolAdminRepository, DepartmentRepository departmentRepository) {
        this.studyProgramRepository = studyProgramRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.departmentRepository = departmentRepository;
    }

    public Long createStudyProgram(CreateStudyProgramRequest request,User dean){
        Long departmentId = request.getDepartmentId();
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new EntityNotFoundException("Department not found with id: " + departmentId));
        StudyProgram studyProgram = new StudyProgram(
                department,
                request.getName(),
                request.getDegreeLevel(),
                request.getDurationSemesters(),
                request.getTotalCredits()
        );
        studyProgram.setCreatedBy(dean);
        StudyProgram savedStudyProgram = studyProgramRepository.save(studyProgram);
        return savedStudyProgram.getId();
    }

    public PaginatedStudyProgramResponse getStudyProgramsForSchool(int page, int perPage, String studyProgramName, DegreeLevel degreeLevel, User user, Long departmentId){
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(user.getId()).orElseThrow(()->new EntityNotFoundException("School Admin not found with user id: " + user.getId()));
        Pageable pageable = PageRequest.of(page,perPage);
        Page<StudyProgram> studyProgramPage =  studyProgramRepository.getStudyProgramsForSchool(pageable,departmentId,studyProgramName,degreeLevel.toString());
        List<StudyProgramModel> studyPrograms = new ArrayList<>();
        for(StudyProgram studyProgram : studyProgramPage.getContent()){
            StudyProgramModel studyProgramModel = new StudyProgramModel(
                    studyProgram.getId(),
                    studyProgram.getName(),
                    studyProgram.getDegreeLevel().toString(),
                    studyProgram.getIsActive()
            );
            studyPrograms.add(studyProgramModel);
        }
        return new PaginatedStudyProgramResponse(
                studyPrograms,
                page,
                perPage,
                studyProgramPage.getTotalElements(),
                studyProgramPage.getTotalPages()
        );
    }

    public PaginatedStudyProgramResponse getStudyProgramsForFaculty(int page, int perPage, String studyProgramName, DegreeLevel degreeLevel, User loggedUser) {
        Faculty faculty = loggedUser.getFacultyOfDean();

        if (faculty == null) {
            throw new EntityNotFoundException("Faculty not found for user id: " + loggedUser.getId());
        }

        Pageable pageable = PageRequest.of(page, perPage);
        Page<StudyProgram> studyProgramPage = studyProgramRepository.getStudyProgramsForFaculty(
                pageable,
                faculty.getId(),
                studyProgramName,
                degreeLevel != null ? degreeLevel.toString() : null
        );

        List<StudyProgramModel> studyPrograms = new ArrayList<>();
        for (StudyProgram studyProgram : studyProgramPage.getContent()) {
            StudyProgramModel studyProgramModel = new StudyProgramModel(
                    studyProgram.getId(),
                    studyProgram.getName(),
                    studyProgram.getDegreeLevel().toString(),
                    studyProgram.getIsActive()
            );
            studyPrograms.add(studyProgramModel);
        }

        return new PaginatedStudyProgramResponse(
                studyPrograms,
                page,
                perPage,
                studyProgramPage.getTotalElements(),
                studyProgramPage.getTotalPages()
        );
    }



}
