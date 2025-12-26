package com.example.School_System.controllers.invitations;


import com.example.School_System.dto.dean.DeanRegistrationRequest;
import com.example.School_System.services.DeanInvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations/dean")
public class DeanInvitationController {
    private final DeanInvitationService deanInvitationService;

    public DeanInvitationController(DeanInvitationService deanInvitationService) {
        this.deanInvitationService = deanInvitationService;
    }

    @PostMapping("/accept")
    public ResponseEntity<HttpStatus> acceptInvitation(
            @RequestParam String token,
            @RequestBody DeanRegistrationRequest request
    ){

        deanInvitationService.acceptInvitation(token,request);
        return ResponseEntity.ok().build();
    }

}
