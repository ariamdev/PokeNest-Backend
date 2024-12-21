package v._1.PokeNest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.exception.custom.PetNameExistException;
import v._1.PokeNest.exception.custom.UserNotFoundException;
import v._1.PokeNest.model.Pet;
import v._1.PokeNest.model.Species;
import v._1.PokeNest.model.Type;
import v._1.PokeNest.model.User;
import v._1.PokeNest.model.enums.Location;
import v._1.PokeNest.repository.PetRepository;
import v._1.PokeNest.repository.SpeciesRepository;
import v._1.PokeNest.repository.TypeRepository;
import v._1.PokeNest.repository.UserRepository;
import v._1.PokeNest.service.PetService;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    private static final Map<String, Location> DEFAULT_LOCATIONS = new HashMap<>();

    static {
        DEFAULT_LOCATIONS.put("Bulbasaur", Location.FOREST);
        DEFAULT_LOCATIONS.put("Charmander", Location.CAVE);
        DEFAULT_LOCATIONS.put("Squirtle", Location.LAKE);
        DEFAULT_LOCATIONS.put("Eevee", Location.FOREST);
        DEFAULT_LOCATIONS.put("Vaporeon", Location.BEACH);
        DEFAULT_LOCATIONS.put("Jolteon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Flareon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Espeon", Location.FOREST);
        DEFAULT_LOCATIONS.put("Umbreon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Leafeon", Location.FOREST);
        DEFAULT_LOCATIONS.put("Glaceon", Location.SNOW);
        DEFAULT_LOCATIONS.put("Sylveon", Location.FOREST);
    }


    @Override
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));


        Species species = speciesRepository.findBySpecieName(petRequestDTO.getSpeciesName())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find a specie of pokemon."));


        List<Type> types = typeRepository.findByNameIn(petRequestDTO.getTypeNames());
        if (types.isEmpty()) {
            throw new IllegalArgumentException("We couldn't find a type of pokemon");
        }


        if (user.getPets().stream().anyMatch(p -> p.getAlias().equalsIgnoreCase(petRequestDTO.getAlias()))) {
            throw new PetNameExistException("The alias '" + petRequestDTO.getAlias() + " is already in use.");
        }

        //En el constructor tenemos que asignar el Location a cada uno de los que creemos por defecto.
        //En caso de que no se le asigne por cualquier cosa, se le pondrá un Bosque.
        Location defaultLocation = DEFAULT_LOCATIONS.getOrDefault(species.getSpecieName(), Location.FOREST);

        Pet pet = Pet.builder()
                .alias(petRequestDTO.getAlias())
                .user(user)
                .species(species)
                .types(new HashSet<>(types))
                .lvl(1)
                .experience(0)
                .happiness(70)
                .ph(100)
                .location(defaultLocation)
                .build();

        // Agregar la mascota al usuario
        user.getPets().add(pet);

        // Guardar el usuario (y por cascada, la mascota)
        userRepository.save(user);

        // Construir el DTO de respuesta
        return PetResponseDTO.builder()
                .id(pet.getId())
                .alias(pet.getAlias())
                .species(species.getSpecieName())
                .types(types.stream().map(Type::getName).collect(Collectors.toSet()))
                .lvl(pet.getLvl())
                .experience(pet.getExperience())
                .happiness(pet.getHappiness())
                .ph(pet.getPh())
                .location(pet.getLocation().name()) // Usar el nombre del enum como respuesta
                .build();
    }
}
