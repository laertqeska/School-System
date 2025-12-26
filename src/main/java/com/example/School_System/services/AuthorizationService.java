package com.example.School_System.services;

import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.exceptions.UnauthorizedException;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final RoleRepository roleRepository;

    public AuthorizationService(StudentRepository studentRepository, TeacherRepository teacherRepository, TeacherSubjectRepository teacherSubjectRepository, SchoolAdminRepository schoolAdminRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.roleRepository = roleRepository;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new UnauthorizedException("No authentication user found!");
        }
        Object principal = authentication.getPrincipal();
        if(!(principal instanceof User)){
            throw new UnauthorizedException("Invalid authentication principal!");
        }
        return (User) principal;
    }


    public void validateTeacherAccessToStudent(Authentication auth, Long studentId){
        User user = (User) auth.getPrincipal();
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher with userId: " + user.getId() + " not found!"));
        boolean hasAccess = teacherSubjectRepository.existsByTeacherIdAndStudentId(teacher.getId(),studentId);
        if(!hasAccess){
            throw new AccessDeniedException("You don't have access to this student!");
        }
    }

    public void validateAdminAccessToStudent(Authentication auth, Long studentId) {
        User user = (User) auth.getPrincipal();

        if(isSuperAdmin(auth)) return;

        SchoolAdmin admin = schoolAdminRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (!student.getSchool().getId().equals(admin.getSchool().getId())) {
            throw new AccessDeniedException("You don't have access to this student");
        }
    }

    public void validateAdminAccessToTeacher(Authentication auth,Long teacherId){
        User user = (User) auth.getPrincipal();

        if(isSuperAdmin(auth)) return;

        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("School admin with userId: " + user.getId() + " does not exist!"));

        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher not found with id: " + teacherId));

        if(!schoolAdmin.getSchool().getId().equals(teacher.getSchool().getId())){
            throw new AccessDeniedException("You don't have access to this teacher!");
        }
    }

    public void applyRoleToUser(RoleName roleName,User user){
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(()->new EntityNotFoundException("Role not found with name: " + roleName));
        user.getRoles().add(role);
    }

    private boolean isSuperAdmin(Authentication auth){
        return auth.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN"));
    }
}
