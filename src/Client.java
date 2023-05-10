public class Client {
    private String identifiantClient;
    private String nomClient;

    public Client(String identifiantClient, String nomClient) {
        this.identifiantClient = identifiantClient;
        this.nomClient = nomClient;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "identifianClient='" + identifiantClient + '\'' +
                ", nomClient='" + nomClient + '\'' +
                '}';
    }


}
