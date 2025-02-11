package v._1.PokeNest.pets.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import v._1.PokeNest.pets.dto.request.PetFindRequestDTO;
import v._1.PokeNest.pets.dto.request.PetRequestDTO;
import v._1.PokeNest.pets.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;


public interface PetService {
    PetResponseDTO createPet(PetRequestDTO petRequestDTO);
    void deletePet(PetFindRequestDTO petFindRequestDTO);
    PetResponseDTO getOnePet(int id);
    Page<PetResponseDTO> getUserPets(Pageable pageable);
    Page<PetAndUserResponseDTO> getAllPets(Pageable pageable);
}
