package pl.lokinski.fridgemanager.util;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secretKey.getBytes());
    }
}
