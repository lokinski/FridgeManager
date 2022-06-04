package pl.lokinski.fridgemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lokinski.fridgemanager.exception.FoodNotFoundException;
import pl.lokinski.fridgemanager.model.Food;
import pl.lokinski.fridgemanager.repository.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> findAll() {
        return this.foodRepository.findAll();
    }

    public Food findFoodById(Long id) {
        return this.foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
    }

    public Food saveFood(Food food) {
        if (food.getName().isEmpty()) {
            throw new IllegalStateException("The name cannot be empty");
        }

        return this.foodRepository.save(food);
    }

    public Food updateFood(Long id, Food updatedFood) {
        return this.foodRepository.findById(id)
                .map(food -> {
                    food.setName(updatedFood.getName());
                    food.setExpirationDate(updatedFood.getExpirationDate());

                    return this.saveFood(food);
                })
                .orElseThrow(() -> new FoodNotFoundException(id));
    }

    public void deleteFoodById(Long id) {
        if (!this.foodRepository.existsById(id)) {
            throw new FoodNotFoundException(id);
        }

        this.foodRepository.deleteById(id);
    }

    public List<Food> findExpiredFood() {
        return this.foodRepository.findAll().stream()
                .filter(Food::isExpired)
                .collect(Collectors.toList());
    }
}