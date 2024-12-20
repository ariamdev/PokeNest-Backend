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
public class PetResponseDTO {
    private int id;
    private String alias;
    private String species; //Se deja como string para no sobrecargar toda la información (Incluyendo ID)
    private Set<String> types;
    private int lvl;
    private int experience;
    private int happiness;
    private int ph;
    private String location; //Se deja como string para no sobrecargar toda la información (Incluyendo ID)
}
