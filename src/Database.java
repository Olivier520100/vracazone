import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Database implements Unit {
    // HashMap pour stocker les clients avec leur identifiant comme clé
    HashMap<String, Client> clientHashMap = new HashMap<String, Client>();
    // HashMap pour stocker les produits avec leur code produit comme clé
    HashMap<Integer, Produit> produitHashMap = new HashMap<Integer, Produit>();
    // HashMap pour stocker les produits avec leur code transaction comme clé

    HashMap<Integer, Panier> panierHashMap = new HashMap<Integer, Panier>();

    /**
     * Constructeur de la classe Database.
     * Ce constructeur initialise les données des clients et des produits à partir des fichiers clients.dat et produits.dat.
     */
    public Database() {

        // Bloc try-catch pour gérer les exceptions liées aux fichiers et aux entrées-sorties
        try (DataInputStream fichierClient = new DataInputStream(new FileInputStream(("./database/clients.dat")))) {
            String identifianClient;
            String nomClient;
            Client clientObjectPointer;
            // Boucle pour lire chaque client du fichier clients.dat
            while (0 < fichierClient.available()) {
                identifianClient = fichierClient.readUTF();
                nomClient = fichierClient.readUTF();
                // Vérifier si l'identifiant du client existe déjà dans le HashMap
                if (clientHashMap.containsKey(identifianClient)) {
                    System.out.println("Erreur dans le fichier");
                } else {
                    // Créer un nouvel objet client et l'ajouter au HashMap
                    clientObjectPointer = new Client(identifianClient, nomClient);
                    clientHashMap.put(identifianClient, clientObjectPointer);
                    System.out.println(clientObjectPointer);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier client non trouvé");
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
        }
        System.out.println(clientHashMap);


        // Thread pour lire les données des produits à partir du fichier produits.dat

        try (DataInputStream fichierProduit = new DataInputStream(new FileInputStream(("./database/produits.dat")))) {
            int codeProduit;
            String description;
            boolean produitAlimentaire;
            boolean isSolide;
            double prixCoutant;
            double prixUnitaire;
            String unite;
            double quantiteMaximale;
            Produit produitObjectPointer;
            // Boucle pour lire chaque produit du fichier produits.dat
            while (0 < fichierProduit.available()) {
                codeProduit = fichierProduit.readInt();
                description = fichierProduit.readUTF();
                produitAlimentaire = fichierProduit.readBoolean();
                isSolide = fichierProduit.readBoolean();
                prixCoutant = fichierProduit.readDouble();
                prixUnitaire = fichierProduit.readDouble();
                unite = fichierProduit.readUTF();
                quantiteMaximale = fichierProduit.readDouble();
                // Vérifier si le code produit existe déjà dans le HashMap
                if (produitHashMap.containsKey(codeProduit)) {
                    System.out.println("Erreur dans le fichier. Verifier" + description);
                } else if (!(Unit.unitMasse.contains(unite)) && !(Unit.unitVolume.contains(unite))) {
                    System.out.println("Unite inconnue pour " + description + "avec" + unite);
                } else {
                    // Créer un nouvel objet produit et l'ajouter au HashMap
                    produitObjectPointer = new Produit(codeProduit, description, produitAlimentaire, isSolide, prixCoutant, prixUnitaire, unite, quantiteMaximale);
                    System.out.println(produitObjectPointer);
                    produitHashMap.put(codeProduit, produitObjectPointer);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier client non trouvé");
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
        }
        System.out.println(produitHashMap);




    }
}