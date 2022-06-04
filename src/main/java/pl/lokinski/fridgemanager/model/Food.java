package pl.lokinski.fridgemanager.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table
@Getter @Setter @NoArgsConstructor
public class Food {

    @Id
    @SequenceGenerator(
            name = "food_sequence",
            sequenceName = "food_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "food_sequence"
    )
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Transient
    private Long daysAfterExpiration;

    @Setter(AccessLevel.PROTECTED)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate addDate;

    public Food(String name, LocalDate expirationDate, LocalDate addDate) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.addDate = addDate;
    }

    public Long getDaysAfterExpiration() {
        return ChronoUnit.DAYS.between(this.expirationDate, LocalDate.now());
    }

    public boolean isExpired() {
        return this.getDaysAfterExpiration() > 0;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", addDate=" + addDate +
                '}';
    }
}