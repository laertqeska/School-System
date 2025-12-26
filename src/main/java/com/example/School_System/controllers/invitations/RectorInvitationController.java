package com.example.School_System.controllers.invitations;

import com.example.School_System.dto.rector.RectorRegistrationRequest;
import com.example.School_System.services.RectorInvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations/rector")
public class RectorInvitationController {
    private final RectorInvitationService rectorInvitationService;

    public RectorInvitationController(RectorInvitationService rectorInvitationService){
        this.rectorInvitationService = rectorInvitationService;
    }

    @PostMapping("/accept")
    public ResponseEntity<HttpStatus> acceptInvitation(
            @RequestParam String token,
            @RequestBody RectorRegistrationRequest request
            ){
        rectorInvitationService.acceptInvitation(token,request);
        return ResponseEntity.ok().build();
    }
}
