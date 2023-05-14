import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {

        Database database = new Database();
        database.importFiles("./database/clients.dat","./database/produits.dat", "./database/paniers.bin");
        database.generateFactures();
        FichierGenerateur fichierGenerateur = new FichierGenerateur();
        fichierGenerateur.genererFactures(database.getFacturePanierArrayList());
        Entrepot entrepot = new Entrepot();
        entrepot.demarrerLivraison(database.getPanierHashMap(), database.getProduitHashMap());


    }
}