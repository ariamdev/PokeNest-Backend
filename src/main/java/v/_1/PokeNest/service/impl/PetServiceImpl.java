package v._1.PokeNest.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.Set;
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
    public List<PetResponseDTO> getUserPets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        List<Pet> pets = user.getPets();

        if (pets.isEmpty()) {
            throw new PetNotFoundException("No pets found for user: " + username);
        }

        //Si no funciona probar solo con user.getPets();
        return user.getPets().stream().map(this::buildPetResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PetAndUserResponseDTO> getAllPets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!isAdmin(username)) {
            throw new SecurityException("Unauthorized to access all pets.");
        }

        List<Pet> pets = petRepository.findAll();

        if (pets.isEmpty()) {
            throw new PetNotFoundException("No pets found in the system.");
        }

        return petRepository.findAll().stream()
                .map(pet -> PetAndUserResponseDTO.builder()
                        .userId(pet.getUser().getId())
                        .username(pet.getUser().getUsername())
                        .id(pet.getId())
                        .alias(pet.getAlias())
                        .species(pet.getSpecies().getSpecieName())
                        .types(pet.getTypes().stream().map(Type::getName).collect(Collectors.toSet()))
                        .lvl(pet.getLvl())
                        .experience(pet.getExperience())
                        .happiness(pet.getHappiness())
                        .ph(pet.getPh())
                        .location(pet.getLocation().name())
                        .build())
                .collect(Collectors.toList());
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
