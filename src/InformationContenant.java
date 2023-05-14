import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Interface InformationContenant
 * Cette interface déclare des constantes qui représentent différentes tailles et coûts de contenants.
 */
public interface InformationContenant {

    // Mappage des formats de pot à leurs descriptions
    LinkedHashMap<Double, String> POT_FORMATS = new LinkedHashMap<Double, String>() {{
        put(20000.0, "Exagéré");
        put(2000.0, "Grand");
        put(500.0, "Petit");
        put(100.0, "Minuscule");
    }};

    // Mappage des formats de pot à leurs unités d'origine
    LinkedHashMap<Double, String> POT_FORMATS_ORIGINAL = new LinkedHashMap<Double, String>() {{
        put(20000.0, "L");
        put(2000.0, "L");
        put(500.0, "ml");
        put(100.0, "ml");
    }};

    // Mappage des formats de sachet à leurs descriptions
    LinkedHashMap<Double, String> SACHET_FORMATS = new LinkedHashMap<Double, String>() {{
        put(500000.0, "Gargantuesque");
        put(100000.0, "Très grand");
        put(20000.0, "Grand");
        put(2000.0, "Moyen");
        put(500.0, "Petit");
        put(100.0, "Minuscule");
        put(30.0, "Lilliputien");
    }};

    // Mappage des formats de sachet à leurs unités d'origine
    HashMap<Double, String> SACHET_FORMATS_ORIGINAL = new LinkedHashMap<Double, String>() {{
        put(500000.0, "kg");
        put(100000.0, "kg");
        put(20000.0, "kg");
        put(2000.0, "kg");
        put(500.0, "g");
        put(100.0, "g");
        put(30.0, "g");
    }};

    // Mappage des formats de pot à leurs coûts
    LinkedHashMap<Double, Double> POT_COUTS = new LinkedHashMap<Double, Double>() {{
        put(20000.0, 5.75);
        put(2000.0, 0.70);
        put(500.0, 0.50);
        put(100.0, 0.30);
    }};

    // Mappage des formats de sachet à leurs coûts
    LinkedHashMap<Double, Double> SACHET_COUTS = new LinkedHashMap<Double, Double>() {{
        put(500000.0, 15.75);
        put(100000.0, 5.50);
        put(20000.0, 1.25);
        put(2000.0, 0.75);
        put(500.0, 0.25);
        put(100.0, 0.15);
        put(30.0, 0.02);
    }};

    // Liste des clés des formats de pot
    ArrayList<Double> POT_FORMATS_KEYS = new ArrayList<>(POT_FORMATS.keySet());

    // Liste des clés des formats de sachet
    ArrayList<Double> SACHET_FORMATS_KEYS = new ArrayList<>(SACHET_FORMATS.keySet());

}
