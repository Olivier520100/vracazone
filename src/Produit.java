public class Produit {

    private int codeProduit;
    private String description;
    private boolean produitAlimentaire;
    private boolean isSolide;
    private double prixCoutant;
    private double prixUnitaire;
    private String unite;
    private double  quantiteMaximale;

    public Produit(Integer codeProduit, String description, Boolean produitAlimentaire, Boolean isSolide, Double prixCoutant, Double prixUnitaire, String unite, Double quantiteMaximale) {
        this.codeProduit = codeProduit;
        this.description = description;
        this.produitAlimentaire = produitAlimentaire;
        this.isSolide = isSolide;
        this.prixCoutant = prixCoutant;
        this.prixUnitaire = prixUnitaire;
        this.unite = unite;
        this.quantiteMaximale = quantiteMaximale;
    }

    public Integer getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(Integer codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getProduitAlimentaire() {
        return produitAlimentaire;
    }

    public void setProduitAlimentaire(Boolean produitAlimentaire) {
        this.produitAlimentaire = produitAlimentaire;
    }

    public Boolean getSolide() {
        return isSolide;
    }

    public void setSolide(Boolean solide) {
        isSolide = solide;
    }

    public Double getPrixCoutant() {
        return prixCoutant;
    }

    public void setPrixCoutant(Double prixCoutant) {
        this.prixCoutant = prixCoutant;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Double getQuantiteMaximale() {
        return quantiteMaximale;
    }

    public void setQuantiteMaximale(Double quantiteMaximale) {
        this.quantiteMaximale = quantiteMaximale;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "codeProduit=" + codeProduit +
                ", description='" + description + '\'' +
                ", produitAlimentaire=" + produitAlimentaire +
                ", isSolide=" + isSolide +
                ", prixCoutant=" + prixCoutant +
                ", prixUnitaire=" + prixUnitaire +
                ", unite='" + unite + '\'' +
                ", quantiteMaximale=" + quantiteMaximale +
                '}';
    }


}
