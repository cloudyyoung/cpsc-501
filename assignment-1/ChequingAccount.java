public class ChequingAccount extends BankAccount {
    private double overdraftFee;
    private double overdraftAmount = 100.0; 

    public ChequingAccount(Customer holder, int accountNumber, double overdraftAmount, double overdraftFee) {
        super(holder, accountNumber);
        this.setOverdraftAmount(overdraftAmount);
        this.setOverdraftFee(overdraftFee);
    }
    
    public ChequingAccount(ChequingAccount copy) {
    	super(copy.getAccountHolder(), copy.getAccountNumber());
        this.setOverdraftAmount(copy.getOverdraftAmount());
        this.setOverdraftFee(copy.getOverdraftFee());
    }
    
    @Override
	protected void setBalance(double balance) {
		if (balance >= -this.overdraftFee) super.setBalance(balance);
	}
 
    private void setOverdraftFee(double fee) {
    	this.overdraftFee = (fee >= 0.0) ? fee : 1.0;
    }
    
    public double getOverdraftFee() { 
    	return this.overdraftFee;
    }
    
    private void setOverdraftAmount(double overdraftAmount){
        this.overdraftAmount = overdraftAmount;
    }
    
    public double getOverdraftAmount() {
    	return this.overdraftAmount;
    }
    
    @Override
    protected double getAvailableBalance() {
    	return this.getBalance() + this.overdraftAmount;
    }
    
    public void withdraw(double amount) {
    	if (this.sufficientFunds(amount)) {
            this.setBalance(this.getBalance() - amount);
        }else {
        	this.setBalance(this.getBalance() - amount - overdraftFee);
        }
    }
    
    protected double getMonthlyFeesAndInterest() {
    	if (this.getBalance() < 0.0) {
    		return 0.2 * this.getBalance();
    	}
    	return 0.0;
    }
    
    public ChequingAccount copy() {
    	return new ChequingAccount(this);
    }
}