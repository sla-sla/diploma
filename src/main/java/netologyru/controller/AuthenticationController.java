package netologyru.controller;

import lombok.AllArgsConstructor;
import netologyru.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import netologyru.dto.request.AuthenticationRequest;
import netologyru.dto.response.AuthenticationResponse;
import netologyru.service.AuthenticationService;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = authenticationService.login(authenticationRequest);
        return new AuthenticationResponse(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authenticationService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}