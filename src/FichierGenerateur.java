import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe responsable de la génération des fichiers de sortie.
 */
public class FichierGenerateur {
    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");

    /**
     * Génère le fichier de factures à partir de la liste des factures de panier.
     *
     * @param facturePanierArrayList La liste des factures de panier.
     */
    public static void genererFactures(ArrayList<FacturePanier> facturePanierArrayList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("factures.txt"))) {
            for (FacturePanier facturePanier : facturePanierArrayList) {
                // Écriture des informations de facture dans le fichier
                writer.write(String.format("%-15s|%-20s| %-10.2f$| %-10.2f$| %-10.2f$|%n",
                        facturePanier.getIdTransaction(),
                        facturePanier.getDateTime(),
                        facturePanier.getSousTotal(),
                        facturePanier.getTaxes(),
                        facturePanier.getTotal()));
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier.");
        }
    }

    /**
     * Génère le fichier de bilan à partir des données du bilan et de la table de hachage des produits.
     *
     * @param bilan                   Les données du bilan Tree map pour l'ordre des cles.
     * @param produitLinkedHashMap    LinkedHashTable (pour l'ordre) des produits.
     */
    public static void genererBilan(TreeMap<Integer, double[]> bilan, LinkedHashMap<Integer, Produit> produitLinkedHashMap) {
        double[] totaux = {0.0, 0.0, 0.0, 0.0};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bilan.txt"))) {
            // Écriture de l'en-tête du bilan
            writer.write(String.format("%-40s %-10s %-10s %-10s %-10s%n", "PRODUITS", "VENTES", "COÛTANT", "EMBALLAGE", "PROFITS"));
            System.out.print(String.format("%-40s %-10s %-10s %-10s %-10s%n", "PRODUITS", "VENTES", "COÛTANT", "EMBALLAGE", "PROFITS"));

            // Parcours des données du bilan
            bilan.forEach((key, value) -> {
                // Mise à jour des totaux
                totaux[0] += value[0];
                totaux[1] += value[1];
                totaux[2] += value[2];
                totaux[3] += value[3];

                try {
                    // Écriture des informations de chaque produit dans le fichier
                    writer.write(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n",
                            produitLinkedHashMap.get(key).getDescription(),
                            value[0],
                            value[1],
                            value[2],
                            value[3]));
                    System.out.print(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n",
                            produitLinkedHashMap.get(key).getDescription(),
                            value[0],
                            value[1],
                            value[2],
                            value[3]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Écriture de la ligne de total
            writer.write("-------------------------------------------------------------------- \n");
            System.out.println("--------------------------------------------------------------------");
            writer.write(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n", "TOTAL", totaux[0], totaux[1], totaux[2], totaux[3]));
            System.out.print(String.format("%-40s %-10.2f %-10.2f %-10.2f %-10.2f%n", "TOTAL", totaux[0], totaux[1], totaux[2], totaux[3]));

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier.");
        }
    }

    /**
     * Génère le fichier de registre à partir de la file d'attente des colis.
     *
     * @param genererRegistre La file des colis.
     */
    public static void genererRegistre(Queue<Colis> genererRegistre) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registre.txt"))) {
            for (Colis colis : genererRegistre) {
                // Écriture des informations de chaque colis dans le fichier
                writer.write(colis.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier.");
        }
    }

    /**
     * Génère le fichier de livraison à partir de la table de hachage des paniers et de la table de hachage des clients.
     *
     * @param panierLinkedHashMap    LinkedHashTable (pour l'ordre) des paniers.
     * @param clientLinkedHashMap    LinkedHashTable (pour l'ordre) des clients.
     */
    public static void genererLivraison(LinkedHashMap<String, Panier> panierLinkedHashMap, LinkedHashMap<String, Client> clientLinkedHashMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("livraison.txt"))) {
            panierLinkedHashMap.forEach((key, value) -> {
                try {
                    // Écriture des informations de chaque livraison dans le fichier
                    writer.write(panierLinkedHashMap.get(key).getIdentificationTransaction() + "|" +
                            panierLinkedHashMap.get(key).getIdentifiantClient() + "|" +
                            clientLinkedHashMap.get(panierLinkedHashMap.get(key).getIdentifiantClient()).getNomClient() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier.");
        }
    }
}
