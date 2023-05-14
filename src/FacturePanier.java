import java.text.DecimalFormat;

public class FacturePanier {
    private final String idTransaction;
    private final String dateTime;
    private final double sousTotal;
    private final double taxes;
    private final double total;

    public String getIdTransaction() {
        return idTransaction;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getSousTotal() {
        return sousTotal;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getTotal() {
        return total;
    }

    public FacturePanier(String idTransaction, String dateTime, double sousTotal, double taxes, double total) {
        this.idTransaction = idTransaction;
        this.dateTime = dateTime;
        this.sousTotal = sousTotal;
        this.taxes = taxes;
        this.total = total;
    }

    }