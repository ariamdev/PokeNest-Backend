package v._1.PokeNest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.dto.request.PetFindRequestDTO;
import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.exception.custom.PetNameExistException;
import v._1.PokeNest.exception.custom.PetNotFoundException;
import v._1.PokeNest.exception.custom.UserNotFoundException;
import v._1.PokeNest.model.Pet;
import v._1.PokeNest.model.Species;
import v._1.PokeNest.model.Type;
import v._1.PokeNest.model.User;
import v._1.PokeNest.model.enums.Location;
import v._1.PokeNest.model.enums.Role;
import v._1.PokeNest.repository.PetRepository;
import v._1.PokeNest.repository.SpeciesRepository;
import v._1.PokeNest.repository.TypeRepository;
import v._1.PokeNest.repository.UserRepository;
import v._1.PokeNest.service.PetService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final DefaultLocationService defaultLocationService;
    private final DefaultTypeService defaultTypeService;


    @Override
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Species species = speciesRepository.findBySpecieName(petRequestDTO.getSpeciesName())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find a specie of pokemon."));

        Set<Type> defaultTypes = defaultTypeService.getDefaultTypesForSpecies(species.getSpecieName()).stream()
                .map(typeName -> typeRepository.findByName(typeName)
                        .orElseThrow(() -> new IllegalArgumentException("Type not found: " + typeName)))
                .collect(Collectors.toSet());

        if (user.getPets().stream().anyMatch(p -> p.getAlias().equalsIgnoreCase(petRequestDTO.getAlias()))) {
            throw new PetNameExistException("The alias '" + petRequestDTO.getAlias() + " is already in use.");
        }

        Location defaultLocation = defaultLocationService.getDefaultLocationForSpecies(species.getSpecieName());

        Pet pet = buildPet(petRequestDTO, user, species, defaultTypes, defaultLocation);

        Pet savedPet = petRepository.save(pet);

        // Añadir el Pet guardado al usuario
        user.getPets().add(savedPet);
        userRepository.save(user);

        return buildPetResponseDTO(savedPet);
    }

    private Pet buildPet(PetRequestDTO petRequestDTO, User user, Species species, Set<Type> types, Location defaultLocation) {
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

    @Override
    public void deletePet(PetFindRequestDTO petFindRequestDTO) {
        Pet pet = verifyOwner(petFindRequestDTO.getId());

        petRepository.delete(pet);
    }

    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        return user.getRole().equals(Role.ADMIN);
    }

    @Override
    public PetResponseDTO getOnePet(int id) {
        Pet pet = verifyOwner(id);

        return buildPetResponseDTO(pet);
    }

    @Override
    public Page<PetResponseDTO> getUserPets(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Page<Pet> petsPage = petRepository.findByUser(user, pageable);

        if (petsPage.isEmpty()) {
            throw new PetNotFoundException("No pets found for user: " + username);
        }

        return petsPage.map(this::buildPetResponseDTO);
    }


    @Override
    public Page<PetAndUserResponseDTO> getAllPets(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!isAdmin(username)) {
            throw new SecurityException("Unauthorized to access all pets.");
        }

        Page<Pet> petsPage = petRepository.findAll(pageable);

        if (petsPage.isEmpty()) {
            throw new PetNotFoundException("No pets found in the system.");
        }

        List<PetAndUserResponseDTO> petAndUserResponseList = petsPage.getContent().stream()
                .collect(Collectors.groupingBy(Pet::getUser))
                .entrySet()
                .stream()
                .map(entry -> PetAndUserResponseDTO.builder()
                        .userId(entry.getKey().getId())
                        .username(entry.getKey().getUsername())
                        .pets(entry.getValue().stream()
                                .map(this::buildPetResponseDTO)
                                .collect(Collectors.toList()))
                        .build())
                .sorted(Comparator.comparing(PetAndUserResponseDTO::getUserId))
                .collect(Collectors.toList());

        return new PageImpl<>(petAndUserResponseList, pageable, petsPage.getTotalElements());
    }

    private Pet verifyOwner(int id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        if (user.getRole().equals(Role.ADMIN)) {
            return pet;
        }

        if (!pet.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to access this pet.");
        }

        return pet;
    }

}
