package user;

public abstract class User {
	private String username;
	private String password;

	public User(String username, String password) {
		/**
		 * Constructor for abstract User class, which admin and customer inherits from
		 */
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
