package v._1.PokeNest.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultTypeService {
    private Map<String, Set<String>> defaultTypes;

    @PostConstruct
    public void loadDefaultTypes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            defaultTypes = objectMapper.readValue(
                    new ClassPathResource("default_types.json").getInputStream(),
                    new TypeReference<Map<String, Set<String>>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default types", e);
        }
    }

    public Set<String> getDefaultTypesForSpecies(String speciesName) {
        return defaultTypes.getOrDefault(speciesName, Set.of());
    }
}
