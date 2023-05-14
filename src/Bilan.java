import java.util.*;

public class Bilan implements InformationContenant, Unit{
    /**
     * Treemap représentant les coûts des produits.
     * La clé est le code du produit, et la valeur est un tableau de doubles contenant les coûts associés.
     * Utilise un treemap pour automatiquement garder l'ordre
     */
    private final TreeMap<Integer, double[]> produitCoutHashMap = new TreeMap<Integer, double[]>();
    /**
     * Calcule le bilan en fonction de la file d'attente de colis et de la table de hachage des produits.
     *
     * @param entrepotColis          La file d'attente des colis.
     * @param produitLinkedHashMap   La table de hachage des produits.
     */
    public void calculerBilan(Queue<Colis> entrepotColis, LinkedHashMap<Integer, Produit> produitLinkedHashMap) {
        int potcount = 0;
        int sachetcount = 0;

        // Parcours des colis de l'entrepôt
        for (Colis colis : entrepotColis) {
            // Parcours des pots dans le colis
            for (Pot pot : colis.getListePot()) {
                // Vérifie si le produit du pot existe déjà dans la table de hachage des coûts des produits
                if (!produitCoutHashMap.containsKey(pot.getCodeProduit())) {
                    // Si le produit n'existe pas, l'ajoute à la table de hachage avec des coûts initialisés à zéro
                    produitCoutHashMap.put(pot.getCodeProduit(), new double[]{0.0, 0.0, 0.0, 0.0});
                }

                potcount += 1;
                // Ajoute la quantité utilisée du pot aux coûts correspondants dans la table de hachage
                (produitCoutHashMap.get(pot.getCodeProduit())[0]) += pot.quantityUsed;
                (produitCoutHashMap.get(pot.getCodeProduit())[1]) += pot.quantityUsed;
                (produitCoutHashMap.get(pot.getCodeProduit())[2]) += POT_COUTS.get(pot.getCapacity());
            }

            // Parcours des sachets dans le colis
            for (Sachet sachet : colis.getStackSachet()) {
                // Vérifie si le produit du sachet existe déjà dans la table de hachage des coûts des produits
                if (!produitCoutHashMap.containsKey(sachet.getCodeProduit())) {
                    // Si le produit n'existe pas, l'ajoute à la table de hachage avec des coûts initialisés à zéro
                    produitCoutHashMap.put(sachet.getCodeProduit(), new double[]{0.0, 0.0, 0.0, 0.0});
                }

                sachetcount += 1;
                // Ajoute la quantité utilisée du sachet aux coûts correspondants dans la table de hachage
                (produitCoutHashMap.get(sachet.getCodeProduit())[0]) += sachet.quantityUsed;
                (produitCoutHashMap.get(sachet.getCodeProduit())[1]) += sachet.quantityUsed;
                (produitCoutHashMap.get(sachet.getCodeProduit())[2]) += SACHET_COUTS.get(sachet.getCapacity());
            }
        }

        // Calcule les coûts finaux pour chaque produit dans la table de hachage
        produitCoutHashMap.forEach((key, value) -> {
            if (unitMasse.containsKey(produitLinkedHashMap.get(key).getUnite())) {
                // Si l'unité de mesure est une unité de masse, convertit les quantités en grammes
                value[0] = value[0] / convertUnits(1, produitLinkedHashMap.get(key).getUnite(), "g");
                value[1] = value[1] / convertUnits(1, produitLinkedHashMap.get(key).getUnite(), "g");
                value[0] = value[0] * produitLinkedHashMap.get(key).getPrixUnitaire();
                value[1] = value[1] * produitLinkedHashMap.get(key).getPrixCoutant();
            } else {
                // Sinon, l'unité de mesure est une unité de volume, convertit les quantités en millilitres
                value[0] = value[0] / convertUnits(1, produitLinkedHashMap.get(key).getUnite(), "ml");
                value[0] = value[0] * produitLinkedHashMap.get(key).getPrixUnitaire();
                value[1] = value[1] / convertUnits(1, produitLinkedHashMap.get(key).getUnite(), "ml");
                value[1] = value[1] * produitLinkedHashMap.get(key).getPrixCoutant();
            }
            // Calcule le coût final en soustrayant le coût d'achat et le coût fixe du coût total
            value[3] = value[0] - value[1] - value[2];
        });
    }

    /**
     * Retourne la table de treemap.
     *
     * @return Le treemap.
     */
    public TreeMap<Integer, double[]> getProduitCoutHashMap() {
        return produitCoutHashMap;
    }

    /**
     * Convertit une quantité d'une unité de mesure à une autre.
     *
     * @param quantity   La quantité à convertir.
     * @param fromUnit   L'unité de mesure source.
     * @param toUnit     L'unité de mesure cible.
     * @return Le résultat de la conversion.
     */
    public double convertUnits(double quantity, String fromUnit, String toUnit) {
        double result = quantity;

        // Vérifie si les deux unités sont des unités de masse
        if (unitMasse.containsKey(fromUnit) && unitMasse.containsKey(toUnit)) {
            double fromFactor = unitMasse.get(fromUnit);
            double toFactor = unitMasse.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        }
        // Vérifie si les deux unités sont des unités de volume
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
