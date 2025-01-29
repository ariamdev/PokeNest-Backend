package v._1.PokeNest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetAndUserResponseDTO {
    private int userId;
    private String username;
    private List<PetResponseDTO> pets;
}
