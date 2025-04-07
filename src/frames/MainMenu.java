package frames;

import user.User;
import entities.Flight;
import entities.Hotel;
import entities.Taxi;
import fileHandling.DatasetParser;
import fileHandling.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    private User user;

    private JComboBox<Taxi> taxiComboBox;
    private JComboBox<Hotel> hotelComboBox;
    private JComboBox<Flight> flightComboBox;

    private Taxi selectedTaxi;
    private Hotel selectedHotel;
    private Flight selectedFlight;
    private JTextField hotelFilterField;
    private JTextField taxiFilterField;
    private JTextField estimatedHotelField;
    private JTextField textField;

    public MainMenu(User user) {
    	/**
    	 * Initializing main menu GUI components, takes the User as input to do operations for the user
    	 */
    	getContentPane().setBackground(SystemColor.textHighlight);
    	setBackground(SystemColor.controlHighlight);
        this.user = user;

        setTitle("KU Travel Agency - Main Menu");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel headerLabel = new JLabel("Welcome to KU Travel Agency, " + user.getUsername());
        headerLabel.setLocation(214, 0);
        headerLabel.setSize(750, 92);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        getContentPane().add(headerLabel);

        JLabel taxiLabel = new JLabel("Select a Taxi:");
        taxiLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        taxiLabel.setLocation(30, 500);
        taxiLabel.setSize(140, 50);
        taxiComboBox = new JComboBox<>();
        taxiComboBox.setLocation(214, 500);
        taxiComboBox.setSize(440, 50);
        loadTaxiData();
        taxiComboBox.addActionListener(e -> selectedTaxi = (Taxi) taxiComboBox.getSelectedItem());
        getContentPane().add(taxiLabel);
        getContentPane().add(taxiComboBox);

        JLabel hotelLabel = new JLabel("Select a Hotel:");
        hotelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        hotelLabel.setLocation(30, 330);
        hotelLabel.setSize(140, 50);
        hotelComboBox = new JComboBox<>();
        hotelComboBox.setLocation(210, 330);
        hotelComboBox.setSize(440, 50);
        loadHotelData();
        hotelComboBox.addActionListener(e -> selectedHotel = (Hotel) hotelComboBox.getSelectedItem());
        getContentPane().add(hotelLabel);
        getContentPane().add(hotelComboBox);

        JLabel flightLabel = new JLabel("Select a Flight:");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        flightLabel.setLocation(30, 160);
        flightLabel.setSize(140, 50);
        flightComboBox = new JComboBox<>();
        flightComboBox.setLocation(210, 160);
        flightComboBox.setSize(440, 50);
        loadFlightData();
        flightComboBox.addActionListener(e -> selectedFlight = (Flight) flightComboBox.getSelectedItem());
        getContentPane().add(flightLabel);
        getContentPane().add(flightComboBox);
        
        JButton packageButton = new JButton("Travel Packages");
        packageButton.setFont(new Font("Arial", Font.PLAIN, 24));
        packageButton.setBounds(750, 150, 350, 100);
        getContentPane().add(packageButton);
        
        JLabel customLabel = new JLabel("Make Your Own Custom Reservation\n");
        customLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        customLabel.setBounds(30, 95, 603, 44);
        getContentPane().add(customLabel);
        
        JLabel lblSeeTravelPackages = new JLabel("See All Available Travel Packages Prepared For You");
        lblSeeTravelPackages.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSeeTravelPackages.setBounds(700, 95, 480, 50);
        getContentPane().add(lblSeeTravelPackages);
        setVisible(true);
        
        JTextField flightFilterField = new JTextField();
        flightFilterField.setLocation(215, 220);
        flightFilterField.setSize(300, 30);
        getContentPane().add(flightFilterField);

        JButton filterFlightButton = new JButton("Filter");
        filterFlightButton.setLocation(520, 220);
        filterFlightButton.setSize(100, 30);
        filterFlightButton.addActionListener(e -> filterFlights(flightFilterField.getText()));
        getContentPane().add(filterFlightButton);
        
        JButton filterHotelButton = new JButton("Filter");
        filterHotelButton.setBounds(520, 390, 100, 30);
        filterHotelButton.addActionListener(e -> filterHotels(hotelFilterField.getText()));
        getContentPane().add(filterHotelButton);
        
        JButton filterTaxiButton = new JButton("Filter");
        filterTaxiButton.setBounds(520, 560, 100, 30);
        filterTaxiButton.addActionListener(e -> filterTaxis(taxiFilterField.getText()));
        getContentPane().add(filterTaxiButton);
        
        hotelFilterField = new JTextField();
        hotelFilterField.setBounds(215, 390, 300, 30);
        getContentPane().add(hotelFilterField);
        
        taxiFilterField = new JTextField();
        taxiFilterField.setBounds(215, 560, 300, 30);
        getContentPane().add(taxiFilterField);
        
        estimatedHotelField = new JTextField();
        estimatedHotelField.setFont(new Font("Arial", Font.PLAIN, 15));
        estimatedHotelField.setBounds(215, 450, 120, 30);
        getContentPane().add(estimatedHotelField);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setForeground(new Color(51, 0, 204));
        separator_2.setBounds(30, 488, 650, 10);
        getContentPane().add(separator_2);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(new Color(51, 0, 204));
        separator_1.setBounds(30, 294, 1150, 10);
        getContentPane().add(separator_1);
        
        JLabel estimatedHotelLabel = new JLabel("Duration (Nights):");
        estimatedHotelLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        estimatedHotelLabel.setBounds(30, 440, 120, 50);
        getContentPane().add(estimatedHotelLabel);
        
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBounds(214, 620, 120, 30);
        getContentPane().add(textField);
        
        JLabel taxiKmLabel = new JLabel("Estimated Distance (KM):");
        taxiKmLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        taxiKmLabel.setBounds(30, 610, 180, 50);
        getContentPane().add(taxiKmLabel);
        
        JSeparator separatorVertical = new JSeparator();
        separatorVertical.setOrientation(SwingConstants.VERTICAL);
        separatorVertical.setForeground(new Color(51, 0, 204));
        separatorVertical.setBounds(675, 130, 10, 560);
        getContentPane().add(separatorVertical);
        
        JSeparator separator_2_1 = new JSeparator();
        separator_2_1.setForeground(new Color(51, 0, 204));
        separator_2_1.setBounds(30, 682, 650, 10);
        getContentPane().add(separator_2_1);
        
        JButton myReservationsButton = new JButton("My Reservations");
        myReservationsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        myReservationsButton.setBounds(780, 400, 300, 80);
        myReservationsButton.addActionListener(e -> showReservations());
        getContentPane().add(myReservationsButton);
        
        

        JButton detailedCostButton = new JButton("Detailed Cost Breakdown");
        detailedCostButton.setFont(new Font("Arial", Font.PLAIN, 18));
        detailedCostButton.setLocation(200, 700);
        detailedCostButton.setSize(300,60);
        detailedCostButton.addActionListener(e -> {
            if (selectedFlight == null && selectedHotel == null && selectedTaxi == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight, hotel and a taxi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double hotelCost = 0;
            if (selectedHotel != null) {
                try {
                    int nights = Integer.parseInt(estimatedHotelField.getText().trim());
                    hotelCost = nights * selectedHotel.getPrice();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid duration (nights) for hotel.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            double taxiCost = 0;
            if (selectedTaxi != null) {
                try {
                    double estimatedDistance = Double.parseDouble(textField.getText().trim());
                    taxiCost = selectedTaxi.getPrice() + (estimatedDistance * selectedTaxi.getPerKmFare());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid estimated distance for taxi.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            StringBuilder costBreakdown = new StringBuilder("Detailed Cost Breakdown:\n");
            if (selectedFlight != null) {
				costBreakdown.append("Flight: ").append(selectedFlight);
			}
            if (selectedHotel != null) {
                costBreakdown.append("\nHotel: ").append(selectedHotel)
                             .append("\n     Nights: ").append(estimatedHotelField.getText())
                             .append("\n     Cost: $").append(String.format("%.2f", hotelCost)).append("\n");
            }
            if (selectedTaxi != null) {
                costBreakdown.append("Taxi: ").append(selectedTaxi)
                             .append("\n     Estimated Distance: ").append(textField.getText())
                             .append(" km\n     Cost: $").append(String.format("%.2f", taxiCost)).append("\n");
            }
            costBreakdown.append("Total Cost: $" + String.format("%.2f", (selectedFlight.getPrice() + hotelCost + taxiCost)));
            int result = JOptionPane.showConfirmDialog(this, costBreakdown.toString(),
                    "Confirm Reservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    FileWriter writer = new FileWriter("reservations.txt", true);

                    if (selectedFlight != null) {
                    	Logger.log(user.getUsername() + " booked: Flight, " + selectedFlight + " at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
                        writer.write(user.getUsername() + ",Flight," + selectedFlight + "\n");
                    }
                    if (selectedHotel != null) {
                    	Logger.log(user.getUsername() + " booked: Hotel, " + selectedHotel + ", " + estimatedHotelField.getText() + " nights" + ", Cost:$" + String.format("%.2f", hotelCost) + " at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
                        writer.write(user.getUsername() + ",Hotel," + selectedHotel + ", " + estimatedHotelField.getText() + " nights" + ", Cost:$" + String.format("%.2f", hotelCost) + "\n");
                    }
                    if (selectedTaxi != null) {
                    	Logger.log(user.getUsername() + " booked: Taxi, " + selectedTaxi + ", " + taxiKmLabel.getText() + ", Cost:$" + String.format("%.2f", taxiCost)+ " at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
                        writer.write(user.getUsername() + ",Taxi," + selectedTaxi + ", " + taxiKmLabel.getText() + ", Cost:$" + String.format("%.2f", taxiCost) + "\n");
                    }


                    writer.close();
                    JOptionPane.showMessageDialog(this, "Reservation confirmed and saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving reservation details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getContentPane().add(detailedCostButton);
        
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		Login login = new Login();
        	}
        });
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setBounds(6, 17, 154, 50);
        getContentPane().add(logoutButton);
        
        JButton statsButton = new JButton("See My Statistics");
        statsButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleSeeMyStatisticsButton();
        	}
        });
        statsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        statsButton.setBounds(780, 550, 300, 80);
        getContentPane().add(statsButton);
        
        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setBounds(960, 20, 200, 50);
        getContentPane().add(dateLabel);

        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            dateLabel.setText(currentDate);
        });
        timer.start();

        
        packageButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleTravelPackagesButton();
        	}
        });
    }

    private void loadTaxiData() {
    	/**
    	 * Loads the taxi data to the combo box
    	 */
        try {
            List<Taxi> taxis = DatasetParser.parseTaxiDataset();
            for (Taxi taxi : taxis) {
                taxiComboBox.addItem(taxi);
            }
        } catch (IOException e) {
            showError("Error loading taxi data.");
        }
    }

    private void loadHotelData() {
    	/**
    	 * Loads the hotel data to the combo box
    	 */
        try {
            List<Hotel> hotels = DatasetParser.parseHotelDataset();
            for (Hotel hotel : hotels) {
                hotelComboBox.addItem(hotel);
            }
        } catch (IOException e) {
            showError("Error loading hotel data.");
        }
    }

    private void loadFlightData() {
    	/**
    	 * Loads the flight data to the combo box
    	 */
        try {
            List<Flight> flights = DatasetParser.parseFlightDataset();
            for (Flight flight : flights) {
                flightComboBox.addItem(flight);
            }
        } catch (IOException e) {
            showError("Error loading flight data.");
        }
    }
    
    private void filterFlights(String filter) {
    	/**
    	 * Allows user to filter flight with words
    	 */
        flightComboBox.removeAllItems();

        try {
            List<Flight> flights = DatasetParser.parseFlightDataset();
            boolean added = false;

            for (Flight flight : flights) {
                if (flight.toString().toLowerCase().contains(filter.toLowerCase())) {
                    flightComboBox.addItem(flight);
                    added = true;
                }
            }

            if (!added) {
                JOptionPane.showMessageDialog(this, "No flights match the filter criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            showError("Error filtering flight data.");
        }
    }
    
    private void filterHotels(String filter) {
    	/**
    	 * Allows user to filter hotels with words
    	 */
        hotelComboBox.removeAllItems();

        try {
            List<Hotel> hotels = DatasetParser.parseHotelDataset();
            boolean added = false;

            for (Hotel hotel : hotels) {
                if (hotel.toString().toLowerCase().contains(filter.toLowerCase())) {
                    hotelComboBox.addItem(hotel);
                    added = true;
                }
            }

            if (!added) {
                JOptionPane.showMessageDialog(this, "No hotels match the filter criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            showError("Error filtering hotel data.");
        }
    }
    
    private void filterTaxis(String filter) {
    	/**
    	 * Allows user to filter taxis with words
    	 */
        taxiComboBox.removeAllItems();

        try {
            List<Taxi> taxis = DatasetParser.parseTaxiDataset();
            boolean added = false;

            for (Taxi taxi : taxis) {
                if (taxi.toString().toLowerCase().contains(filter.toLowerCase())) {
                    taxiComboBox.addItem(taxi);
                    added = true;
                }
            }

            if (!added) {
                JOptionPane.showMessageDialog(this, "No taxis match the filter criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            showError("Error filtering taxi data.");
        }
    }
    
    private void showReservations() {
    	/**
    	 * Shows all reservations belongs to the user
    	 */
        try {
            List<String> reservations = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(user.getUsername())) {
                	String reservationDetails = String.join(",", Arrays.copyOfRange(parts, 1, parts.length));
                    reservations.add(reservationDetails);
                }
            }
            reader.close();

            if (reservations.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No reservations found.", "My Reservations", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JList<String> reservationList = new JList<>(reservations.toArray(new String[0]));
            reservationList.setFont(new Font("Arial", Font.PLAIN, 14));
            reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(reservationList);
            scrollPane.setPreferredSize(new Dimension(800, 300));

            int option = JOptionPane.showOptionDialog(
                    this,
                    scrollPane,
                    "My Reservations",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"Cancel Selected Reservation", "Back"},
                    "Cancel Selected Reservation"
            );

            if (option == 0) {
                String selectedReservation =  reservationList.getSelectedValue();
                if (selectedReservation != null) {
                	selectedReservation = user.getUsername() + "," + reservationList.getSelectedValue();
                    cancelReservation(selectedReservation);
                } 
                else {
                    JOptionPane.showMessageDialog(this, "No reservation selected.", "Cancel Reservation", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading reservations file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void cancelReservation(String reservation) {
    	/**
    	 * Cancels the reservation if the conditions are met
    	 */
    	boolean canCancel = confirmCancellation(reservation);
    	if (!canCancel) {
			return;
		}
    	File inputFile = new File("reservations.txt");
        File tempFile = new File("reservations-temp.txt");
    	try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(reservation)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            JOptionPane.showMessageDialog(this, "Reservation cancelled successfully.", "Cancel Reservation", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error updating reservations file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(this, "Error updating reservations file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    private void handleTravelPackagesButton() {
    	/**
    	 * Shows the available travel packages to the user
    	 */
        JFrame travelPackagesFrame = new JFrame("Travel Packages");
        travelPackagesFrame.setSize(1000, 600);
        travelPackagesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        travelPackagesFrame.setLocationRelativeTo(null);
        travelPackagesFrame.getContentPane().setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Available Travel Packages", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        travelPackagesFrame.getContentPane().add(headerLabel, BorderLayout.NORTH);

        DefaultListModel<String> packageListModel = new DefaultListModel<>();
        JList<String> packageList = new JList<>(packageListModel);
        packageList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(packageList);
        travelPackagesFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton reserveButton = new JButton("Reserve Selected Package");
        reserveButton.setEnabled(false);
        reserveButton.addActionListener(e -> reserveSelectedPackage(packageList.getSelectedValue()));
        travelPackagesFrame.getContentPane().add(reserveButton, BorderLayout.SOUTH);

        packageList.addListSelectionListener(e -> reserveButton.setEnabled(packageList.getSelectedValue() != null));

        try (BufferedReader reader = new BufferedReader(new FileReader("travel-packages.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                	String flight = parts[0];
                    String hotel = parts[1];
                    String taxi = parts[2];
                    int hotelNights = Integer.parseInt(parts[1].split(",")[3]);
                    int taxiDistance = Integer.parseInt(parts[2].split(",")[2]);
                    double commission = Double.parseDouble(parts[3]);
                    double totalCost = Double.parseDouble(parts[4]);
                    String formattedPackage = String.format(
                            "%s; %s; %s; Nights at hotel: %d, Also %d Km taxi ride only for: Commission: %.1f%% Total Cost$;%.2f",
                            flight, hotel, taxi, hotelNights, taxiDistance ,commission, totalCost
                    );

                    packageListModel.addElement(formattedPackage);
                } else {
                    packageListModel.addElement(line);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(travelPackagesFrame, "Error loading travel packages.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        travelPackagesFrame.setVisible(true);
    }


    private void reserveSelectedPackage(String packageLine) {
    	/**
    	 * Reserves selected travel package for user
    	 */
        if (packageLine == null || packageLine.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No package selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] mainParts = packageLine.split(";");
        if (mainParts.length != 5) {
            JOptionPane.showMessageDialog(null, "Invalid package format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String flightInfo = mainParts[0]; 
        String hotelPart = mainParts[1]; 
        String taxiPart = mainParts[2];
        double totalCost = Double.parseDouble(mainParts[4]);

        String[] hotelParts = hotelPart.split(",");
        if (hotelParts.length != 4) {
            JOptionPane.showMessageDialog(null, "Invalid hotel format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String hotelInfo = hotelParts[0];
        int duration = Integer.parseInt(hotelParts[3]);

        String[] taxiParts = taxiPart.split(",");
        if (taxiParts.length != 3) {
            JOptionPane.showMessageDialog(null, "Invalid taxi format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String taxiInfo = taxiParts[0];
        int taxiKm = Integer.parseInt(taxiParts[2]);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            if (!flightInfo.isEmpty()) {
                writer.write(user.getUsername() + ",Travel Package: Flight," + flightInfo + " ");
            }
            if (!hotelInfo.isEmpty()) {
                writer.write(" Hotel," + hotelInfo + ", " + duration + " nights ");
            }
            if (!taxiInfo.isEmpty()) {
                writer.write("Taxi," + taxiInfo + ", " + taxiKm + " km ");
            }
            writer.write("Total Cost:$" + String.format("%.2f", totalCost) + "\n");
            Logger.log(user.getUsername() + " booked Travel Package: Flight, " + flightInfo + " Hotel," + hotelInfo + ", " + duration + " nights " +  "Taxi," + taxiInfo + ", " + taxiKm + " KM" +  " Total Cost, Cost:$" + String.format("%.2f", totalCost) +" at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
            JOptionPane.showMessageDialog(null, "Package reserved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving reservation.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResources(String flightInfo, String hotelInfo, String taxiInfo) {
    	/**
    	 * Updates the new states of the reservations to the data
    	 */
        try {
            List<Flight> flights = DatasetParser.parseFlightDataset();
            for (Flight flight : flights) {
                if (flightInfo.contains(flight.toString())) {
                    flight.setAvailableSeats(flight.getAvailability() - 1);
                    break;
                }
            }
            List<Hotel> hotels = DatasetParser.parseHotelDataset();
            for (Hotel hotel : hotels) {
                if (hotelInfo.contains(hotel.toString())) {
                    hotel.setAvailableRooms(hotel.getAvailability() - 1);
                    break;
                }
            }
            List<Taxi> taxis = DatasetParser.parseTaxiDataset();
            for (Taxi taxi : taxis) {
                if (taxiInfo.contains(taxi.toString())) {
                    taxi.setAvailableTaxis(taxi.getAvailability() - 1);
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating resources.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean confirmCancellation(String reservation) {
    	/**
    	 * Confirms cancellation operations 
    	 */

    	if (reservation.contains("Travel Package")) {
    		JOptionPane.showMessageDialog(null, "There is no cancel policy for this Travel Package", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
    	if (!reservation.contains("Flight")) {
    		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this reservation?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            return result == JOptionPane.YES_OPTION;
		}
        String departureTime = extractDepartureTime(reservation);
        if (departureTime == null) {
            return false;
        }

        int departureHour = Integer.parseInt(departureTime.split(":")[0]);
        int currentHour = LocalTime.now().getHour();

        if (currentHour > departureHour) {
            JOptionPane.showMessageDialog(null, "The reservation date is already passed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this reservation?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    
    private String extractDepartureTime(String reservation) {
    	/**
    	 * Extracts departure time information from the flight's data
    	 */
        try {
            String flightDetails = reservation.split("\\(")[1].split("\\)")[0];
            String[] parts = flightDetails.split("->");
            String departureSegment = parts[0].trim();
            String departureTime = departureSegment.substring(departureSegment.lastIndexOf(" ") + 1);

            return departureTime;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid reservation format.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void handleSeeMyStatisticsButton() {
    	/**
    	 * Handles see my statistics button, shows the users stats
    	 */
        double totalCost = 0.0;
        int totalReservations = 0;
        String username = user.getUsername();

        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username)) {
                    totalReservations++;
                    double cost = 0.0;

                    if (line.contains("Total Cost:")) {
                    	String regex = "Total Cost:\\$(\\d+\\.\\d{2})";
                      
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                        	String strCost = matcher.group(1);    	
                        	try {
                                cost = Double.parseDouble(strCost);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid cost format in line: " + line);
                            }
                        }
                    } else if (line.contains("Cost:")) {
                        String costString = line.substring(line.lastIndexOf("Cost:") + 6).trim();
                        try {
                            cost = Double.parseDouble(costString);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid cost format in line: " + line);
                        }
                    }else if (line.contains("Flight")) {
                        int dollarIndex = line.indexOf('$');
                        if (dollarIndex != -1) {
                        	String costString = line.substring(dollarIndex + 1, line.indexOf(' ', dollarIndex)).trim();
                        	costString = costString.replaceAll("[^\\d.]", ""); 
                            try {                           	
                            	cost = Double.parseDouble(costString);
                                
                            } catch (NumberFormatException ex) {
                                continue;
                            }
                        }
                    } 

                    totalCost += cost;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading reservation data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(
        	    null, 
        	    "Total Reservations Made: " + totalReservations + "\nTotal Cost: $" + String.format("%.2f", totalCost), 
        	    "Statistics", 
        	    JOptionPane.INFORMATION_MESSAGE
        	);
    }

    
    private void showError(String message) {
    	/**
    	 * shows error based on the given message
    	 */
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}