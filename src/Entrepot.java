import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe Entrepot
 * Cette classe représente un entrepôt qui contient des colis et peut démarrer la livraison de ces colis.
 */
public class Entrepot {

    // Liste des colis dans l'entrepôt
    private ArrayList<Colis> entrepotColis = new ArrayList<>();

    /**
     * Cette méthode démarre le processus de livraison en utilisant un robot pour préparer les paniers
     * et ajoute ces paniers préparés à la liste des colis de l'entrepôt.
     *
     * @param panierHashMap   HashMap des paniers à livrer, mappés par leur identifiant.
     * @param produitHashMap  HashMap des produits disponibles, mappés par leur identifiant.
     */
    public void demarrerLivraison(HashMap<String, Panier> panierHashMap, HashMap<Integer, Produit> produitHashMap){

        // Création d'une instance de robot
        Robot robot = new Robot();

        // Création d'une liste de paniers à partir du HashMap et tri de cette liste
        ArrayList<Panier> panierList = new ArrayList<>(panierHashMap.values());
        panierList.sort(null);

        // Utilisation du robot pour préparer chaque panier et ajout de ces paniers à la liste des colis
        for (Panier panier : panierList) {
            entrepotColis.add(robot.preparationPanier(panier, produitHashMap));
        }

        // Affichage des informations de chaque colis
        for (Colis colis : entrepotColis) {
            System.out.println(colis);
        }
    }
}
