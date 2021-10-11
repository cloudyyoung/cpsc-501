import java.time.LocalDate;

public class E_Transfer extends Transaction {
	private Customer from;
	private Customer to;
	private String referenceCode;
	private LocalDate receivedOn;

	public E_Transfer(String description, double amount, Customer from, Customer to, String referenceCode, LocalDate receiveOn) {
		super(description, amount);
		this.setFrom(from);
		this.setTo(to);
		this.setReferenceCode(referenceCode);
		this.setReceivedOn(receiveOn);
	}

	public Customer getFrom() {
		return new Customer(this.from);
	}

	private void setFrom(Customer from) {
		this.from = new Customer(from);
	}

	public Customer getTo() {
		return new Customer(this.to);
	}

	private void setTo(Customer to) {
		this.to = new Customer(to);
	}

	public String getReferenceCode() {
		return this.referenceCode;
	}

	private void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public LocalDate getReceivedOn() {
		return this.receivedOn;
	}

	private void setReceivedOn(LocalDate receivedOn) {
		this.receivedOn = receivedOn;
	}

}
