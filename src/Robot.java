import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Robot implements Unit, InformationContenant {
    /**
     * Prépare un panier en utilisant les produits disponibles.
     * Elle détermine le type de contenant nécessaire (pot ou sachet) en fonction de l'unité du produit,
     * puis remplit les contenants jusqu'à ce que la quantité requise du produit soit atteinte.
     * Ensuite, elle ajoute les pots et sachets remplis à leurs listes respectives.
     *
     * @param panier          Panier contenant les informations des produits à préparer.
     * @param hashMapProduit  HashMap des produits disponibles, mappés par leur identifiant.
     * @return                Le colis contenant les pots et sachets remplis.
     */
    public Colis preparationPanier(Panier panier, HashMap<Integer, Produit> hashMapProduit) {
        ArrayList<Pot> pots = new ArrayList<>();
        ArrayList<Sachet> sachets = new ArrayList<>();
        double currentQuantityLeft = 0;
        int currentContenantNumber = 1;
        int containerTypePointer = 0;
        int conteurPot = 1;
        int conteurSachet = 1;
        Pot currentPot = null;
        Sachet currentSachet = null;
        int pointeurPanier = 0;
        while (pointeurPanier < panier.getCodesProduit().length && panier.getCodesProduit()[pointeurPanier] != 0) {
            currentQuantityLeft = 0;
            currentContenantNumber = 1;
            containerTypePointer = 0;
            if (unitVolume.containsKey(panier.getUnite()[pointeurPanier])) {
                currentQuantityLeft = convertUnits((panier.getQuantite()[pointeurPanier]), panier.getUnite()[pointeurPanier], "ml");
                while (currentQuantityLeft != 0) {
                    containerTypePointer = 0;
                    while (containerTypePointer < POT_FORMATS_KEYS.toArray().length) {
                        if (currentQuantityLeft > POT_FORMATS_KEYS.get(containerTypePointer) * 0.5 && (containerTypePointer < POT_FORMATS_KEYS.toArray().length - 1)) {
                            if (currentQuantityLeft - POT_FORMATS_KEYS.get(containerTypePointer) < 0) {
                                currentPot = new Pot(POT_FORMATS_KEYS.get(containerTypePointer), currentQuantityLeft, conteurPot, panier.getCodesProduit()[pointeurPanier]);
                                currentQuantityLeft = 0;
                            } else {
                                currentQuantityLeft = currentQuantityLeft - POT_FORMATS_KEYS.get(containerTypePointer);
                                currentPot = new Pot(POT_FORMATS_KEYS.get(containerTypePointer), POT_FORMATS_KEYS.get(containerTypePointer), conteurPot, panier.getCodesProduit()[pointeurPanier]);

                            } containerTypePointer = POT_FORMATS_KEYS.toArray().length;
                        } else if ((containerTypePointer == POT_FORMATS_KEYS.toArray().length - 1)) {
                            if (currentQuantityLeft - POT_FORMATS_KEYS.get(containerTypePointer) < 0) {

                                currentPot = new Pot(POT_FORMATS_KEYS.get(containerTypePointer), currentQuantityLeft, conteurPot, panier.getCodesProduit()[pointeurPanier]);
                                currentQuantityLeft = 0;
                            } else {
                                currentQuantityLeft = currentQuantityLeft - POT_FORMATS_KEYS.get(containerTypePointer);
                                currentPot = new Pot(POT_FORMATS_KEYS.get(containerTypePointer), POT_FORMATS_KEYS.get(containerTypePointer), conteurPot, panier.getCodesProduit()[pointeurPanier]);

                            } containerTypePointer = POT_FORMATS_KEYS.toArray().length;
                        }
                        containerTypePointer += 1;
                    }
                    conteurPot+=1;
                    pots.add(currentPot);
                    System.out.println("Verser " + convertUnits((currentPot.getQuantityUsed()), "ml", POT_FORMATS_ORIGINAL.get(currentPot.getCapacity())) + " " +  POT_FORMATS_ORIGINAL.get(currentPot.getCapacity()) + " de " + hashMapProduit.get(currentPot.codeProduit).getDescription() + " dans " + POT_FORMATS.get(currentPot.getCapacity()) + " pot de " + convertUnits((currentPot.getCapacity()), "ml", POT_FORMATS_ORIGINAL.get(currentPot.getCapacity()))+ " " +  POT_FORMATS_ORIGINAL.get(currentPot.getCapacity()) + "(pot " + currentPot.containerNumber + ")");
                    System.out.println("Visser " + currentPot.containerNumber);
                }
            } else {
                currentQuantityLeft = convertUnits((panier.getQuantite()[pointeurPanier]), panier.getUnite()[pointeurPanier], "g");
                while (currentQuantityLeft != 0) {
                    containerTypePointer = 0;
                    while (containerTypePointer < SACHET_FORMATS_KEYS.toArray().length) {
                        if (currentQuantityLeft > SACHET_FORMATS_KEYS.get(containerTypePointer) * 0.5 && (containerTypePointer < SACHET_FORMATS_KEYS.toArray().length - 1)) {
                            if (currentQuantityLeft - SACHET_FORMATS_KEYS.get(containerTypePointer) < 0) {
                                currentSachet = new Sachet(SACHET_FORMATS_KEYS.get(containerTypePointer), currentQuantityLeft, conteurSachet, panier.getCodesProduit()[pointeurPanier]);
                                currentQuantityLeft = 0;
                            } else {
                                currentQuantityLeft = currentQuantityLeft - SACHET_FORMATS_KEYS.get(containerTypePointer);
                                currentSachet = new Sachet(SACHET_FORMATS_KEYS.get(containerTypePointer), SACHET_FORMATS_KEYS.get(containerTypePointer), conteurSachet, panier.getCodesProduit()[pointeurPanier]);

                            } containerTypePointer = SACHET_FORMATS_KEYS.toArray().length;
                        } else if ((containerTypePointer == SACHET_FORMATS_KEYS.toArray().length - 1)) {
                            if (currentQuantityLeft - SACHET_FORMATS_KEYS.get(containerTypePointer) < 0) {

                                currentSachet = new Sachet(SACHET_FORMATS_KEYS.get(containerTypePointer), currentQuantityLeft, conteurSachet, panier.getCodesProduit()[pointeurPanier]);
                                currentQuantityLeft = 0;
                            } else {
                                currentQuantityLeft = currentQuantityLeft - SACHET_FORMATS_KEYS.get(containerTypePointer);
                                currentSachet = new Sachet(SACHET_FORMATS_KEYS.get(containerTypePointer), SACHET_FORMATS_KEYS.get(containerTypePointer), conteurSachet, panier.getCodesProduit()[pointeurPanier]);

                            } containerTypePointer = SACHET_FORMATS_KEYS.toArray().length;
                        }
                        containerTypePointer += 1;
                    }
                    conteurSachet+=1;
                    sachets.add(currentSachet);
                    System.out.println("Verser " + convertUnits((currentSachet.getQuantityUsed()), "g", SACHET_FORMATS_ORIGINAL.get(currentSachet.getCapacity())) + " " +  SACHET_FORMATS_ORIGINAL.get(currentSachet.getCapacity()) + " de " + hashMapProduit.get(currentSachet.codeProduit).getDescription() + " dans " + SACHET_FORMATS.get(currentSachet.getCapacity()) + " sachet de " + convertUnits((currentSachet.getCapacity()), "g", SACHET_FORMATS_ORIGINAL.get(currentSachet.getCapacity()))+ " " +  SACHET_FORMATS_ORIGINAL.get(currentSachet.getCapacity()) + "(sachet " + currentSachet.containerNumber + ")");
                    System.out.println("Sceller sachet " + currentSachet.containerNumber);
                }
            }
            pointeurPanier += 1;
        }



        Colis colis = emballage(pots,sachets,panier.getIdentificationTransaction());
        return colis;

    }
    /**
     * Emballe les pots et sachets dans un colis.
     * Trie les pots et sachets par ordre croissant de capacité, puis les empile dans un colis.
     *
     * @param pots            Liste des pots à emballer.
     * @param sachets         Liste des sachets à emballer.
     * @param idTransaction   Identifiant de la transaction associée au colis.
     * @return                Le colis contenant les pots et sachets emballés.
     */
    public Colis emballage(ArrayList<Pot> pots, ArrayList<Sachet> sachets, String idTransaction){
        pots.sort(null);
        sachets.sort(null);

        Stack<Sachet> sachetStack = new Stack<>();
        sachetStack.addAll(sachets);



        return new Colis(pots,sachetStack,idTransaction);

    }

    /**
     * Convertit une quantité d'une unité à une autre.
     * Si les deux unités sont des unités de masse, effectue la conversion en utilisant les facteurs de conversion appropriés.
     * Si les deux unités sont des unités de volume, effectue la conversion de la même manière.
     * Si les unités ne sont pas compatibles, affiche un message d'erreur.
     *
     * @param quantity   Quantité à convertir.
     * @param fromUnit   Unité de la quantité à convertir.
     * @param toUnit     Unité vers laquelle convertir la quantité.
     * @return           La quantité convertie, ou la quantité originale si la conversion n'est pas possible.
     */
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
