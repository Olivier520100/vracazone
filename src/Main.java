import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Database: \n");
        Database database = new Database();
        database.importFiles("./database/clients.dat", "./database/produits.dat","./database/paniers.bin");
        FichierGenerateur fichierGenerateur = new FichierGenerateur();
        System.out.println("\n");
        database.generateFactures();
        fichierGenerateur.genererFactures(database.getFacturePanierArrayList());
        System.out.println("\n");
        System.out.println("Robot: \n");

        Entrepot entrepot = new Entrepot();
        Map<String, Panier> paniers = database.getPanierHashMap();
        Map<Integer, Produit> produits = database.getProduitHashMap();
        entrepot.demarrerLivraison((LinkedHashMap<String, Panier>) paniers, (LinkedHashMap<Integer, Produit>) produits);
        System.out.println("\n");
        System.out.println("Bilan: \n");
        fichierGenerateur.genererRegistre(entrepot.getEntrepotColis());
        System.out.println("\n");
        Bilan bilan = new Bilan();
        bilan.calculerBilan(entrepot.getEntrepotColis(), (LinkedHashMap<Integer, Produit>) produits);
        fichierGenerateur.genererBilan(bilan.getProduitCoutHashMap(), (LinkedHashMap<Integer, Produit>) produits);
        System.out.println("\n");

        Map<String, Client> clients = database.getClientHashMap();
        fichierGenerateur.genererLivraison((LinkedHashMap<String, Panier>) paniers, (LinkedHashMap<String, Client>) clients);


    }
}
