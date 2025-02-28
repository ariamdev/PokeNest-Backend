package v._1.PokeNest.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v._1.PokeNest.pets.model.Species;
import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Optional<Species> findBySpecieName(String specieName);
}