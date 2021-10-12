import java.util.ArrayList;
import java.util.HashSet;

public class Bank{

    private HashSet<BankAccount> accounts = new HashSet<BankAccount>();
    
    public void addAccount(BankAccount account) {
    	this.accounts.add(account.copy());
    }

    public ArrayList<BankAccount> getAccounts(){
    	ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
    	for(BankAccount ca : this.accounts) {
    		accounts.add(ca.copy());
    	}
        return accounts;
    }
}