package v._1.PokeNest.service.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class DefaultTypeService {
    private static final Map<String, Set<String>> DEFAULT_TYPES = new HashMap<>();

    static {
        DEFAULT_TYPES.put("Bulbasaur", Set.of("Planta", "Veneno"));
        DEFAULT_TYPES.put("Ivysaur", Set.of("Planta", "Veneno"));
        DEFAULT_TYPES.put("Venusaur", Set.of("Planta", "Veneno"));
        DEFAULT_TYPES.put("Charmander", Set.of("Fuego"));
        DEFAULT_TYPES.put("Charmeleon", Set.of("Fuego"));
        DEFAULT_TYPES.put("Charizard", Set.of("Fuego", "Volador"));
        DEFAULT_TYPES.put("Squirtle", Set.of("Agua"));
        DEFAULT_TYPES.put("Wartortle", Set.of("Agua"));
        DEFAULT_TYPES.put("Blastoise", Set.of("Agua"));
        DEFAULT_TYPES.put("Eevee", Set.of("Normal"));
        DEFAULT_TYPES.put("Vaporeon", Set.of("Agua"));
        DEFAULT_TYPES.put("Jolteon", Set.of("Eléctrico"));
        DEFAULT_TYPES.put("Flareon", Set.of("Fuego"));
        DEFAULT_TYPES.put("Espeon", Set.of("Psíquico"));
        DEFAULT_TYPES.put("Umbreon", Set.of("Siniestro"));
        DEFAULT_TYPES.put("Leafeon", Set.of("Planta"));
        DEFAULT_TYPES.put("Glaceon", Set.of("Hielo"));
        DEFAULT_TYPES.put("Sylveon", Set.of("Hada"));
    }

    public Set<String> getDefaultTypesForSpecies(String speciesName) {
        return DEFAULT_TYPES.getOrDefault(speciesName, Set.of());
    }
}
