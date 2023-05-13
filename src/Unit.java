import java.util.HashMap;
import java.util.HashSet;

public interface Unit {
    HashMap<String, Double> unitMasse = new HashMap<String, Double>() {{
        put("mg", 1.0);
        put("g", 1000.0);
        put("kg", 1000000.0);
    }};
    HashMap<String, Double> unitVolume = new HashMap<String, Double>() {{
        put("ml", 1.0);
        put("cl", 10.0);
        put("L", 1000.0);
    }};

}