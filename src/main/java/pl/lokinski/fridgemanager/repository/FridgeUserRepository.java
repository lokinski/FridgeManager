package pl.lokinski.fridgemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lokinski.fridgemanager.model.FridgeUser;

import java.util.Optional;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {
    Optional<FridgeUser> findByUsername(String username);
}
