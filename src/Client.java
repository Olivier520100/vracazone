/**
 * Classe représentant un client.
 */
public class Client {
    private String identifiantClient;
    private String nomClient;

    /**
     * Constructeur de la classe Client.
     *
     * @param identifiantClient   L'identifiant du client.
     * @param nomClient           Le nom du client.
     */
    public Client(String identifiantClient, String nomClient) {
        this.identifiantClient = identifiantClient;
        this.nomClient = nomClient;
    }

    /**
     * Retourne l'identifiant du client.
     *
     * @return L'identifiant du client.
     */
    public String getIdentifiantClient() {
        return identifiantClient;
    }

    /**
     * Définit l'identifiant du client.
     *
     * @param identifiantClient   L'identifiant du client.
     */
    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    /**
     * Retourne le nom du client.
     *
     * @return Le nom du client.
     */
    public String getNomClient() {
        return nomClient;
    }

    /**
     * Définit le nom du client.
     *
     * @param nomClient   Le nom du client.
     */
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Client.
     *
     * @return Une chaîne de caractères représentant le client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "identifiantClient='" + identifiantClient + '\'' +
                ", nomClient='" + nomClient + '\'' +
                '}';
    }
}
