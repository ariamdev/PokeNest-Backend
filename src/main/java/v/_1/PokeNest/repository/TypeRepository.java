package v._1.PokeNest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v._1.PokeNest.model.Type;

import java.util.List;
import java.util.Set;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {
    List<Type> findByNameIn(Set<String> names);
}
