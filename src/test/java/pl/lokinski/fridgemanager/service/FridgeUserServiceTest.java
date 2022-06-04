package pl.lokinski.fridgemanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lokinski.fridgemanager.exception.FridgeUserUsernameAlreadyExistsException;
import pl.lokinski.fridgemanager.model.FridgeUser;
import pl.lokinski.fridgemanager.repository.FridgeUserRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FridgeUserServiceTest {

    @Mock private FridgeUserRepository fridgeUserRepository;
    @Mock private PasswordEncoder passwordEncoder;
    private FridgeUserService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new FridgeUserService(this.fridgeUserRepository, this.passwordEncoder);
    }

    @Test
    void canCreateFridgeUser() {
        String password = "test";
        FridgeUser fridgeUser = new FridgeUser(1L, "test", password);
        doReturn(fridgeUser).when(this.fridgeUserRepository).save(fridgeUser);

        this.underTest.createUser(fridgeUser);

        assertThat(this.passwordEncoder.encode(password))
                .isEqualTo(fridgeUser.getPassword())
                .isNotEqualTo(password);

        ArgumentCaptor<FridgeUser> fridgeUserArgumentCaptor = ArgumentCaptor.forClass(FridgeUser.class);

        verify(this.fridgeUserRepository)
                .save(fridgeUserArgumentCaptor.capture());
        FridgeUser capturedFridgeUser = fridgeUserArgumentCaptor.getValue();

        assertThat(capturedFridgeUser).isEqualTo(fridgeUser);
    }

    @Test
    void willThrowIfUsernameOrPasswordIsEmpty() {
        FridgeUser fridgeUser = new FridgeUser(1L, "", "");

        assertThatThrownBy(() -> this.underTest.createUser(fridgeUser))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The username or password cannot be empty");

        verify(this.fridgeUserRepository, never()).save(any());
    }

    @Test
    void willThrowIfUsernameIsTaken() {
        FridgeUser fridgeUser = new FridgeUser(1L, "test", "test");
        doReturn(Optional.of(fridgeUser)).when(this.fridgeUserRepository).findByUsername(fridgeUser.getUsername());

        assertThatThrownBy(() -> this.underTest.createUser(fridgeUser))
                .isInstanceOf(FridgeUserUsernameAlreadyExistsException.class)
                .hasMessageContaining("Username already exists");

        verify(this.fridgeUserRepository, never()).save(any());
    }

    @Test
    void canLoadFridgeUserByUsername() {
        String username = "test";
        FridgeUser fridgeUser = new FridgeUser(1L, username, "test");
        doReturn(Optional.of(fridgeUser)).when(this.fridgeUserRepository).findByUsername(fridgeUser.getUsername());

        assertThat(this.underTest.loadUserByUsername(username))
                .isInstanceOf(UserDetails.class);
    }

    @Test
    void willThrowIfCantLoadFridgeUserByUsername() {
        String username = "test";
        doReturn(Optional.empty()).when(this.fridgeUserRepository).findByUsername(username);

        assertThatThrownBy(() -> this.underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}