import java.text.DecimalFormat;

public class FacturePanier {
    private String idTransaction;
    private String dateTime;
    private double sousTotal;
    private double taxes;
    private double total;

    private static final double TAUX_TAXE = 0.15;
    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");
    public FacturePanier(String idTransaction, String dateTime, double sousTotal, double taxes, double total) {
        this.idTransaction = idTransaction;
        this.dateTime = dateTime;
        this.sousTotal = sousTotal;
        this.taxes = taxes;
        this.total = total;
    }

    @Override
    public String toString() {
        DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");
        String sousTotalTexte = FORMAT_DECIMAL.format(this.sousTotal);
        String taxesTexte = FORMAT_DECIMAL.format(this.taxes);
        String totalTexte = FORMAT_DECIMAL.format(this.total);

        return idTransaction + "|" + dateTime + "|" + "$" + sousTotalTexte + "$|" + "$" + taxesTexte + "$|" + "$" + totalTexte + "$";
    }
}