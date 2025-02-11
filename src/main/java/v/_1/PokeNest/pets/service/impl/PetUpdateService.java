package v._1.PokeNest.pets.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.pets.dto.request.PetFindRequestDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.exception.custom.PetNotFoundException;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.Species;
import v._1.PokeNest.pets.model.Type;
import v._1.PokeNest.pets.model.enums.Location;
import v._1.PokeNest.pets.model.enums.PetInteraction;
import v._1.PokeNest.pets.repository.PetRepository;
import v._1.PokeNest.pets.repository.SpeciesRepository;
import v._1.PokeNest.pets.repository.TypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PetUpdateService {
    private final PetRepository petRepository;
    private final TypeRepository typeRepository;
    private final SpeciesRepository speciesRepository;
    private final DefaultLocationService defaultLocationService;
    private final DefaultTypeService defaultTypeService;

    private Pet verifyOwner(int id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));

        if (!pet.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to access this pet.");
        }
        return pet;
    }

      public PetResponseDTO updatePet(PetFindRequestDTO petFindRequestDTO, PetInteraction petInteraction) {
        Pet pet = verifyOwner(petFindRequestDTO.getId());

        switch (petInteraction) {
            case FEED:
                feedPet(pet);
                break;
            case SLEEP:
                startSleep(pet);
                break;
            case PLAY:
                playWithPet(pet);
                break;
            case TRAIN:
                trainPet(pet);
                break;
            case EXPLORE:
                exploreWithPet(pet);
                break;
            case HEAL:
                healPet(pet);
             break;
            default:
                throw new IllegalArgumentException("Unknown interaction type: " + petInteraction);
        }

        verifyPetState(pet);
        updatePetTypes(pet, pet.getSpecies().getSpecieName());
        petRepository.save(pet);

        return buildPetResponseDTO(pet);
    }


    private PetResponseDTO buildPetResponseDTO(Pet pet) {
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

    private void verifyPetState(Pet pet) {

        if(pet.getPh() <= 40){
            pet.setHappiness(40);
        }
    }

    private void updatePetTypes(Pet pet, String speciesName) {
        Set<Type> defaultTypes = defaultTypeService.getDefaultTypesForSpecies(speciesName).stream()
                .map(typeName -> typeRepository.findByName(typeName)
                        .orElseThrow(() -> new IllegalArgumentException("Type not found: " + typeName)))
                .collect(Collectors.toSet());

        pet.setTypes(defaultTypes);
    }

    public void healPet(Pet pet) {
        pet.setPh(100);
        pet.setHappiness(100);
    }

    private void startSleep(Pet pet) {
        pet.setPh(100);
        pet.setHappiness(100);
    }

    private void feedPet(Pet pet) {
        pet.setPh(Math.min(pet.getPh() + 25, 100));
        pet.setHappiness(Math.min(pet.getHappiness() + 20, 100));
    }

    private void playWithPet(Pet pet) {
        pet.setHappiness(Math.min(pet.getHappiness() + 30, 100));
    }

    private void trainPet(Pet pet) {
        pet.setPh(Math.max(pet.getPh() - 25, 0));
        pet.setExperience(Math.min(pet.getExperience() + 25, 100));
        pet.setHappiness(Math.max(pet.getPh() - 10, 0));
        checkEvolution(pet);
    }

    private void exploreWithPet(Pet pet) {

        pet.setPh(Math.max(pet.getPh() - 10, 0));
        pet.setExperience(Math.min(pet.getExperience() + 10, 100));
        pet.setLocation(Location.EXPLORE);

        petRepository.save(pet);

        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            scheduler.schedule(() -> {
                pet.setLocation(defaultLocationService.getDefaultLocationForSpecies(pet.getSpecies().getSpecieName()));
                petRepository.save(pet);
            }, 3, TimeUnit.SECONDS);
        }

        checkEvolution(pet);
    }

    private void checkEvolution(Pet pet) {
        if (pet.getLvl() >= 100) return;

        if (pet.getExperience() >= 100) {
            pet.setLvl(pet.getLvl() + 1);
            pet.setExperience(0);
            pet.setPh(100);
            pet.setHappiness(100);
            handleEvolution(pet);
        }
    }

    private void handleEvolution(Pet pet) {
        String speciesName = pet.getSpecies().getSpecieName();
        int lvl = pet.getLvl();

        Map<String, Map<Integer, String>> evolutionMap = new HashMap<>();

        evolutionMap.put("Charmander", Map.of(16, "Charmeleon", 36, "Charizard"));
        //evolutionMap.put("Charmeleon", Map.of(36, "Charizard"));
        evolutionMap.put("Bulbasaur", Map.of(16, "Ivysaur", 32, "Venusaur"));
        //evolutionMap.put("Ivysaur", Map.of(32, "Venusaur"));
        evolutionMap.put("Squirtle", Map.of(16, "Wartortle", 32, "Blastoise"));
        //evolutionMap.put("Wartortle", Map.of(32, "Blastoise"));


        if ("Eevee".equals(speciesName)) {
            return;
        }

        if (evolutionMap.containsKey(speciesName)) {
            Map<Integer, String> speciesEvolutions = evolutionMap.get(speciesName);
            for (Map.Entry<Integer, String> entry : speciesEvolutions.entrySet()) {
                if (lvl >= entry.getKey()) {
                    updateSpecies(pet, entry.getValue());
                    pet.setLocation(defaultLocationService.getDefaultLocationForSpecies(entry.getValue()));
                    updatePetTypes(pet, pet.getSpecies().getSpecieName());
                }
            }
        }
    }

    public PetResponseDTO evolveEevee(PetFindRequestDTO petFindRequestDTO, String targetSpecies) {
        Pet pet = verifyOwner(petFindRequestDTO.getId());

        if (!"Eevee".equals(pet.getSpecies().getSpecieName())) {
            throw new IllegalStateException("Only Eevee can evolve manually.");
        }

        List<String> eeveeVariants = List.of(
                "Vaporeon", "Jolteon", "Flareon", "Espeon",
                "Umbreon", "Leafeon", "Glaceon", "Sylveon"
        );

        if (!eeveeVariants.contains(targetSpecies)) {
            throw new IllegalArgumentException("Invalid Eevee evolution target: " + targetSpecies);
        }

        updateSpecies(pet, targetSpecies);
        pet.setLocation(defaultLocationService.getDefaultLocationForSpecies(targetSpecies));
        updatePetTypes(pet, pet.getSpecies().getSpecieName());
        petRepository.save(pet);

        return buildPetResponseDTO(pet);
    }

    private void updateSpecies(Pet pet, String newSpeciesName) {
        Species newSpecies = speciesRepository.findBySpecieName(newSpeciesName)
                .orElseThrow(() -> new IllegalArgumentException("Species not found: " + newSpeciesName));
        pet.setSpecies(newSpecies);
    }
}
