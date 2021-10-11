public class SavingsAccount extends BankAccount {
	private double annualInterestRate = 0.05;
	private double minimumBalance = 0;
	
	public SavingsAccount(Customer holder, int accountNumber, double annualInterestRate, double minimumBalance) {
		super(holder, accountNumber);
		this.setAnnualInterestRate(annualInterestRate);
		this.setMinimumBalance(minimumBalance);
	}
	
	public SavingsAccount(SavingsAccount copy) {
		this(copy.getAccountHolder(), copy.getAccountNumber(), copy.getAnnualInterestRate(), copy.getMinimumBalance());
	}

    public SavingsAccount copy() {
    	return new SavingsAccount(this);
    }
	
    public double getAnnualInterestRate() {
		return this.annualInterestRate;
	}
	
	private void setAnnualInterestRate(double rate) {
		if (rate >= 0.0 && rate <= 1.0) this.annualInterestRate = rate;
	}
	
	public double getMinimumBalance(){
		return this.minimumBalance;
	}
	
	private void setMinimumBalance(double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	
	public void withdraw(double amount) {
		if (this.getBalance() - amount >= this.minimumBalance) super.withdraw(amount);
	}	
	
	@Override
    protected double getMonthlyFeesAndInterest() {
        return this.annualInterestRate * this.getBalance() / 12;
    }
    
}