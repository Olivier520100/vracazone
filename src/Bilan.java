import java.util.*;

public class Bilan implements InformationContenant, Unit{
    private final TreeMap<Integer, double[]> produitCoutHashMap = new TreeMap<Integer, double[]>();
    public void calculerBilan(Queue<Colis> entrepotColis, LinkedHashMap<Integer, Produit> produitLinkedHashMap){
        int potcount = 0;
        int sachetcount = 0;
        for (Colis colis : entrepotColis){
            for (Pot pot : colis.getListePot()){
                if (!produitCoutHashMap.containsKey(pot.getCodeProduit())){
                    produitCoutHashMap.put(pot.getCodeProduit(),new double[]{0.0,0.0,0.0,0.0});
                }
                potcount+=1;
                (produitCoutHashMap.get(pot.getCodeProduit())[0])+= pot.quantityUsed;
                (produitCoutHashMap.get(pot.getCodeProduit())[1])+= pot.quantityUsed;
                (produitCoutHashMap.get(pot.getCodeProduit())[2])+= POT_COUTS.get(pot.getCapacity());
            }
            for (Sachet sachet : colis.getStackSachet()){
                if (!produitCoutHashMap.containsKey(sachet.getCodeProduit())){
                    produitCoutHashMap.put(sachet.getCodeProduit(),new double[]{0.0,0.0,0.0,0.0});
                }
                sachetcount+=1;
                (produitCoutHashMap.get(sachet.getCodeProduit())[0])+= sachet.quantityUsed;
                (produitCoutHashMap.get(sachet.getCodeProduit())[1])+= sachet.quantityUsed;
                (produitCoutHashMap.get(sachet.getCodeProduit())[2])+= SACHET_COUTS.get(sachet.getCapacity());
            }

        }
        produitCoutHashMap.forEach((key, value) -> {
            value[3] = value[0] - value[1] - value[2];
            if (unitMasse.containsKey(produitLinkedHashMap.get(key).getUnite())){
                value[0] = value[0]/convertUnits(1,produitLinkedHashMap.get(key).getUnite(),"g");
                value[1] = value[1]/convertUnits(1,produitLinkedHashMap.get(key).getUnite(),"g");
                value[0] = value[0] * produitLinkedHashMap.get(key).getPrixUnitaire();
                value[1] = value[1] * produitLinkedHashMap.get(key).getPrixCoutant();
            } else{
                value[0] = value[0]/convertUnits(1,produitLinkedHashMap.get(key).getUnite(),"ml");
                value[0] = value[0] * produitLinkedHashMap.get(key).getPrixUnitaire();
                value[1] = value[1]/convertUnits(1,produitLinkedHashMap.get(key).getUnite(),"ml");
                value[1] = value[1] * produitLinkedHashMap.get(key).getPrixCoutant();
            }
            value[3] = value[0] - value[1] - value[2];
        });
    }

    public TreeMap<Integer, double[]> getProduitCoutHashMap() {
        return produitCoutHashMap;
    }

    public double convertUnits(double quantity, String fromUnit, String toUnit) {
        double result = quantity;

        // Check if both units are mass units
        if (unitMasse.containsKey(fromUnit) && unitMasse.containsKey(toUnit)) {
            double fromFactor = unitMasse.get(fromUnit);
            double toFactor = unitMasse.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        }
        else if (unitVolume.containsKey(fromUnit) && unitVolume.containsKey(toUnit)) {
            double fromFactor = unitVolume.get(fromUnit);
            double toFactor = unitVolume.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        }
        else {
            System.out.println("Conversion Impossible");
        }

        return result;
    }

}
