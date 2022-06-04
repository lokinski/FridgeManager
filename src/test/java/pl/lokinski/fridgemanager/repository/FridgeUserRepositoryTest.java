package pl.lokinski.fridgemanager.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.lokinski.fridgemanager.model.FridgeUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FridgeUserRepositoryTest {

    @Autowired
    private FridgeUserRepository underTest;

    @AfterEach
    void tearDown() {
        this.underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfFridgeUserUsernameExist() {
        String username = "test";
        FridgeUser fridgeUser = new FridgeUser(1L, username, "test");
        this.underTest.save(fridgeUser);

        boolean found = this.underTest.findByUsername(username).isPresent();

        assertThat(found).isTrue();
    }

    @Test
    void itShouldCheckIfFridgeUserUsernameDoesNotExist() {
        String username = "test";

        boolean found = this.underTest.findByUsername(username).isPresent();

        assertThat(found).isFalse();
    }
}