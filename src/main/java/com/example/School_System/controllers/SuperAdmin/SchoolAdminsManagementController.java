package com.example.School_System.controllers.SuperAdmin;

import com.example.School_System.dto.schoolAdmin.CreateSchoolAdminRequest;
import com.example.School_System.dto.schoolAdmin.PaginatedSchoolAdminResponse;
import com.example.School_System.dto.schoolAdmin.SchoolAdminDetailsResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SchoolAdminCreationService;
import com.example.School_System.services.SchoolAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/super-admin/admins")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SchoolAdminsManagementController {
    @Autowired
    private SchoolAdminCreationService schoolAdminCreationService;

    @Autowired
    private SchoolAdminService schoolAdminService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<Long> createAdmin(@RequestBody CreateSchoolAdminRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = schoolAdminCreationService.createAdmin(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedSchoolAdminResponse> getAllAdmins(@RequestParam int page,@RequestParam int perPage,@RequestParam(required = false) String search){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedSchoolAdminResponse response = schoolAdminService.getAllSchoolAdmin(loggedUser,page,perPage,search);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<?> getAdminDetails(@PathVariable Long adminId){
        SchoolAdminDetailsResponse response =  schoolAdminService.getAdminDetails(adminId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId){
        schoolAdminService.deleteAdmin(adminId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
