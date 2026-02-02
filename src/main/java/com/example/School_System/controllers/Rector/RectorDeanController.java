package com.example.School_System.controllers.Rector;

import com.example.School_System.dto.rector.RectorInviteDeanRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.RectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rector/dean")
@PreAuthorize("hasRole('RECTOR')")
public class RectorDeanController {
    private final RectorService rectorService;
    private final AuthorizationService authorizationService;

    public RectorDeanController(RectorService rectorService,AuthorizationService authorizationService) {
        this.rectorService = rectorService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<String> inviteDean(@RequestBody RectorInviteDeanRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        String invitationToken = rectorService.createAndSendDeanInvitation(request,loggedUser);
        return new ResponseEntity<>(invitationToken, HttpStatus.OK);
    }

}
