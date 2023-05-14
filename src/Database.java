import javax.swing.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database implements Unit{


    private LinkedHashMap<String, Client> clientHashMap = new LinkedHashMap<String, Client>();
    // HashMap pour stocker les produits avec leur code produit comme clé
    private LinkedHashMap<Integer, Produit> produitHashMap = new LinkedHashMap<Integer, Produit>();

    // HashMap pour stocker les paniers avec leur code transaction comme clé
    private LinkedHashMap<String, Panier> panierHashMap = new LinkedHashMap<String, Panier>();
    private ArrayList<FacturePanier> facturePanierArrayList = new ArrayList<>();

    private boolean dataLoaded = false;

    public void importFiles(String fichierClients, String fichierProduits, String fichierPaniers) {
        loadClients(fichierClients);
        loadProduits(fichierProduits);
        loadPaniers(fichierPaniers);
    }

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
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
        }
    }

    public void loadProduits(String fichier) {
        // Thread pour lire les données des produits à partir du fichier produits.dat
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
                    System.out.println("Erreur dans le fichier. Verifier" + description);
                } else if (!(unitMasse.containsKey(unite)) && !(unitVolume.containsKey(unite))) {
                    System.out.println("Unite inconnue pour " + description + "avec" + unite);
                } else if (prixCoutant >= ( prixUnitaire/1.2)) {
                    System.out.println("Erreur dans le fichier. Le prix coûtant doit être inférieur d'au moins 20% au prix unitaire.");
                } else {
                    // Créer un nouvel objet produit et l'ajouter au HashMap
                    produitObjectPointer = new Produit(codeProduit, description, produitAlimentaire, isSolide, prixCoutant, prixUnitaire, unite, quantiteMaximale);
                    produitHashMap.put(codeProduit, produitObjectPointer);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier client non trouvé");
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
        }
    }

    public HashMap<String, Client> getClientHashMap() {
        return clientHashMap;
    }

    public HashMap<String, Panier> getPanierHashMap() {
        return panierHashMap;
    }

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
            String messageErreur = ("PANIERS REJETÉS \n") + ("================================================= \n");
            // Boucle pour lire chaque produit du fichier produits.dat
            while (0 < fichierPanier.available()) {
                errorType = 0;
                identificationTransaction = fichierPanier.readUTF();
                identifiantClient = fichierPanier.readUTF();
                tempsDepuisUnixEpoch = fichierPanier.readLong();
                nombreDeDifferentProduit = fichierPanier.readInt();

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

                } else {
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
                                codeProduitAvecErreur = currentCodeProduit;
                            }

                        }
                        conteurProduit++;
                    }

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
                        messageErreur +=(identificationTransaction + " Quantité non autorisée (produit " + codeProduitAvecErreur + ").\n");
                        break;
                    default:
                        System.out.println("Erreur : Type d'erreur inconnu.");
                        System.out.println("Erreur dans :" + identificationTransaction);
                        break;
                }
            }
            messageErreur += ("=================================================");
            if (erreurDansFichier == true) {
                System.out.println(messageErreur);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier paniers non trouvé");
        } catch (IOException e) {
            System.out.println("Erreur d'entrées-sorties");
        }
    }



    public void generateFactures() {

        final double TAUX_TAXE = 0.15;
        for (Panier panier : panierHashMap.values()) {
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
            facturePanierArrayList.add(new FacturePanier(idTransaction,dateTime,sousTotal,taxes,total));
        }
    }

    public void printData() {

        ArrayList<Panier> panierList = new ArrayList<>(panierHashMap.values());



        for (Panier panier : panierList) {
            System.out.println(panier);
            System.out.println(); // prints a newline character
        }
    }
    public String unixATimeStamp(long tempsUnix){
        String dateTexte;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTexte = formatDate.format(new Date(tempsUnix * 1000L));
        return dateTexte;
    }
    public double convertUnits(double quantity, String fromUnit, String toUnit) {
        double result = quantity;

        // Check if both units are mass units
        if (unitMasse.containsKey(fromUnit) && unitMasse.containsKey(toUnit)) {
            double fromFactor = unitMasse.get(fromUnit);
            double toFactor = unitMasse.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        }
        else if (unitVolume.containsKey(fromUnit) && unitVolume.containsKey(toUnit)) {
            double fromFactor = unitVolume.get(fromUnit);
            double toFactor = unitVolume.get(toUnit);
            result = (quantity * fromFactor) / toFactor;
        }
        else {
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

