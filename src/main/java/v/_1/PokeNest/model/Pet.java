package v._1.PokeNest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import v._1.PokeNest.model.enums.Location;

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
    @JoinColumn(name = "user_id", nullable = false) // Clave foránea hacia User
    private User user;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false) // Clave foránea hacia Species
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
}
