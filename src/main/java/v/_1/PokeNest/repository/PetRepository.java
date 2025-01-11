package v._1.PokeNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v._1.PokeNest.model.Pet;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
}
