/**
 * Classe représentant un sachet qui hérite de la classe Contenant et implémente l'interface Comparable.
 */
public class Sachet extends Contenant implements Comparable {
    /**
     * Constructeur de la classe Sachet.
     *
     * @param capacity        La capacité du sachet.
     * @param quantityUsed    La quantité utilisée dans le sachet.
     * @param containerNumber Le numéro du contenant.
     * @param codeProduit     Le code du produit associé au sachet.
     */
    public Sachet(double capacity, double quantityUsed, int containerNumber, int codeProduit) {
        this.quantityUsed = quantityUsed;
        this.capacity = capacity;
        this.containerNumber = containerNumber;
        this.codeProduit = codeProduit;
    }

    /**
     * Compare l'objet courant avec un autre objet de type Sachet.
     *
     * @param o L'objet à comparer.
     * @return Une valeur négative, zéro ou une valeur positive si l'objet courant est respectivement inférieur, égal ou supérieur à l'objet spécifié.
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Sachet potComparaison) {
            // Comparaison des quantités utilisées des sachets
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
     * Retourne une représentation sous forme de chaîne de caractères du sachet.
     *
     * @return Une chaîne de caractères représentant le numéro du contenant.
     */
    @Override
    public String toString() {
        return String.valueOf(containerNumber);
    }
}