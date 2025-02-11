package v._1.PokeNest.pets.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.auth.service.AuthService;
import v._1.PokeNest.pets.dto.request.PetFindRequestDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.exception.custom.PetNotFoundException;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.Species;
import v._1.PokeNest.pets.model.Type;
import v._1.PokeNest.pets.model.User;
import v._1.PokeNest.pets.model.enums.Location;
import v._1.PokeNest.pets.model.enums.PetInteraction;
import v._1.PokeNest.pets.repository.PetRepository;
import v._1.PokeNest.pets.repository.SpeciesRepository;
import v._1.PokeNest.pets.repository.TypeRepository;
import v._1.PokeNest.pets.service.PetMapper;

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
    private final PetEvolutionService petEvolutionService;
    private final AuthService authService;
    private final PetMapper petMapper;

    private Pet verifyOwner(int id) {
        User user = authService.getAuthenticatedUser();
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));

        pet.verifyOwnership(user, authService);
        return pet;
    }

    public PetResponseDTO updatePet(PetFindRequestDTO petFindRequestDTO, PetInteraction petInteraction) {
        Pet pet = verifyOwner(petFindRequestDTO.getId());

        switch (petInteraction) {
            case FEED -> pet.feedPet(pet);
            case SLEEP -> pet.startSleep(pet);
            case PLAY -> pet.playWithPet(pet);
            case TRAIN -> trainPet(pet);
            case EXPLORE -> exploreWithPet(pet);
            case HEAL -> pet.healPet(pet);
            default -> throw new IllegalArgumentException("Unknown interaction type: " + petInteraction);
        }
        pet.verifyPetState(pet);
        Pet savedPet = petRepository.save(pet);
        return petMapper.toDTO(savedPet);
    }

    private void trainPet(Pet pet) {
        pet.trainPet(pet);
        petEvolutionService.checkEvolution(pet);
    }

    private void exploreWithPet(Pet pet) {
        pet.exploreWithPet(pet);
        petEvolutionService.checkEvolution(pet);
    }

    public PetResponseDTO evolveEevee(PetFindRequestDTO petFindRequestDTO, String targetSpecies) {
        Pet pet = verifyOwner(petFindRequestDTO.getId());
        if (!"Eevee".equals(pet.getSpecies().getSpecieName())) {
            throw new IllegalStateException("Only Eevee can evolve manually.");
        }
        return petEvolutionService.evolveEevee(pet, targetSpecies);
    }
}
