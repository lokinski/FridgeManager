package pl.lokinski.fridgemanager.exception;

public class FridgeUserUsernameAlreadyExistsException extends RuntimeException {
    public FridgeUserUsernameAlreadyExistsException(String username) {
        super("Username already exists: " + username);
    }
}
