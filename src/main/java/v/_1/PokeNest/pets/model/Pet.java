package v._1.PokeNest.pets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import v._1.PokeNest.pets.model.enums.Location;
import v._1.PokeNest.auth.service.AuthService;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    @Column(nullable = false)
    private int lvl = 1;

    @Column(nullable = false)
    private int experience = 0;

    @Column(nullable = false)
    private int happiness = 70;

    @Column(nullable = false)
    private int ph = 100;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "pet_type",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<Type> types;

    @Column(name = "is_sleeping", nullable = false)
    private boolean isSleeping = false;

    @Column(name = "sleep_end_time")
    private LocalDateTime sleepEndTime;

    public void verifyOwnership(User user, AuthService authService) {
        if (!authService.isAdmin(user) && !this.user.equals(user)) {
            throw new SecurityException("Unauthorized to access this pet.");
        }
    }

}
