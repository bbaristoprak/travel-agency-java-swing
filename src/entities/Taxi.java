package entities;

public class Taxi implements Entity{
	private String city;
	private String taxiType;
	private int availableTaxis;
	private double baseFare;
	private double perKmFare;
	
	
	public Taxi(String city, String taxiType, int availableTaxis, double baseFare, double perKmFare) {
		/**
		 * Constructor for Taxi entity
		 */
		this.city = city;
		this.taxiType = taxiType;
		this.availableTaxis = availableTaxis;
		this.baseFare = baseFare;
		this.perKmFare = perKmFare;
	}

	public int getAvailability() {
		return availableTaxis;
	}


	public void setAvailableTaxis(int availableTaxis) {
		this.availableTaxis = availableTaxis;
	}


	public String getCity() {
		return city;
	}


	public String getTaxiType() {
		return taxiType;
	}


	public double getPrice() {
		return baseFare;
	}


	public double getPerKmFare() {
		return perKmFare;
	}
	
	@Override
    public String toString() {
        return city + " "+ taxiType + " (Available Taxis: " + availableTaxis + ", Price: $" + baseFare + " + $" + perKmFare + "/km)";
    }
	

}
