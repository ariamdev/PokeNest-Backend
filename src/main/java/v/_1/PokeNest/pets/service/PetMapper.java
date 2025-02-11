package v._1.PokeNest.pets.service;

import org.springframework.stereotype.Component;
import v._1.PokeNest.pets.dto.request.PetRequestDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.Species;
import v._1.PokeNest.pets.model.Type;
import v._1.PokeNest.pets.model.User;
import v._1.PokeNest.pets.model.enums.Location;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PetMapper {
    public Pet toEntity(PetRequestDTO petRequestDTO, User user, Species species, Set<Type> types, Location defaultLocation) {
        return Pet.builder()
                .alias(petRequestDTO.getAlias())
                .user(user)
                .species(species)
                .types(types)
                .lvl(1)
                .experience(0)
                .happiness(70)
                .ph(100)
                .location(defaultLocation)
                .build();
    }

    public PetResponseDTO toDTO(Pet pet) {
        return PetResponseDTO.builder()
                .id(pet.getId())
                .alias(pet.getAlias())
                .species(pet.getSpecies().getSpecieName())
                .types(pet.getTypes().stream().map(Type::getName).collect(Collectors.toSet()))
                .lvl(pet.getLvl())
                .experience(pet.getExperience())
                .happiness(pet.getHappiness())
                .ph(pet.getPh())
                .location(pet.getLocation().name())
                .build();
    }
}
