import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

public class Panier {

    private String identificationTransaction;
    private String identifiantClient;
    private Long tempsDepuisUnixEpoch;
    private int nombreDeDifferentProduit;
    private int []codesProduit;
    private double []quantite;
    private String []unite;

    public Panier(String identificationTransaction, String identifiantClient, Long tempsDepuisUnixEpoch, int nombreDeDifferentProduit, int[] codesProduit, double[] quantite, String[] unite) {
        this.identificationTransaction = identificationTransaction;
        this.identifiantClient = identifiantClient;
        this.tempsDepuisUnixEpoch = tempsDepuisUnixEpoch;
        this.nombreDeDifferentProduit = nombreDeDifferentProduit;
        this.codesProduit = codesProduit;
        this.quantite = quantite;
        this.unite = unite;
    }

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
