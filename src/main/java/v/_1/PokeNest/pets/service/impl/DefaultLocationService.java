package v._1.PokeNest.pets.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import v._1.PokeNest.pets.model.enums.Location;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultLocationService {

    private Map<String, Location> defaultLocations;

    @PostConstruct
    public void loadDefaultLocations() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            defaultLocations = objectMapper.readValue(
                    new ClassPathResource("default_locations.json").getInputStream(),
                    new TypeReference<Map<String, Location>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default locations", e);
        }
    }

    public Location getDefaultLocationForSpecies(String speciesName) {
        return defaultLocations.getOrDefault(speciesName, Location.FOREST);
    }
}
