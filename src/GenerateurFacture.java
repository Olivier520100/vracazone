
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
public class GenerateurFacture {
    private static final double TAUX_TAXE = 0.15;
    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");

    public static void genererFactures(HashMap<String, Panier> panierHashMap, HashMap<Integer, Produit> produitHashMap){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("factures.txt"))) {
            for (Panier panier : panierHashMap.values()) {
                String idTransaction = panier.getIdentificationTransaction();
                String dateTime = unixVersHorodatage(panier.getTempsDepuisUnixEpoch()/1000);
                double sousTotal = 0.0;
                double taxes = 0.0;

                for (int i = 0; i < panier.getQuantite().length; i++) {
                    Produit produit = produitHashMap.get(panier.getCodesProduit()[i]);
                    double quantite = panier.getQuantite()[i];
                    double prixUnitaire = produit.getPrixUnitaire();

                    sousTotal += quantite * prixUnitaire;
                    if (!produit.getProduitAlimentaire()) {
                        taxes += quantite * prixUnitaire * TAUX_TAXE;
                    }
                }

                double total = sousTotal + taxes;
                String sousTotalTexte = FORMAT_DECIMAL.format(sousTotal);
                String taxesTexte = FORMAT_DECIMAL.format(taxes);
                String totalTexte = FORMAT_DECIMAL.format(total);

                String ligneFacture = idTransaction + "|" + dateTime + "|" + "$" + sousTotalTexte + "$|" + "$" + taxesTexte + "$|" + "$" + totalTexte + "$";
                System.out.println(ligneFacture);
                writer.write(ligneFacture);
                writer.newLine();
            }
        } catch (IOException e) {
                    System.err.println("An error occurred while writing to the file.");
                    e.printStackTrace();
        }
    }

    private static String unixVersHorodatage(long tempsUnix) {
        String dateTexte;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTexte = formatDate.format(new Date(tempsUnix * 1000L));
        return dateTexte;
    }
}
