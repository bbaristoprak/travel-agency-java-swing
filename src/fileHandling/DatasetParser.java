package fileHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Flight;
import entities.Hotel;
import entities.Taxi;

public class DatasetParser {
	/**
	 * This class contains parsers for each entity, parses the data from csv files and creates the entities, adding them do their list.
	 * @return
	 * @throws IOException
	 */
	public static List<Taxi> parseTaxiDataset() throws IOException {
        List<Taxi> taxiList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("FinalKU_Travel_Agency_Dataset_Taxis.csv"));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            Taxi taxi = new Taxi(
                values[0],
                values[1],
                Integer.parseInt(values[2]),
                Double.parseDouble(values[3]),
                Double.parseDouble(values[4])
            );
            taxiList.add(taxi);
        }
        reader.close();
        return taxiList;
    }
	
	public static List<Hotel> parseHotelDataset() throws IOException {
        List<Hotel> hotelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("FinalKU_Travel_Agency_Dataset_Hotels.csv"));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            Hotel hotel = new Hotel(
                values[0],
                values[1],
                values[2],
                Integer.parseInt(values[3]), 
                Double.parseDouble(values[4]),
                Double.parseDouble(values[5])
            );
            hotelList.add(hotel);
        }
        reader.close();
        return hotelList;
    }
	
	public static List<Flight> parseFlightDataset() throws IOException {
	    List<Flight> flightList = new ArrayList<>();
	    BufferedReader reader = new BufferedReader(new FileReader("FinalKU_Travel_Agency_Dataset_Flights.csv"));
	    String line;
	    reader.readLine();

	    while ((line = reader.readLine()) != null) {
	        String[] values = line.split(",");

	        String flightId = values[0];
	        String airline = values[1];
	        String departureCity = values[2];
	        String arrivalCity = values[3];
	        String departureTime = values[4];
	        String arrivalTime = values[5];
	        String ticketClass = values[6];
	        double price = Double.parseDouble(values[7]);
	        int availableSeats = Integer.parseInt(values[8]);
	        
	        if (values.length >= 10) {
	        	 if (!values[9].isEmpty() && !values[10].isEmpty()) {
	        		departureTime = values[11];
	 	            arrivalCity = values[10];
	 	            arrivalTime = values[14];
	 	        }
			}
	       
	        Flight flight = new Flight(
	            flightId,
	            airline,
	            departureCity,
	            arrivalCity,
	            departureTime,
	            arrivalTime,
	            ticketClass,
	            price,
	            availableSeats
	        );

	        flightList.add(flight);
	    }

	    reader.close();
	    return flightList;
	}
	
}
