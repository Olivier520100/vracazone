
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FichierGenerateur {
    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");
    public static void genererFactures(ArrayList<FacturePanier> facturePanierArrayList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("factures.txt"))) {
            for (FacturePanier facturePanier : facturePanierArrayList) {
                writer.write(String.format("%-15s|%-20s| %-10.2f$| %-10.2f$| %-10.2f$|%n", facturePanier.getIdTransaction(), facturePanier.getDateTime(), facturePanier.getSousTotal(), facturePanier.getTaxes(), facturePanier.getTotal()));
            }
        } catch (IOException e) {
                    System.err.println("An error occurred while writing to the file.");
        }
    }
    public static void genererBilan(TreeMap<Integer,double[]> bilan, LinkedHashMap<Integer, Produit> produitLinkedHashMap){
        double[] totaux = {0.0,0.0,0.0,0.0};
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bilan.txt"))) {
            writer.write(String.format("%-40s %-10s %-10s %-10s %-10s%n", "PRODUITS", "VENTES", "COÛTANT", "EMBALLAGE", "PROFITS"));
            bilan.forEach((key, value) -> {
                totaux[0]+= value[0];
                totaux[1]+= value[1];
                totaux[2]+= value[2];
                totaux[3]+= value[3];
                try {
                    writer.write(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n", produitLinkedHashMap.get(key).getDescription(), value[0], value[1], value[2], value[3]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.write("-------------------------------------------------------------------- \n");
            writer.write(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n", "TOTAL", totaux[0], totaux[1], totaux[2], totaux[3]));

        } catch (IOException e) {
            System.err.println("Erreur en ecrivant le fichier.");
        }
    }
    public static void genererRegistre(Queue<Colis> genererRegistre){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registre.txt"))) {
            for (Colis colis : genererRegistre) {
                writer.write(colis.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
        }
    }

    public static void genererLivraison(LinkedHashMap<String,Panier> panierLinkedHashMap, LinkedHashMap<String, Client> clientLinkedHashMap){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("livraison.txt"))) {
            panierLinkedHashMap.forEach((key, value) -> {

                try {
                    writer.write(panierLinkedHashMap.get(key).getIdentificationTransaction()+"|"+panierLinkedHashMap.get(key).getIdentifiantClient()+"|"+clientLinkedHashMap.get(panierLinkedHashMap.get(key).getIdentifiantClient()).getNomClient()+"\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
        }
    }
}
