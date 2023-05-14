public class Sachet extends Contenant implements Comparable{
    public Sachet(double capacity, double quantityUsed, int containerNumber, int codeProduit) {
        this.quantityUsed = quantityUsed;
        this.capacity = capacity;
        this.containerNumber = containerNumber;
        this.codeProduit = codeProduit;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Sachet potComparaison) {

            int quantityComparison = Double.compare(potComparaison.quantityUsed, this.quantityUsed);
            if (quantityComparison != 0) {
                return quantityComparison;
            }

            return Integer.compare(this.containerNumber, potComparaison.containerNumber);
        }
        return 0;
    }
    public String toString() {
        return String.valueOf(containerNumber);
    }
}
