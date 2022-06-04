package pl.lokinski.fridgemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lokinski.fridgemanager.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}