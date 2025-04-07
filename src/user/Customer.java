package user;

public class Customer extends User{
	private String name;
	private String surname;
	private String ID;

	public Customer(String username, String password, String name, String surname, String ID) {
		/**
		 * Constructor for Customer
		 */
		super(username, password);
		this.name = name;
		this.surname = surname;
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
