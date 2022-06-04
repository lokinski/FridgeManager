package pl.lokinski.fridgemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.lokinski.fridgemanager.model.Food;
import pl.lokinski.fridgemanager.service.FoodService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/foods")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping()
    public ResponseEntity<List<Food>> findAll() {
        return ResponseEntity
                .ok(this.foodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.foodService.findFoodById(id));
    }

    /**
     *
     * @return a list of food that expired
     */
    @GetMapping("/expired")
    public ResponseEntity<List<Food>> findExpiredFood() {
        return ResponseEntity
                .ok(this.foodService.findExpiredFood());
    }

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/foods").toUriString());
        return ResponseEntity
                .created(uri)
                .body(this.foodService.saveFood(food));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food updatedFood) {
        return ResponseEntity
                .ok(this.foodService.updateFood(id, updatedFood));
    }

    @DeleteMapping("/{id}")
    public void deleteFoodById(@PathVariable Long id) {
        this.foodService.deleteFoodById(id);
    }
}