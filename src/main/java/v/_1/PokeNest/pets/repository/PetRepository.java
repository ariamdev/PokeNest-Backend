package v._1.PokeNest.pets.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.User;


@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
    Page<Pet> findByUser(User user, Pageable pageable);
    Page<Pet> findAll(Pageable pageable);
}
