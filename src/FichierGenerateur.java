
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
public class FichierGenerateur {
    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#0.00");
    public static void genererFactures(ArrayList<FacturePanier> facturePanierArrayList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("factures.txt"))) {
            for (FacturePanier facturePanier : facturePanierArrayList) {
                System.out.println(facturePanier.toString());
                writer.write(facturePanier.toString());
                writer.newLine();
            }
        } catch (IOException e) {
                    System.err.println("An error occurred while writing to the file.");
        }
    }
}
