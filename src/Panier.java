import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

public class Panier implements Comparable{

    private String identificationTransaction;
    private String identifiantClient;
    private Long tempsDepuisUnixEpoch;
    private int nombreDeDifferentProduit;
    private int []codesProduit;
    private double []quantite;
    private String []unite;

    /**
     * Constructeur de la classe Panier.
     *
     * @param identificationTransaction L'identifiant de la transaction du panier.
     * @param identifiantClient         L'identifiant du client associé au panier.
     * @param tempsDepuisUnixEpoch      Le temps écoulé depuis l'Unix Epoch (en millisecondes).
     * @param nombreDeDifferentProduit  Le nombre de produits différents dans le panier.
     * @param codesProduit              Les codes des produits présents dans le panier.
     * @param quantite                  Les quantités des produits dans le panier.
     * @param unite                     Les unités de mesure des produits dans le panier.
     */
    public Panier(String identificationTransaction, String identifiantClient, Long tempsDepuisUnixEpoch,
                  int nombreDeDifferentProduit, int[] codesProduit, double[] quantite, String[] unite) {
        this.identificationTransaction = identificationTransaction;
        this.identifiantClient = identifiantClient;
        this.tempsDepuisUnixEpoch = tempsDepuisUnixEpoch;
        this.nombreDeDifferentProduit = nombreDeDifferentProduit;
        this.codesProduit = codesProduit;
        this.quantite = quantite;
        this.unite = unite;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du panier.
     *
     * @return Une chaîne de caractères représentant le panier.
     */
    @Override
    public String toString() {
        return "Panier{" +
                "identificationTransaction='" + identificationTransaction + '\'' +
                ", identifiantClient='" + identifiantClient + '\'' +
                ", tempsDepuisUnixEpoch=" + tempsDepuisUnixEpoch +
                ", nombreDeDifferentProduit=" + nombreDeDifferentProduit +
                ", codesProduit=" + Arrays.toString(codesProduit) +
                ", quantite=" + Arrays.toString(quantite) +
                ", unite=" + Arrays.toString(unite) +
                '}';
    }

    /**
     * Compare l'objet courant avec un autre objet de type Panier.
     *
     * @param o L'objet à comparer.
     * @return Une valeur négative, zéro ou une valeur positive si l'objet courant est respectivement inférieur, égal ou supérieur à l'objet spécifié.
     */
    public int compareTo(Object o) {
        Panier panierComparaison = (Panier) o;
        return Long.compare(this.tempsDepuisUnixEpoch, panierComparaison.tempsDepuisUnixEpoch);
    }
    public String getIdentificationTransaction() {
        return identificationTransaction;
    }

    public void setIdentificationTransaction(String identificationTransaction) {
        this.identificationTransaction = identificationTransaction;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    public Long getTempsDepuisUnixEpoch() {
        return tempsDepuisUnixEpoch;
    }

    public void setTempsDepuisUnixEpoch(Long tempsDepuisUnixEpoch) {
        this.tempsDepuisUnixEpoch = tempsDepuisUnixEpoch;
    }

    public int getNombreDeDifferentProduit() {
        return nombreDeDifferentProduit;
    }

    public void setNombreDeDifferentProduit(int nombreDeDifferentProduit) {
        this.nombreDeDifferentProduit = nombreDeDifferentProduit;
    }

    public int[] getCodesProduit() {
        return codesProduit;
    }

    public void setCodesProduit(int[] codesProduit) {
        this.codesProduit = codesProduit;
    }

    public double[] getQuantite() {
        return quantite;
    }

    public void setQuantite(double[] quantite) {
        this.quantite = quantite;
    }

    public String[] getUnite() {
        return unite;
    }

    public void setUnite(String[] unite) {
        this.unite = unite;
    }
}
