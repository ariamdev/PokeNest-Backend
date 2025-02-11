package v._1.PokeNest.pets.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.Species;
import v._1.PokeNest.pets.model.Type;
import v._1.PokeNest.pets.repository.PetRepository;
import v._1.PokeNest.pets.repository.SpeciesRepository;
import v._1.PokeNest.pets.repository.TypeRepository;
import v._1.PokeNest.pets.service.PetMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetEvolutionService {

    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final TypeRepository typeRepository;
    private final DefaultLocationService defaultLocationService;
    private final DefaultTypeService defaultTypeService;
    private final PetMapper petMapper;

    private static final Map<String, Map<Integer, String>> EVOLUTION_MAP = Map.of(
            "Charmander", Map.of(16, "Charmeleon", 36, "Charizard"),
            "Bulbasaur", Map.of(16, "Ivysaur", 32, "Venusaur"),
            "Squirtle", Map.of(16, "Wartortle", 32, "Blastoise")
    );

    public void checkEvolution(Pet pet) {
        if (pet.getLvl() >= 100 || pet.getExperience() < 100) return;

        pet.setLvl(pet.getLvl() + 1);
        pet.setExperience(0);
        pet.setPh(100);
        pet.setHappiness(100);
        handleEvolution(pet);
    }

    private void handleEvolution(Pet pet) {
        String speciesName = pet.getSpecies().getSpecieName();
        if ("Eevee".equals(speciesName)) return;

        EVOLUTION_MAP.getOrDefault(speciesName, Collections.emptyMap())
                .entrySet().stream()
                .filter(entry -> pet.getLvl() >= entry.getKey())
                .findFirst()
                .ifPresent(entry -> evolvePet(pet, entry.getValue()));
    }

    public PetResponseDTO evolveEevee(Pet pet, String targetSpecies) {
        List<String> eeveeVariants = List.of(
                "Vaporeon", "Jolteon", "Flareon", "Espeon",
                "Umbreon", "Leafeon", "Glaceon", "Sylveon"
        );

        if (!eeveeVariants.contains(targetSpecies)) {
            throw new IllegalArgumentException("Invalid Eevee evolution target: " + targetSpecies);
        }

        evolvePet(pet, targetSpecies);
        return petMapper.toDTO(petRepository.save(pet));
    }

    private void evolvePet(Pet pet, String newSpeciesName) {
        Species newSpecies = speciesRepository.findBySpecieName(newSpeciesName)
                .orElseThrow(() -> new IllegalArgumentException("Species not found: " + newSpeciesName));

        pet.setSpecies(newSpecies);
        pet.setLocation(defaultLocationService.getDefaultLocationForSpecies(newSpeciesName));
        updatePetTypes(pet, newSpeciesName);
    }

    private void updatePetTypes(Pet pet, String speciesName) {
        Set<Type> defaultTypes = defaultTypeService.getDefaultTypesForSpecies(speciesName).stream()
                .map(typeName -> typeRepository.findByName(typeName)
                        .orElseThrow(() -> new IllegalArgumentException("Type not found: " + typeName)))
                .collect(Collectors.toSet());
        pet.setTypes(defaultTypes);
    }
}

