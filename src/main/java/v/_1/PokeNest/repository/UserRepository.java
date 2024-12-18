package v._1.PokeNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v._1.PokeNest.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
