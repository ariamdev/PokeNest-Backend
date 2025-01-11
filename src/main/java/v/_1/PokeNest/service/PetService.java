package v._1.PokeNest.service;

import v._1.PokeNest.dto.request.PetFindRequestDTO;
import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.model.Pet;

import java.util.List;


public interface PetService {
    PetResponseDTO createPet(PetRequestDTO petRequestDTO);
    void deletePet(PetFindRequestDTO petFindRequestDTO);
    PetResponseDTO getOnePet(int id);
    List<PetResponseDTO> getUserPets();
    List<PetAndUserResponseDTO> getAllPets();
}
