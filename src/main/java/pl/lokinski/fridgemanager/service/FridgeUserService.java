package pl.lokinski.fridgemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lokinski.fridgemanager.exception.FridgeUserUsernameAlreadyExistsException;
import pl.lokinski.fridgemanager.model.FridgeUser;
import pl.lokinski.fridgemanager.model.FridgeUserCreatedResponse;
import pl.lokinski.fridgemanager.repository.FridgeUserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FridgeUserService implements UserDetailsService {

    private final FridgeUserRepository fridgeUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FridgeUserService(FridgeUserRepository fridgeUserRepository, PasswordEncoder passwordEncoder) {
        this.fridgeUserRepository = fridgeUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public FridgeUserCreatedResponse createUser(FridgeUser fridgeUser) {
        if (fridgeUser.getUsername().isEmpty() || fridgeUser.getPassword().isEmpty()) {
            throw new IllegalStateException("The username or password cannot be empty");
        }

        boolean nameTaken = this.fridgeUserRepository.findByUsername(fridgeUser.getUsername()).isPresent();
        if (nameTaken) {
            throw new FridgeUserUsernameAlreadyExistsException(fridgeUser.getUsername());
        }
        fridgeUser.setPassword(this.passwordEncoder.encode(fridgeUser.getPassword()));
        FridgeUser createdFridgeUser = this.fridgeUserRepository.save(fridgeUser);

        return new FridgeUserCreatedResponse(createdFridgeUser.getId(), createdFridgeUser.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FridgeUser fridgeUser = this.fridgeUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new User(fridgeUser.getUsername(), fridgeUser.getPassword(), authorities);
    }
}
