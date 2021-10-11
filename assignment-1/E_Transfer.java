import java.time.LocalDate;

public class E_Transfer extends Transaction {
	private BankAccount from;
	private BankAccount to;
	private LocalDate receivedOn;

	public E_Transfer(String description, double amount, BankAccount from, BankAccount to, LocalDate receiveOn) {
		super(description, amount);
		this.setFrom(from);
		this.setTo(to);
		this.setReceivedOn(receiveOn);
	}

	public BankAccount getFrom() {
		return this.from.copy();
	}

	private void setFrom(BankAccount from) {
		this.from = from.copy();
	}

	public BankAccount getTo() {
		return this.to.copy();
	}

	private void setTo(BankAccount to) {
		this.to = to.copy();
	}

	public LocalDate getReceivedOn() {
		return this.receivedOn;
	}

	private void setReceivedOn(LocalDate receivedOn) {
		this.receivedOn = receivedOn;
	}

}
