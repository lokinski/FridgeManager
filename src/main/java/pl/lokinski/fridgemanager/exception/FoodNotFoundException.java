package pl.lokinski.fridgemanager.exception;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(Long id) {
        super("Could not find food with id: " + id);
    }
}
