import javax.xml.crypto.Data;
import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;

/**
 * Classe principale du programme.
 */
public class Main {
    public static void main(String[] args) {
        Database database = new Database();

        // Importer les fichiers clients.dat, produits.dat et paniers.bin dans la base de données
        database.importFiles("./database/clients.dat", "./database/produits.dat", "./database/paniers.bin");

        // Générer les factures pour les paniers
        database.generateFactures();

        FichierGenerateur fichierGenerateur = new FichierGenerateur();

        // Générer les fichiers de factures
        FichierGenerateur.genererFactures(database.getFacturePanierArrayList());

        Entrepot entrepot = new Entrepot();

        // Démarrer la livraison en utilisant les paniers de la base de données et les produits associés
        entrepot.demarrerLivraison((LinkedHashMap<String, Panier>) database.getPanierHashMap(),
                (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());

        // Générer le registre des colis de l'entrepôt
        FichierGenerateur.genererRegistre(entrepot.getEntrepotColis());

        Bilan bilan = new Bilan();

        // Calculer le bilan en utilisant les colis de l'entrepôt et les produits associés de la base de données
        bilan.calculerBilan(entrepot.getEntrepotColis(),
                (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());

        // Générer le fichier de bilan
        FichierGenerateur.genererBilan(bilan.getProduitCoutHashMap(),
                (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());

        // Générer les fichiers de livraison en utilisant les paniers de la base de données et les clients associés
        FichierGenerateur.genererLivraison((LinkedHashMap<String, Panier>) database.getPanierHashMap(),
                (LinkedHashMap<String, Client>) database.getClientHashMap());
    }
}
