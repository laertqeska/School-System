package com.example.School_System.services;

import com.example.School_System.dto.department.CreateDepartmentRequest;
import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.Department;
import com.example.School_System.entities.Faculty;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.DepartmentRepository;
import com.example.School_System.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;

    public DepartmentService(DepartmentRepository departmentRepository, TeacherRepository teacherRepository){
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Long createDepartment(User loggedUser, CreateDepartmentRequest request){
        Faculty faculty = loggedUser.getFacultyOfDean();

        Department department = new Department(
                faculty,request.getName()
        );

        Department savedDepartment = departmentRepository.save(department);

        return savedDepartment.getId();
    }

    public List<DepartmentModelResponse> getDepartments(User user){
        Long facultyId = user.getFacultyOfDean().getId();
        return departmentRepository.findByFacultyId(facultyId);
    }
}
