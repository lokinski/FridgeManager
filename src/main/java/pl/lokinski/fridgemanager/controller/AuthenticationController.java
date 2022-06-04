package pl.lokinski.fridgemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.lokinski.fridgemanager.model.AuthRequest;
import pl.lokinski.fridgemanager.model.AuthResponse;
import pl.lokinski.fridgemanager.model.FridgeUser;
import pl.lokinski.fridgemanager.model.FridgeUserCreatedResponse;
import pl.lokinski.fridgemanager.service.AuthenticationService;
import pl.lokinski.fridgemanager.service.FridgeUserService;

import java.net.URI;

@Controller
@RequestMapping(path = "api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final FridgeUserService fridgeUserService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, FridgeUserService fridgeUserService) {
        this.authenticationService = authenticationService;
        this.fridgeUserService = fridgeUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity
                .ok(this.authenticationService.login(authRequest.getUsername(), authRequest.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<FridgeUserCreatedResponse> registerUser(@RequestBody FridgeUser fridgeUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/auth").toUriString());
        return ResponseEntity
                .created(uri)
                .body(this.fridgeUserService.createUser(fridgeUser));
    }
}