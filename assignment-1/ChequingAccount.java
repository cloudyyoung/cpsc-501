public class ChequingAccount extends BankAccount{
    private double overdraftFee;
    private double overdraftAmount = 100.0; 

    public ChequingAccount(Customer holder, int accountNumber, double overdraftAmount, double overdraftFee) {
        super(holder, accountNumber);
        this.setOverdraftAmount(overdraftAmount);
        this.setOverdraftFee(overdraftFee);
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
    
    @Override
    public void withdraw(double amount) {
		super.withdraw(amount + overdraftFee);
    }
    
    protected double getMonthlyFeesAndInterest() {
    	if (getBalance() < 0.0) {
    		return 0.2 * getBalance();
    	}
    	return 0.0;
    }
}