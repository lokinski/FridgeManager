package pl.lokinski.fridgemanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lokinski.fridgemanager.exception.FoodNotFoundException;
import pl.lokinski.fridgemanager.model.Food;
import pl.lokinski.fridgemanager.repository.FoodRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock private FoodRepository foodRepository;
    private FoodService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new FoodService(this.foodRepository);
    }

    @Test
    void canGetAllFood() {
        this.underTest.findAll();

        verify(this.foodRepository).findAll();
    }

    @Test
    void canFindFoodById() {
        Long foodId = 1L;
        Food food = new Food("Apple", LocalDate.of(2022, 6, 20), LocalDate.of(2022, 6, 4));
        doReturn(Optional.of(food)).when(this.foodRepository).findById(foodId);

        assertThat(this.underTest.findFoodById(foodId))
                .isEqualTo(food);
    }

    @Test
    void willThrowIfFoodWithSpecificIdDoesNotExist() {
        Long foodId = 1L;
        doReturn(Optional.empty()).when(this.foodRepository).findById(foodId);

        assertThatThrownBy(() -> this.underTest.findFoodById(foodId))
                .isInstanceOf(FoodNotFoundException.class)
                .hasMessageContaining("Could not find food with id: " + foodId);
    }

    @Test
    void canSaveFood() {
        Food food = new Food("Apple", LocalDate.of(2022, 6, 20), LocalDate.of(2022, 6, 4));
        this.underTest.saveFood(food);

        ArgumentCaptor<Food> foodArgumentCaptor = ArgumentCaptor.forClass(Food.class);

        verify(this.foodRepository)
                .save(foodArgumentCaptor.capture());
        Food capturedFood = foodArgumentCaptor.getValue();

        assertThat(capturedFood).isEqualTo(food);
    }

    @Test
    void willThrowIfFoodNameIsEmpty() {
        Food food = new Food("", LocalDate.of(2022, 6, 20), LocalDate.of(2022, 6, 4));

        assertThatThrownBy(() -> this.underTest.saveFood(food))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The name cannot be empty");

        verify(this.foodRepository, never()).save(any());
    }

    @Test
    void shouldSaveUpdatedFood() {
        Long foodId = 1L;
        Food food = new Food("Milk", LocalDate.of(2022, 6, 20), LocalDate.now());
        doReturn(Optional.of(food)).when(this.foodRepository).findById(foodId);
        doReturn(food).when(this.foodRepository).save(food);

        Food updatedFood = new Food("Strawberry Milk", LocalDate.of(2022, 6, 21), LocalDate.now());
        this.underTest.updateFood(foodId, updatedFood);

        ArgumentCaptor<Food> foodArgumentCaptor = ArgumentCaptor.forClass(Food.class);
        verify(this.foodRepository)
                .save(foodArgumentCaptor.capture());
        Food capturedFood = foodArgumentCaptor.getValue();

        assertThat(capturedFood).isEqualTo(food);
        assertThat(capturedFood.getName()).isEqualTo(updatedFood.getName());
        assertThat(capturedFood.getExpirationDate()).isEqualTo(updatedFood.getExpirationDate());
        verify(this.foodRepository).save(food);
    }

    @Test
    void shouldReturnOnlyExpiredFood() {
        Food foodNormal = new Food("Milk", LocalDate.now().plusDays(5), LocalDate.now());
        Food foodExpired = new Food("Milk", LocalDate.now().minusDays(5), LocalDate.now());
        doReturn(Arrays.asList(foodExpired, foodNormal)).when(this.foodRepository).findAll();

        assertThat(this.underTest.findExpiredFood().stream().anyMatch(food -> !food.isExpired())).isFalse();
    }
}