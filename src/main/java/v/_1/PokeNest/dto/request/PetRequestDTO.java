package v._1.PokeNest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDTO {
    private String alias;
    private String speciesName;
    private Set<String> typeNames;
    private int userId;
}
