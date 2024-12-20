package v._1.PokeNest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.exception.custom.PetNameExistException;
import v._1.PokeNest.exception.custom.UserNotFoundException;
import v._1.PokeNest.model.Pet;
import v._1.PokeNest.model.Species;
import v._1.PokeNest.model.Type;
import v._1.PokeNest.model.User;
import v._1.PokeNest.repository.PetRepository;
import v._1.PokeNest.repository.SpeciesRepository;
import v._1.PokeNest.repository.TypeRepository;
import v._1.PokeNest.repository.UserRepository;
import v._1.PokeNest.service.PetService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    @Override
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) {

        // Buscar el usuario
        User user = userRepository.findById(petRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + petRequestDTO.getUserId()));

        // Buscar la especie
        Species species = speciesRepository.findBySpecieName(petRequestDTO.getSpeciesName())
                .orElseThrow(() -> new IllegalArgumentException("No se ha encontrado la especie de pokemon."));

        // Buscar los tipos
        List<Type> types = typeRepository.findByNameIn(petRequestDTO.getTypeNames());
        if (types.isEmpty()) {
            throw new IllegalArgumentException("No se han encontrado los tipos de pokemon.");
        }

        // Validar alias duplicado para el mismo usuario
        if (user.getPets().stream().anyMatch(p -> p.getAlias().equalsIgnoreCase(petRequestDTO.getAlias()))) {
            throw new PetNameExistException("El alias '" + petRequestDTO.getAlias() +
                    " ya existe, porfavor, escriba uno nuevo.");
        }

        // Crear el Pet
        Pet pet = Pet.builder()
                .alias(petRequestDTO.getAlias())
                .user(user)
                .species(species)
                .types(new HashSet<>(types))
                .lvl(1) // Valores iniciales por defecto
                .experience(0)
                .happiness(70)
                .ph(100)
                .build();

        // Agregar el Pet a la lista del User
        user.getPets().add(pet);

        // Guardar el User (esto guarda automáticamente el Pet si cascade = CascadeType.ALL está configurado)
        userRepository.save(user);

        // Devolver el Pet como ResponseDTO
        return PetResponseDTO.builder()
                .id(pet.getId())
                .alias(pet.getAlias())
                .species(species.getSpecieName())
                .types(types.stream().map(Type::getName).collect(Collectors.toSet()))
                .lvl(pet.getLvl())
                .experience(pet.getExperience())
                .happiness(pet.getHappiness())
                .ph(pet.getPh())
                .build();
    }
}
