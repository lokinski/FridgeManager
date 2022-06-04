package pl.lokinski.fridgemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.lokinski.fridgemanager.model.AuthResponse;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthResponse login(String username, String password) {
        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Authentication failed");
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            String jwt = this.jwtService.generateToken(userDetails);

            return new AuthResponse(jwt);
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Wrong username or password");
        }
    }
}
