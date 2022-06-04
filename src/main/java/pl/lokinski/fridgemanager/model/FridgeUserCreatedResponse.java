package pl.lokinski.fridgemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FridgeUserCreatedResponse {
    private final Long id;
    private final String username;
}
