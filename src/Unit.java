import java.util.HashSet;

public interface Unit {
    HashSet<String> unitMasse = new HashSet<String>() {{
        add("mg");
        add("g");
        add("kg");
    }};
    HashSet<String> unitVolume = new HashSet<String>() {{
        add("ml");
        add("cl");
        add("L");
    }};

}