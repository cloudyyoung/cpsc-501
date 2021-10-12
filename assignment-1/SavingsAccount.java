public class SavingsAccount extends BankAccount {
	private double annualInterestRate = 0.05;
	private double minimumBalance = 0;
	
	public SavingsAccount(Customer holder, int accountNumber, double annualInterestRate, double minimumBalance) {
        super(holder, accountNumber);
		this.setAnnualInterestRate(annualInterestRate);
		this.setMinimumBalance(minimumBalance);
	}
	
	public SavingsAccount(SavingsAccount copy) {
        super(copy.getAccountHolder(), copy.getAccountNumber());
		this.setAnnualInterestRate(copy.getAnnualInterestRate());
		this.setMinimumBalance(copy.getMinimumBalance());
	}
    
	@Override
    protected double getAvailableBalance() {
    	return this.getBalance() - this.getMinimumBalance();
    }

    public void transfer(double amount, ChequingAccount receipient) {
        if (this.sufficientFunds(amount)) {
            this.withdraw(amount);
            receipient.deposit(amount);
        }
    }
    
    public void transfer(double amount, SavingsAccount receipient) {
        if (this.sufficientFunds(amount)) {
            this.withdraw(amount);
            receipient.deposit(amount);
        }
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
	
	@Override
	public void withdraw(double amount) {
		if (amount > 0 && this.getBalance() - amount >= this.minimumBalance) this.setBalance(this.getBalance() - amount);
	}
	
    protected double getMonthlyFeesAndInterest() {
        return this.annualInterestRate * this.getBalance() / 12;
    }
    
    public SavingsAccount copy() {
    	return new SavingsAccount(this);
    }
    
}