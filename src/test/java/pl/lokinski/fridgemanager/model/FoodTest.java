package pl.lokinski.fridgemanager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FoodTest {

    @Test
    void shouldHaveCorrectDaysAfterExpiration() {
        Food food = new Food("Apple", LocalDate.now().minusDays(9), LocalDate.now());

        assertThat(food.getDaysAfterExpiration()).isEqualTo(9);
    }

    @Test
    void shouldBeExpired() {
        Food food = new Food("Apple", LocalDate.now().minusDays(1), LocalDate.now());

        assertThat(food.isExpired()).isTrue();
    }
}