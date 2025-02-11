package v._1.PokeNest.pets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import v._1.PokeNest.pets.dto.request.PetFindRequestDTO;
import v._1.PokeNest.pets.dto.request.PetRequestDTO;
import v._1.PokeNest.pets.dto.response.PetAndUserResponseDTO;
import v._1.PokeNest.pets.dto.response.PetResponseDTO;
import v._1.PokeNest.pets.model.enums.PetInteraction;
import v._1.PokeNest.pets.service.PetService;
import v._1.PokeNest.pets.service.impl.PetUpdateService;

@RestController
@RequestMapping("/pokenest")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetUpdateService petUpdateService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponseDTO> createPet(@RequestBody PetRequestDTO petRequestDTO) {
        PetResponseDTO petResponseDTO = petService.createPet(petRequestDTO);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.CREATED);
    }

   @DeleteMapping("/delete")
   @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> deletePet(@RequestBody PetFindRequestDTO petFindRequestDTO){
        petService.deletePet(petFindRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/poke/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponseDTO> getOnePet(@PathVariable int id) {
        PetResponseDTO petResponseDTO = petService.getOnePet(id);
        return new ResponseEntity<>(petResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/getUserPoke")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<PetResponseDTO>> getUserPets(
            @PageableDefault(page = 0, size = 5, sort = "alias", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(petService.getUserPets(pageable));
    }

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PetAndUserResponseDTO>> getAllPets(
            @PageableDefault(page= 0, size = 10, sort = "user.id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(petService.getAllPets(pageable));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponseDTO> updatePet(@RequestBody PetFindRequestDTO petFindRequestDTO,
                                                    @RequestParam PetInteraction petInteraction){
        PetResponseDTO petResponseDTO = petUpdateService.updatePet(petFindRequestDTO, petInteraction);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/update/eev")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PetResponseDTO> evolveEevee(
            @RequestBody PetFindRequestDTO petFindRequestDTO,
            @RequestParam String targetSpecies) {
        PetResponseDTO petResponseDTO = petUpdateService.evolveEevee(petFindRequestDTO, targetSpecies);
        return new ResponseEntity<>(petResponseDTO, HttpStatus.OK);
    }
}
