package v._1.PokeNest.pets.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetAndUserResponseDTO {
    private int userId;
    private String username;
    private List<PetResponseDTO> pets;
}
