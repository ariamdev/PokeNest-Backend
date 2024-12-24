package v._1.PokeNest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetAndUserResponseDTO {
    private int userId;
    private String username;
    private int id;
    private String alias;
    private String species;
    private Set<String> types;
    private int lvl;
    private int experience;
    private int happiness;
    private int ph;
    private String location;
}
