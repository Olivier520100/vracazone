public class Pot extends Contenant implements Comparable{
    public Pot(double capacity, double quantityUsed, int containerNumber, int codeProduit) {
        this.quantityUsed = quantityUsed;
        this.capacity = capacity;
        this.containerNumber = containerNumber;
        this.codeProduit = codeProduit;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Pot) {
            Pot potComparaison = (Pot) o;

            int quantityComparison = Double.compare(potComparaison.quantityUsed, this.quantityUsed);
            if (quantityComparison != 0) {
                return quantityComparison;
            }

            return Integer.compare(this.containerNumber, potComparaison.containerNumber);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "" +
                containerNumber +
                "";
    }
}
