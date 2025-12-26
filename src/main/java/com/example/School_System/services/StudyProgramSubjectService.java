package com.example.School_System.services;

import com.example.School_System.dto.mappers.TeacherMapper;
import com.example.School_System.dto.studyProgramSubject.CreateStudyProgramSubjectRequest;
import com.example.School_System.dto.studyProgramSubject.TeacherStudyProgramSubjectModel;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StudyProgramSubjectService {
    private final SubjectRepository subjectRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudyProgramSubjectService(SubjectRepository subjectRepository, StudyProgramRepository studyProgramRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Long createStudyProgramSubject(Long studyProgramId, CreateStudyProgramSubjectRequest request){
        StudyProgram studyProgram = studyProgramRepository.findById(studyProgramId).orElseThrow(()->new EntityNotFoundException("Study Program not found for id: " + studyProgramId));
        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(()-> new EntityNotFoundException("Subject not found for id: " + request.getSubjectId()));
        StudyProgramSubject studyProgramSubject = new StudyProgramSubject(
                studyProgram,
                subject,
                request.getCredits(),
                request.getSemester(),
                request.getYearLevel(),
                request.getPrerequisites(),
                request.isActive()
        );
        StudyProgramSubject savedStudyProgramSubject = studyProgramSubjectRepository.save(studyProgramSubject);
        return savedStudyProgramSubject.getId();
    }

    public List<TeacherStudyProgramSubjectModel> getStudyProgramSubjectsForTeacher(Long userId){
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with userId: " + userId));

        Set<TeacherSubject> teacherSubjects = teacher.getTeacherSubjects();
        List<TeacherStudyProgramSubjectModel> subjectsModel = new ArrayList<>();
        for(TeacherSubject teacherSubject : teacherSubjects){
            String studyProgramSubjectName = TeacherMapper.getFullStudyProgramSubjectName(teacherSubject.getStudyProgramSubject());
            TeacherStudyProgramSubjectModel subjectModel = new TeacherStudyProgramSubjectModel(
                    teacherSubject.getStudyProgramSubject().getId(),
                    studyProgramSubjectName
            );
            subjectsModel.add(subjectModel);
        }
        return subjectsModel;
    }
}
