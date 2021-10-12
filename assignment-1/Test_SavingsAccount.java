import static org.junit.Assert.*;
import org.junit.Test;


public class Test_SavingsAccount {
	
	@Test
	public void createSavingsAccount(){
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 10);
		
		assertEquals("Expected new SavingsAccount with Customer named Yunfan", "Yunfan", b.getAccountHolder().getName());
		assertEquals("Expected new SavingsAccount with account number 10238405", 10238405, b.getAccountNumber());
	}
	
	@Test
	public void copySavingsAccount() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 10);
		SavingsAccount c = new SavingsAccount(b);
		
		assertEquals("Expected copied SavingsAccount with Customer named Yunfan", "Yunfan", c.getAccountHolder().getName());
		assertEquals("Expected copied SavingsAccount with account number 10238405", 10238405, c.getAccountNumber());
	}
	
	@Test
	public void stringSavingsAccount() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 5);
		b.deposit(20);
		
		assertEquals("Expcted SavingsAccount toString to (Yunfan 102940) 10238405: 20.0", "(Yunfan 102940) 10238405: 20.0", b.toString());
	}

	@Test
	public void transferExactlySufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(1000);

		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		
		b.transfer(900, d);
		
        assertEquals("Transfered 900 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 100.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 900 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 900.0, d.getBalance(), 0.000001);
	}

	@Test
	public void transferSufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(1000);

		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		
		b.transfer(100, d);
		
        assertEquals("Transfered 100 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 900.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 100 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 100.0, d.getBalance(), 0.000001);
	}

	@Test
	public void transferInsufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(1000);
		
		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		
		d.transfer(1000, b);
		
        assertEquals("Transfered 1000 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 1000.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 1000 from account with 1000 balance (and 100 minimum balance) to account with 0 balance.", 0.0, d.getBalance(), 0.000001);
	}
	

	@Test
    public void withdrawAvailableBalance() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(1000);
		
        b.withdraw(900.0);
        assertEquals("Withdrew 900.0 from account with balance 1000.0 (and 100 minimum balance)", 100.0, b.getBalance(), 0.000001);
	}

	@Test
    public void withdrawEntireBalance() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(1000);
		
        b.withdraw(1000.0);
        assertEquals("Withdrew 1000.0 from account with balance 1000.0 (and 100 minimum balance)", 1000.0, b.getBalance(), 0.000001);
	}
	

	@Test
    public void withdrawNegativeNumber() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(100.0);
		
        b.withdraw(-5.0);
        assertEquals("Withdrew -5.0 from account with balance 100.0", 100.0, b.getBalance(), 0.000001);
	}


	@Test
    public void depositNegativeNumber() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		b.deposit(100.0);
		
        b.deposit(-5.0);
        assertEquals("Withdrew -5.0 from account with balance 100.0", 100.0, b.getBalance(), 0.000001);
	}
	
	@Test
	public void encapsulatedCustomer() {
		Customer a = new Customer("Yunfan", 102940);
		SavingsAccount b = new SavingsAccount(a, 10238405, 0.2, 100);
		
		a.setName("George");
		assertEquals("Customer changed name unchanged for ChequingAccount", "Yunfan", b.getAccountHolder().getName());
		
		Customer c = b.getAccountHolder();
		c.setName("Zhanghang");
		assertEquals("Customer changed name unchanged for ChequingAccount", "Yunfan", b.getAccountHolder().getName());
	}
}
