package v._1.PokeNest.pets.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import v._1.PokeNest.pets.dto.request.PetFindRequestDTO;
import v._1.PokeNest.pets.dto.request.PetRequestDTO;
import v._1.PokeNest.pets.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.exception.custom.PetNameExistException;
import v._1.PokeNest.pets.exception.custom.PetNotFoundException;
import v._1.PokeNest.pets.model.Pet;
import v._1.PokeNest.pets.model.Species;
import v._1.PokeNest.pets.model.Type;
import v._1.PokeNest.pets.model.User;
import v._1.PokeNest.pets.model.enums.Location;
import v._1.PokeNest.pets.repository.PetRepository;
import v._1.PokeNest.pets.repository.SpeciesRepository;
import v._1.PokeNest.pets.repository.TypeRepository;
import v._1.PokeNest.pets.service.PetMapper;
import v._1.PokeNest.pets.service.PetService;
import v._1.PokeNest.auth.service.AuthService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesRepository speciesRepository;
    private final TypeRepository typeRepository;
    private final DefaultLocationService defaultLocationService;
    private final DefaultTypeService defaultTypeService;
    private final AuthService authService;
    private final PetMapper petMapper;


    @Override
    public PetResponseDTO createPet(PetRequestDTO petRequestDTO) {
        User user = authService.getAuthenticatedUser();

        Species species = speciesRepository.findBySpecieName(petRequestDTO.getSpeciesName())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find a species of Pokemon."));

        Set<Type> defaultTypes = defaultTypeService.getDefaultTypesForSpecies(species.getSpecieName()).stream()
                .map(typeName -> typeRepository.findByName(typeName)
                        .orElseThrow(() -> new IllegalArgumentException("Type not found: " + typeName)))
                .collect(Collectors.toSet());

        if (user.hasPetWithAlias(petRequestDTO.getAlias())) {
            throw new PetNameExistException("The alias '" + petRequestDTO.getAlias() + "' is already in use.");
        }

        Location defaultLocation = defaultLocationService.getDefaultLocationForSpecies(species.getSpecieName());

        Pet pet = petMapper.toEntity(petRequestDTO, user, species, defaultTypes, defaultLocation);

        Pet savedPet = petRepository.save(pet);
        user.getPets().add(savedPet);

        return petMapper.toDTO(savedPet);
    }

    @Override
    public void deletePet(PetFindRequestDTO petFindRequestDTO) {
        Pet pet = getPetById(petFindRequestDTO.getId());
        pet.verifyOwnership(authService.getAuthenticatedUser(), authService);
        petRepository.delete(pet);
    }

    @Override
    public PetResponseDTO getOnePet(int id) {
        Pet pet = getPetById(id);
        pet.verifyOwnership(authService.getAuthenticatedUser(), authService);
        return petMapper.toDTO(pet);
    }

    @Override
    public Page<PetResponseDTO> getUserPets(Pageable pageable) {
        User user = authService.getAuthenticatedUser();
        Page<Pet> petsPage = petRepository.findByUser(user, pageable);
        return petsPage.map(petMapper::toDTO);
    }

    @Override
    public Page<PetAndUserResponseDTO> getAllPets(Pageable pageable) {
        if (!authService.isAdmin(authService.getAuthenticatedUser())) {
            throw new SecurityException("Unauthorized to access all pets.");
        }

        Page<Pet> petsPage = petRepository.findAll(pageable);
        return petsPage.map(pet -> new PetAndUserResponseDTO(pet.getUser().getId(), pet.getUser().getUsername(), List.of(petMapper.toDTO(pet))));
    }

    private Pet getPetById(int id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + id));
    }

}
