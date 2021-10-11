public class Transaction {
	private String description;
	private double amount;

	public Transaction(String description, double amount) {
		this.setDescription(description);
		this.setAmount(amount);
	}
	
	public Transaction(Transaction copy) {
		this(copy.getDescription(), copy.getAmount());
	}

	public String getDescription() {
		return this.description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return this.amount;
	}

	private void setAmount(double amount) {
		this.amount = amount;
	}

}
