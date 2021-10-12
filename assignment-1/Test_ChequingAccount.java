import static org.junit.Assert.*;
import org.junit.Test;


public class Test_ChequingAccount {
	
	@Test
	public void createChequingAccount(){
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		
		assertEquals("Expected new ChequingAccount with Customer named Yunfan", "Yunfan", b.getAccountHolder().getName());
		assertEquals("Expected new ChequingAccount with account number 10238405", 10238405, b.getAccountNumber());
	}
	
	@Test
	public void copyChequingAccount() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		ChequingAccount c = new ChequingAccount(b);
		
		assertEquals("Expected copied ChequingAccount with Customer named Yunfan", "Yunfan", c.getAccountHolder().getName());
		assertEquals("Expected copied ChequingAccount with account number 10238405", 10238405, c.getAccountNumber());
	}
	
	@Test
	public void stringChequingAccount() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		b.deposit(20);
		
		assertEquals("Expcted ChequingAccount toString to (Yunfan 102940) 10238405: 20.0", "(Yunfan 102940) 10238405: 20.0", b.toString());
	}

	@Test
	public void transferExactlySufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);

		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		d.deposit(1000);
		
		d.transfer(1000, b);
		
        assertEquals("Transfered 1000 from account with 1000 balance to account with 0 balance.", 1000.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 1000 from account with 1000 balance to account with 0 balance.", 0.0, d.getBalance(), 0.000001);
	}

	@Test
	public void transferSufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);

		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		d.deposit(2500);
		
		d.transfer(1000, b);
		
        assertEquals("Transfered 1000 from account with 2500 balance to account with 0 balance.", 1000.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 1000 from account with 2500 balance to account with 0 balance.", 1500.0, d.getBalance(), 0.000001);
	}

	@Test
	public void transferInsufficientFunds() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);

		Customer c = new Customer("Zhanghang", 110293);
		ChequingAccount d = new ChequingAccount(c, 19302932, 500, 10);
		d.deposit(400);
		
		d.transfer(1000, b);
		
        assertEquals("Transfered 1000 from account with 2500 balance to account with 0 balance.", 0.0, b.getBalance(), 0.000001);
        assertEquals("Transfered 1000 from account with 2500 balance to account with 0 balance.", 400.0, d.getBalance(), 0.000001);
	}
	

	@Test
    public void withdrawEntireBalance() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		b.deposit(5.0);
		
        b.withdraw(5.0);
        assertEquals("Withdrew 5.0 from account with balance 5.0", 0.0, b.getBalance(), 0.000001);
	}
	

	@Test
    public void withdrawNegativeNumber() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		b.deposit(100.0);
		
        b.withdraw(-5.0);
        assertEquals("Withdrew -5.0 from account with balance 100.0", 100.0, b.getBalance(), 0.000001);
	}


	@Test
    public void depositNegativeNumber() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		b.deposit(100.0);
		
        b.deposit(-5.0);
        assertEquals("Withdrew -5.0 from account with balance 100.0", 100.0, b.getBalance(), 0.000001);
	}
	
	@Test
	public void encapsulatedCustomer() {
		Customer a = new Customer("Yunfan", 102940);
		ChequingAccount b = new ChequingAccount(a, 10238405, 200, 5);
		
		a.setName("George");
		assertEquals("Customer changed name unchanged for ChequingAccount", "Yunfan", b.getAccountHolder().getName());
		
		Customer c = b.getAccountHolder();
		c.setName("Zhanghang");
		assertEquals("Customer changed name unchanged for ChequingAccount", "Yunfan", b.getAccountHolder().getName());
	}
}
