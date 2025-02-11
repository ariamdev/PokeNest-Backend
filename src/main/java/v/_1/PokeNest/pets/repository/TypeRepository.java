package v._1.PokeNest.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v._1.PokeNest.pets.model.Type;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {
    Optional<Type> findByName(String name);
}
