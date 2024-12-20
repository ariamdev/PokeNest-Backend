package v._1.PokeNest.service;

import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.model.Pet;


public interface PetService {
    PetResponseDTO createPet(PetRequestDTO petRequestDTO);
}
