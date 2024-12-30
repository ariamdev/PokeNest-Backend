package v._1.PokeNest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v._1.PokeNest.dto.request.PetFindRequestDTO;
import v._1.PokeNest.dto.request.PetRequestDTO;
import v._1.PokeNest.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.dto.response.PetResponseDTO;
import v._1.PokeNest.model.enums.PetInteraction;
import v._1.PokeNest.service.PetService;
import v._1.PokeNest.service.impl.PetUpdateService;

import java.util.List;

@RestController
@RequestMapping("/pokenest")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetUpdateService petUpdateService;

    @PostMapping("/create")
    public ResponseEntity<PetResponseDTO> createPet(@RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.CREATED);
    }

   @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePet(@RequestBody PetFindRequestDTO petFindRequestDTO){
        petService.deletePet(petFindRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getOne")
    public ResponseEntity<PetResponseDTO> getOnePet(@RequestBody PetFindRequestDTO petFindRequestDTO) {
        PetResponseDTO petResponseDTO = petService.getOnePet(petFindRequestDTO);
        return new ResponseEntity<>(petResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/getUserPoke")
    public ResponseEntity<List<PetResponseDTO>> getUserPets() {
        return ResponseEntity.ok(petService.getUserPets());
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<List<PetAndUserResponseDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PostMapping("/update")
    public ResponseEntity<PetResponseDTO> updatePet(@RequestBody PetFindRequestDTO petFindRequestDTO,
                                                    @RequestParam PetInteraction petInteraction){
        PetResponseDTO petResponseDTO = petUpdateService.updatePet(petFindRequestDTO, petInteraction);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/update/heal")
    public ResponseEntity<PetResponseDTO> healPet(@RequestBody PetFindRequestDTO petFindRequestDTO) {
        PetResponseDTO petResponseDTO = petUpdateService.healPet(petFindRequestDTO);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/update/eev")
    public ResponseEntity<PetResponseDTO> evolveEevee(
            @RequestBody PetFindRequestDTO petFindRequestDTO,
            @RequestParam String targetSpecies) {
        PetResponseDTO petResponseDTO = petUpdateService.evolveEevee(petFindRequestDTO, targetSpecies);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.OK);
    }
}
