import java.util.ArrayList;

public abstract class BankAccount {
    private int accountNumber;
    private double balance;
    private Customer holder;
    private ArrayList<Transaction> transactions;
    
    public BankAccount(Customer holder, int accountNumber) {
        this.setAccountHolder(holder);
        this.accountNumber = accountNumber;
    }
    
    public BankAccount(BankAccount copy){
        this.setBalance(copy.getBalance());
        this.accountNumber = copy.accountNumber;
        this.holder = copy.holder;
    }
    
    protected abstract double getMonthlyFeesAndInterest();
    
    protected boolean sufficientFunds(double amount) {
        return amount > 0.0 && amount <= this.getAvailableBalance();
    }

    public Customer getAccountHolder() {
        return new Customer(this.holder);
    }
    
    private void setAccountHolder(Customer holder) {
    	this.holder = new Customer(holder);
    }
    
    public double getBalance() {
        return this.balance;
    }
    
	private void setBalance(double balance) {
		if (balance >= 0) this.balance = balance;
	}
    
    protected double getAvailableBalance() {
    	return this.getBalance();
    }

    public void deposit(double amount) {
        if (amount > 0) this.setBalance(this.getBalance() + amount);
    }
    
    public void withdraw(double amount) {
        if (this.sufficientFunds(amount)) {
            this.setBalance(this.getBalance() - amount);
        }
    }
    
    public void transfer(double amount, BankAccount receipient) {
        if (this.sufficientFunds(amount)) {
            this.withdraw(amount);
            receipient.deposit(amount);
        }
    }
    
    public int getAccountNumber() {
        return this.accountNumber;
    }
    
    public String toString() {
        return "(" + this.holder.toString() + ") " + this.accountNumber + ": " + this.getBalance();
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof BankAccount){
            BankAccount obj = (BankAccount) object;
            if(obj.getAccountNumber() == this.accountNumber){
                return true;
            }
        }
        return false;
    }

	public ArrayList<Transaction> getTransactions() {
		return new ArrayList<Transaction>(this.transactions);
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(new Transaction(transaction));
	}


}