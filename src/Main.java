import javax.xml.crypto.Data;
import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        database.importFiles("./database/clients.dat","./database/produits.dat", "./database/paniers.bin");
        database.generateFactures();
        FichierGenerateur fichierGenerateur = new FichierGenerateur();
        FichierGenerateur.genererFactures(database.getFacturePanierArrayList());
        Entrepot entrepot = new Entrepot();
        entrepot.demarrerLivraison((LinkedHashMap<String, Panier>) database.getPanierHashMap(), (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());
        FichierGenerateur.genererRegistre(entrepot.getEntrepotColis());
        Bilan bilan = new Bilan();
        bilan.calculerBilan(entrepot.getEntrepotColis(), (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());
        FichierGenerateur.genererBilan(bilan.getProduitCoutHashMap(), (LinkedHashMap<Integer, Produit>) database.getProduitHashMap());
        FichierGenerateur.genererLivraison((LinkedHashMap<String, Panier>) database.getPanierHashMap(), (LinkedHashMap<String, Client>) database.getClientHashMap());
    }
}