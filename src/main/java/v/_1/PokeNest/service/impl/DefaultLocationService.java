package v._1.PokeNest.service.impl;

import org.springframework.stereotype.Service;
import v._1.PokeNest.model.enums.Location;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultLocationService {

    private static final Map<String, Location> DEFAULT_LOCATIONS = new HashMap<>();

    static {
        DEFAULT_LOCATIONS.put("Bulbasaur", Location.FOREST);
        DEFAULT_LOCATIONS.put("Ivysaur", Location.FOREST);
        DEFAULT_LOCATIONS.put("Venusaur", Location.FOREST);
        DEFAULT_LOCATIONS.put("Charmander", Location.CAVE);
        DEFAULT_LOCATIONS.put("Charmeleon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Charizard", Location.CAVE);
        DEFAULT_LOCATIONS.put("Squirtle", Location.LAKE);
        DEFAULT_LOCATIONS.put("Wartortle", Location.LAKE);
        DEFAULT_LOCATIONS.put("Blastoise", Location.LAKE);
        DEFAULT_LOCATIONS.put("Eevee", Location.FOREST);
        DEFAULT_LOCATIONS.put("Vaporeon", Location.BEACH);
        DEFAULT_LOCATIONS.put("Jolteon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Flareon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Espeon", Location.FOREST);
        DEFAULT_LOCATIONS.put("Umbreon", Location.CAVE);
        DEFAULT_LOCATIONS.put("Leafeon", Location.FOREST);
        DEFAULT_LOCATIONS.put("Glaceon", Location.SNOW);
        DEFAULT_LOCATIONS.put("Sylveon", Location.FOREST);
    }

    public Location getDefaultLocationForSpecies(String speciesName) {
        return DEFAULT_LOCATIONS.getOrDefault(speciesName, Location.FOREST);
    }
}
