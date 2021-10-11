public class Customer {
	private String name;
	private int id;
	
	public Customer(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public Customer(Customer copy) {
		this(copy.getName(), copy.getID());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}

	public String toString() {
		return this.name + " " + this.id;
	}
}