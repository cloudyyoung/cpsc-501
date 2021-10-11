import java.util.ArrayList;
import java.util.HashSet;

public class Bank{

    private HashSet<BankAccount> accounts = new HashSet<BankAccount>();

    public void addAccount(BankAccount add){
        this.accounts.add(add);
    }

    public ArrayList<BankAccount> getAccounts(){
        return new ArrayList<BankAccount>(this.accounts);
    }
}