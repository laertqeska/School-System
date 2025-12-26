package com.example.School_System.config;

import com.example.School_System.entities.Role;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles(){
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            roleRepository.findByName(roleName)
                    .or(()->{
                        roleRepository.save(new Role(roleName));
                        return java.util.Optional.empty();
                    });
        });
    }
}
