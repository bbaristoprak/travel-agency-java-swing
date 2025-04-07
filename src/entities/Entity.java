package entities;

public interface Entity {
	/**
	 * The interface for Flight, Hotel, Taxi
	 * @return
	 */
	int getAvailability();
	double getPrice();
	String toString();
}
