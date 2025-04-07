package entities;

public class Hotel implements Entity{
	private String name;
    private String city;
    private String roomType;
    private int availableRooms;
    private double pricePerNight;
    private double distanceToAirport;
    
	public Hotel(String name, String city, String roomType, int availableRooms, double pricePerNight, double distanceToAirport) {
		/**
		 * Constructor for Hotel entity
		 */
		this.name = name;
        this.city = city;
        this.roomType = roomType;
        this.availableRooms = availableRooms;
        this.pricePerNight = pricePerNight;
        this.distanceToAirport = distanceToAirport;
	}

	public int getAvailability() {
		return availableRooms;
	}

	public void setAvailableRooms(int availableRooms) {
		this.availableRooms = availableRooms;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getRoomType() {
		return roomType;
	}

	public double getPrice() {
		return pricePerNight;
	}

	public double getDistanceToAirport() {
		return distanceToAirport;
	}
	
	@Override
    public String toString() {
        return name + ", " + city  + " (" + roomType + ", $" + pricePerNight + "/night) " + getAvailability() + " rooms left";
    }

}
