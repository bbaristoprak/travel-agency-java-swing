package entities;

public class Flight implements Entity{
	private String flightId;
	private String airline;
	private String departureCity;
	private String arrivalCity;
	private String departureTime;
	private String arrivalTime;
	private String ticketClass;
	private double price;
	private int availableSeats;

	public Flight(String flightId, String airline, String departureCity, String arrivalCity, String departureTime,
			String arrivalTime, String ticketClass, double price, int availableSeats) {
		/**
		 * Constructor for Flight entity
		 */
		this.flightId = flightId;
        this.airline = airline;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketClass = ticketClass;
        this.price = price;
        this.availableSeats = availableSeats;
	}
	
	public int getAvailability() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getFlightId() {
		return flightId;
	}

	public String getAirline() {
		return airline;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public String getTicketClass() {
		return ticketClass;
	}

	public double getPrice() {
		return price;
	}
	 @Override
	    public String toString() {
	        return getFlightId() + " " + airline + " (" + departureCity + " " + departureTime + " -> " + arrivalCity + " "+ arrivalTime + ", $" + price + ") " + getAvailability() + " seats left";
	    }

}
