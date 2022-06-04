package pl.lokinski.fridgemanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock AuthenticationManager authenticationManager;
    @Mock UserDetailsService userDetailsService;
    @Mock JwtService jwtService;
    private AuthenticationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new AuthenticationService(this.authenticationManager, this.userDetailsService, this.jwtService);
    }

    @Test
    void willThrowIfBadCredentials() {
        String username = "test";
        String password = "test";

        Authentication auth = mock(Authentication.class);
        doReturn(auth).when(this.authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));

        assertThatThrownBy(() -> this.underTest.login(username, password))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Wrong username or password");
    }
}