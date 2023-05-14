import java.util.ArrayList;
import java.util.Stack;

public class Colis {
    private ArrayList<Pot> listePot = new ArrayList<>();
    private Stack<Sachet> stackSachet = new Stack<>();
    private String identificationTransaction;


    public Colis(ArrayList<Pot> listePot, Stack<Sachet> stackSachet, String identificationTransaction) {
        this.listePot = listePot;
        this.stackSachet = stackSachet;
        this.identificationTransaction = identificationTransaction;
    }

    @Override
    public String toString() {
        return identificationTransaction + "\n" +
                "\t Sachets: " + stackSachet + "\n" +
                "\t Pots: " + listePot + "\n";

    }

    public ArrayList<Pot> getListePot() {
        return listePot;
    }

    public void setListePot(ArrayList<Pot> listePot) {
        this.listePot = listePot;
    }

    public Stack<Sachet> getStackSachet() {
        return stackSachet;
    }

    public void setStackSachet(Stack<Sachet> stackSachet) {
        this.stackSachet = stackSachet;
    }

    public String getIdentificationTransaction() {
        return identificationTransaction;
    }

    public void setIdentificationTransaction(String identificationTransaction) {
        this.identificationTransaction = identificationTransaction;
    }
}
