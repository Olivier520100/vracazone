/**
 * Classe représentant un pot qui hérite de la classe Contenant et implémente l'interface Comparable.
 */
public class Pot extends Contenant implements Comparable {
    /**
     * Constructeur de la classe Pot.
     *
     * @param capacity        La capacité du pot.
     * @param quantityUsed    La quantité utilisée dans le pot.
     * @param containerNumber Le numéro du contenant.
     * @param codeProduit     Le code du produit associé au pot.
     */
    public Pot(double capacity, double quantityUsed, int containerNumber, int codeProduit) {
        this.quantityUsed = quantityUsed;
        this.capacity = capacity;
        this.containerNumber = containerNumber;
        this.codeProduit = codeProduit;
    }

    /**
     * Compare l'objet courant avec un autre objet de type Pot.
     *
     * @param o L'objet à comparer.
     * @return Une valeur négative, zéro ou une valeur positive si l'objet courant est respectivement inférieur, égal ou supérieur à l'objet spécifié.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Pot potComparaison) {
            // Comparaison des quantités utilisées des pots
            int quantityComparison = Double.compare(potComparaison.quantityUsed, this.quantityUsed);
            if (quantityComparison != 0) {
                return quantityComparison;
            }

            // Comparaison des numéros de contenant
            return Integer.compare(this.containerNumber, potComparaison.containerNumber);
        }
        return 0;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du pot.
     *
     * @return Une chaîne de caractères représentant le numéro du contenant.
     */
    @Override
    public String toString() {
        return String.valueOf(containerNumber);
    }
}
