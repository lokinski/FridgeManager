package pl.lokinski.fridgemanager.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FridgeUserTest {

    @Test
    void shouldNotContainPasswordFieldToString() {
        FridgeUser fridgeUser = new FridgeUser(1L, "test", "test");

        assertThat(fridgeUser.toString()).doesNotContain("password");
    }
}