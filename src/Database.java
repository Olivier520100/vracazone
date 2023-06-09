import javax.swing.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database implements Unit{


    // HashMap pour stocker les clients avec leur identifiant comme clé
    private final LinkedHashMap<String, Client> clientHashMap = new LinkedHashMap<String, Client>();
    // HashMap pour stocker les produits avec leur code produit comme clé
    private final LinkedHashMap<Integer, Produit> produitHashMap = new LinkedHashMap<Integer, Produit>();
    // HashMap pour stocker les paniers avec leur code transaction comme clé
    private final LinkedHashMap<String, Panier> panierHashMap = new LinkedHashMap<String, Panier>();
    private final ArrayList<FacturePanier> facturePanierArrayList = new ArrayList<>();

    private final boolean dataLoaded = false;

    /**
     * Importe les fichiers de données dans la base de données.
     *
     * @param fichierClients  Le fichier contenant les données des clients.
     * @param fichierProduits Le fichier contenant les données des produits.
     * @param fichierPaniers  Le fichier contenant les données des paniers.
     */
    public void importFiles(String fichierClients, String fichierProduits, String fichierPaniers) {
        loadClients(fichierClients);
        loadProduits(fichierProduits);
        loadPaniers(fichierPaniers);
    }

    /**
     * Charge les données des clients à partir du fichier spécifié.
     *
     * @param fichier Le fichier contenant les données des clients.
     */
    public void loadClients(String fichier) {
        try (DataInputStream fichierClient = new DataInputStream(new FileInputStream((fichier)))) {
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
                }
            }
        } catch (FileNotFoundException e) {

            System.out.println("Fichier client non trouvé");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
            System.exit(0);

        }
    }

    /**
     * Charge les données des produits à partir du fichier spécifié.
     *
     * @param fichier Le fichier contenant les données des produits.
     */
    public void loadProduits(String fichier) {
        try (DataInputStream fichierProduit = new DataInputStream(new FileInputStream((fichier)))) {
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
                    System.out.println("Erreur dans le fichier. Vérifiez " + description);
                } else if (!(unitMasse.containsKey(unite)) && !(unitVolume.containsKey(unite))) {
                    System.out.println("Unité inconnue pour " + description + " avec " + unite);
                } else if (!(prixUnitaire/1.2 >= (prixCoutant))) {
                    System.out.println("Erreur dans le fichier. Le prix coûtant doit être inférieur d'au moins 20% au prix unitaire.");
                } else {
                    // Créer un nouvel objet produit et l'ajouter au HashMap
                    produitObjectPointer = new Produit(codeProduit, description, produitAlimentaire, isSolide, prixCoutant, prixUnitaire, unite, quantiteMaximale);
                    produitHashMap.put(codeProduit, produitObjectPointer);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier produit non trouvé");
            System.exit(0);
        } catch (IOException e) {

            System.out.println("Erreur d'entrées-sorties");
            System.exit(0);
        }
    }

    // Méthodes pour accéder aux HashMaps

    /**
     * Retourne le HashMap des clients.
     *
     * @return Le HashMap des clients.
     */
    public LinkedHashMap<String, Client> getClientHashMap() {
        return clientHashMap;
    }

    /**
     * Retourne le HashMap des paniers.
     *
     * @return Le HashMap des paniers.
     */
    public LinkedHashMap<String, Panier> getPanierHashMap() {
        return panierHashMap;
    }
    /**
     * Charge les données des paniers à partir du fichier spécifié.
     *
     * @param fichier Le fichier contenant les données des paniers.
     */
    public void loadPaniers(String fichier) {
        try (DataInputStream fichierPanier = new DataInputStream(new FileInputStream((fichier)))) {

            String identificationTransaction;
            String identifiantClient;
            Long tempsDepuisUnixEpoch;
            int nombreDeDifferentProduit;
            ArrayList<Integer> codesProduit = new ArrayList<Integer>(0);
            ArrayList<Double> quantite = new ArrayList<Double>(0);
            ArrayList<String> unite = new ArrayList<String>(0);
            HashMap<Integer, Integer> produitAcheterHashMap = new HashMap<Integer, Integer>();
            Integer currentCodeProduit;
            Double currentQuantite;
            String currentUnite;
            int conteurProduit = 1;
            int indexProduit = 0;
            int errorType = 0;
            int codeProduitAvecErreur = 0;
            boolean erreurDansFichier = false;
            double quantiteMaxException = 0;
            String unitException = "";
            String messageErreur = ("PANIERS REJETÉS \n") + ("================================================= \n");
            // Boucle pour lire chaque produit du fichier produits.dat
            while (0 < fichierPanier.available()) {
                errorType = 0;
                identificationTransaction = fichierPanier.readUTF();
                identifiantClient = fichierPanier.readUTF();
                tempsDepuisUnixEpoch = fichierPanier.readLong();
                nombreDeDifferentProduit = fichierPanier.readInt();
                quantiteMaxException = 0;
                unitException ="";

                if (!clientHashMap.containsKey(identifiantClient)) {
                    errorType = 4;
                    erreurDansFichier = true;
                } else if (panierHashMap.containsKey(identificationTransaction)) {
                    errorType = 3;
                    erreurDansFichier = true;

                } else if (tempsDepuisUnixEpoch <= 0) {
                    errorType = 2;
                    erreurDansFichier = true;

                } else if (nombreDeDifferentProduit <= 0) {
                    errorType = 1;
                    erreurDansFichier = true;

                }
                conteurProduit = 0;
                indexProduit = 0;
                codesProduit.clear();
                quantite.clear();
                unite.clear();
                produitAcheterHashMap.clear();
                while (conteurProduit < nombreDeDifferentProduit) {
                    currentCodeProduit = fichierPanier.readInt();
                    currentQuantite = fichierPanier.readDouble();
                    currentUnite = fichierPanier.readUTF();
                    if (!produitHashMap.containsKey(currentCodeProduit)) {
                        errorType = 5;
                        erreurDansFichier = true;
                        codeProduitAvecErreur = currentCodeProduit;

                    } else if (!unitMasse.containsKey(currentUnite) && !unitVolume.containsKey(currentUnite)) {
                        errorType = 6;
                        erreurDansFichier = true;
                        codeProduitAvecErreur = currentCodeProduit;
                    } else {
                        if (!(produitAcheterHashMap.containsKey(currentCodeProduit))) {
                            produitAcheterHashMap.put(currentCodeProduit, indexProduit);
                            codesProduit.add(currentCodeProduit);
                            quantite.add(currentQuantite);
                            unite.add(currentUnite);
                            indexProduit+=1;
                        } else {
                            double valeurMiseAJour = quantite.get(produitAcheterHashMap.get(currentCodeProduit)) + convertUnits(currentQuantite, currentUnite, unite.get(produitAcheterHashMap.get(currentCodeProduit)));
                            quantite.set(produitAcheterHashMap.get(currentCodeProduit), valeurMiseAJour);
                        }
                        if (quantite.get(produitAcheterHashMap.get(currentCodeProduit)) > convertUnits(produitHashMap.get(currentCodeProduit).getQuantiteMaximale(), produitHashMap.get(currentCodeProduit).getUnite(), unite.get(produitAcheterHashMap.get(currentCodeProduit)))) {
                            errorType = 7;
                            erreurDansFichier = true;
                            quantiteMaxException = quantite.get(produitAcheterHashMap.get(currentCodeProduit));
                            unitException = unite.get(produitAcheterHashMap.get(currentCodeProduit));
                            codeProduitAvecErreur = currentCodeProduit;
                        }

                    }
                    conteurProduit++;
                }


            switch (errorType) {
                case 0:
                    // Ajouter les informations du panier à l'objet Panier
                    int[] codeProduitArray = codesProduit.stream().mapToInt(i -> i).toArray();
                    double[] quantiteArray = quantite.stream().mapToDouble(d -> d).toArray();
                    String[] uniteArray = unite.toArray(new String[0]);

                    for (int i = 0; i < codeProduitArray.length; i++) {
                        Produit produit = produitHashMap.get(codeProduitArray[i]);
                        double quantitePourConvertir = convertUnits(quantiteArray[i], uniteArray[i], produit.getUnite());
                        codeProduitArray[i] = produit.getCodeProduit();
                        quantiteArray[i] = quantitePourConvertir;
                        uniteArray[i] = produit.getUnite();
                    }

                    Panier panier = new Panier(identificationTransaction, identifiantClient, tempsDepuisUnixEpoch, nombreDeDifferentProduit, codeProduitArray, quantiteArray, uniteArray);
                    panierHashMap.put(identificationTransaction, panier);
                    break;
                case 1:

                    messageErreur += (identificationTransaction + " Le nombre de produit est invalide \n");

                    break;
                case 2:

                    messageErreur +=(identificationTransaction + " Le temps depuis Unix Epoch est invalid\n");

                    break;
                case 3:

                    messageErreur +=(identificationTransaction + " L'identification de transaction existe déjà.\n");

                    break;
                case 4:

                    messageErreur +=(identificationTransaction + " Identifiant de client invalide (" + identifiantClient + ").\n");

                    break;
                case 5:

                    messageErreur += (identificationTransaction + " Produit invalide (" + codeProduitAvecErreur + "). \n");
                    break;
                case 6:

                    messageErreur +=(identificationTransaction + " Unité inconnue.\n");
                    break;
                case 7:
                    messageErreur +=(identificationTransaction + " Quantité non autorisée de " + quantiteMaxException + " " + unitException + " (produit " + codeProduitAvecErreur + ").\n");
                    break;
                default:
                    System.out.println("Erreur : Type d'erreur inconnu.");
                    System.out.println("Erreur dans :" + identificationTransaction);
                    break;
                }
            }
            messageErreur += ("=================================================");
            if (erreurDansFichier) {
                System.out.println(messageErreur);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier paniers non trouvé");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
            System.exit(0);
        }
    }



    // Méthode pour générer les factures des paniers
    public void generateFactures() {
        final double TAUX_TAXE = 0.15;
        List<Panier> panierList = new ArrayList<Panier>(panierHashMap.values());
        panierList.sort(null);
        for (Panier panier : panierList) {
            String idTransaction = panier.getIdentificationTransaction();
            String dateTime = unixATimeStamp(panier.getTempsDepuisUnixEpoch() / 1000);
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
            facturePanierArrayList.add(new FacturePanier(idTransaction, dateTime, sousTotal, taxes, total));
        }
    }

    /**
     * Convertit un timestamp Unix en une chaîne de caractères au format yyyy-MM-dd HH:mm z.
     *
     * @param tempsUnix Le timestamp Unix.
     * @return La chaîne de caractères représentant la date et l'heure.
     */
    public String unixATimeStamp(long tempsUnix) {
        String dateTexte;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTexte = formatDate.format(new Date(tempsUnix * 1000L));
        return dateTexte;
    }

    /**
     * Convertit une quantité d'une unité à une autre.
     *
     * @param quantity  La quantité à convertir.
     * @param fromUnit  L'unité de départ.
     * @param toUnit    L'unité de destination.
     * @return La quantité convertie.
     */
    public double convertUnits(double quantity, String fromUnit, String toUnit) {
        double result = quantity;

        // Vérifier si les deux unités sont des unités de masse
        if (unitMasse.containsKey(fromUnit) && unitMasse.containsKey(toUnit)) {
            double fromFactor = unitMasse.get(fromUnit);
            double toFactor = unitMasse.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        } else if (unitVolume.containsKey(fromUnit) && unitVolume.containsKey(toUnit)) {
            double fromFactor = unitVolume.get(fromUnit);
            double toFactor = unitVolume.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        } else {
            System.out.println("Conversion Impossible");
        }

        return result;
    }

    public ArrayList<FacturePanier> getFacturePanierArrayList() {
        return facturePanierArrayList;
    }

    public HashMap<Integer, Produit> getProduitHashMap() {
        return produitHashMap;
    }
}

