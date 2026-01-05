package com.example.School_System.services;

import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {
    public School resolveSchool(User user){
        if(user.isRector()) return user.getRectorOfSchool();
        if(user.isSchoolAdmin()) return user.getSchoolAdmin().getSchool();
        if(user.isDean()) return user.getFacultyOfDean().getSchool();
        if(user.isTeacher()) return user.getTeacher().getSchool();
        if(user.isStudent()) return user.getStudent().getSchool();
        throw new IllegalStateException("User has no school!");
    }
}
