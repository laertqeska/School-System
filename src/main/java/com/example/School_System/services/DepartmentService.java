package com.example.School_System.services;

import com.example.School_System.dto.department.CreateDepartmentRequest;
import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolContextService schoolContextService;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final RoleRepository roleRepository;

    public DepartmentService(DepartmentRepository departmentRepository, TeacherRepository teacherRepository, SchoolContextService schoolContextService, StudyProgramRepository studyProgramRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, RoleRepository roleRepository){
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.schoolContextService = schoolContextService;
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.roleRepository = roleRepository;
    }

    public Long createDepartment(User dean, CreateDepartmentRequest request){
        Faculty faculty = dean.getFacultyOfDean();

        boolean departmentAlreadyExists = departmentRepository.existsByFacultyIdAndName(faculty.getId(), request.getName());
        if(departmentAlreadyExists){
            throw new IllegalStateException("Department with this name already exists in this faculty!");
        }

        Department department = new Department(
                faculty,request.getName()
        );

        department.setCreatedBy(dean);

        Department savedDepartment = departmentRepository.save(department);

        return savedDepartment.getId();
    }

    public List<DepartmentModelResponse> getDepartments(User user){
        Long facultyId = user.getFacultyOfDean().getId();
        return departmentRepository.findDepartmentModelsByFacultyId(facultyId);
    }

    @Transactional
    public void deleteDepartment(User loggedUser,Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));

        if(department.getDeleted()){
            throw new IllegalStateException("Faculty is already deleted!");
        }
        if(department.getFaculty().getDeleted()){
            throw new IllegalStateException("Cannot delete department because faculty is deleted!");
        }

        School school = schoolContextService.resolveSchool(loggedUser);
        if(loggedUser.isSchoolAdmin() && !department.getFaculty().getSchool().getId().equals(school.getId())){
            throw new AccessDeniedException("You do not have permission to delete this department!");
        }
        if(loggedUser.isDean() && !department.getFaculty().getDean().getId().equals(loggedUser.getId())){
            throw new AccessDeniedException("You do not have permission to delete this department!");
        }

        department.delete(loggedUser);
        departmentRepository.save(department);
        List<StudyProgram> departmentStudyPrograms = studyProgramRepository.findByDepartmentId(departmentId);
        List<StudyProgramSubject> departmentSubjects = studyProgramSubjectRepository.findStudyProgramSubjectByDepartment(departmentId);

        for(StudyProgram sp : departmentStudyPrograms){
           sp.delete(loggedUser);
        }
        for(StudyProgramSubject sps : departmentSubjects){
            sps.delete(loggedUser);
        }
    }

    @Transactional
    public void assignDepartmentHead(User loggedUser,Long departmentId,Long teacherId){
        School school = schoolContextService.resolveSchool(loggedUser);
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId)
                .orElseThrow(()-> new EntityNotFoundException("Department not found for id: " + departmentId));
        if(!department.getFaculty().getDean().getId().equals(loggedUser.getId())){
            throw new AccessDeniedException("You do not have access to this department");
        }
        Teacher teacher = teacherRepository.findByIdAndIsDeletedFalse(teacherId)
                .orElseThrow(()->new EntityNotFoundException("Teacher not found for id: " + teacherId));
        if(!teacher.getDepartment().getId().equals(departmentId)){
            throw new IllegalStateException("Teacher does not belong to department");
        }
        Teacher previousHead = department.getDepartmentHead();
        if(previousHead != null){
            removePreviousHead(department,previousHead);
        }

        department.setDepartmentHead(teacher);
        departmentRepository.save(department);
        addDepartmentHeadRole(teacher);
    }

    public void addDepartmentHeadRole(Teacher teacher){
        User user = teacher.getUser();
        if(user.hasRole(RoleName.DEPARTMENT_HEAD)){
            return;
        }

        Role role = roleRepository.findByName(RoleName.DEPARTMENT_HEAD)
                .orElseThrow(()-> new EntityNotFoundException("Role not found for name"));
        user.getRoles().add(role);
    }

    public void removePreviousHead(Department department,Teacher currentHead){
        if(department.getDepartmentHead() == null){
            return;
        }
        department.setDepartmentHead(null);
        currentHead.setHeadOfDepartment(null);
        User user = currentHead.getUser();
        user.getRoles().removeIf(role->role.getName() == RoleName.DEPARTMENT_HEAD);
    }

}
