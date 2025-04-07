package frames;

import entities.Flight;
import entities.Hotel;
import entities.Taxi;
import fileHandling.DatasetParser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMenu extends JFrame {
    private JComboBox<Flight> flightComboBox;
    private JComboBox<Hotel> hotelComboBox;
    private JComboBox<Taxi> taxiComboBox;
    private JComboBox<Flight> userFlightComboBox;
    private JComboBox<Hotel> userHotelComboBox;
    private JComboBox<Taxi> userTaxiComboBox;
    private JTextField hotelDurationField;
    private JTextField taxiKmField;
    private JTextField commissionField;
    private Flight selectedFlight;
    private Hotel selectedHotel;
    private Taxi selectedTaxi;
    private Flight userSelectedFlight;
    private Hotel userSelectedHotel;
    private Taxi userSelectedTaxi;
    private double commissionRate;
    private JComboBox<String> userComboBox;
    private JTextField userHotelDurationField;
    private JTextField userTaxiKmField;

    public AdminMenu() {
    	/**
    	 * Initializing admin menu with the GUI components
    	 */
        setTitle("Admin Panel - KU Travel Agency");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel headerLabel = new JLabel("Admin Panel");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setBounds(450, 20, 300, 50);
        getContentPane().add(headerLabel);
        
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
        
        JButton viewTravelPackagesButton = new JButton("View Travel Packages");
        viewTravelPackagesButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewTravelPackagesButton.setBounds(120, 580, 350, 80);
        getContentPane().add(viewTravelPackagesButton);
        viewTravelPackagesButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		handleTravelPackagesButton();
        	}
        });

        addUserSection();
        addComponents();
        addFlightSection();
        addHotelSection();
        addTaxiSection();
        addCommissionField();
       

        setVisible(true);
    }

    private void addFlightSection() {
    	/**
    	 * Adds the Flight section's GUI components
    	 */
        JLabel flightLabel = new JLabel("Select a Flight:");
        flightLabel.setBounds(30, 100, 100, 30);
        getContentPane().add(flightLabel);

        flightComboBox = new JComboBox<>();
        flightComboBox.setBounds(160, 100, 440, 30);
        loadFlightData();
        flightComboBox.addActionListener(e -> selectedFlight = (Flight) flightComboBox.getSelectedItem());
        getContentPane().add(flightComboBox);
    }

    private void addHotelSection() {
    	/**
    	 * Adds the Hotel section's GUI components
    	 */
        JLabel hotelLabel = new JLabel("Select a Hotel:");
        hotelLabel.setBounds(30, 150, 100, 30);
        getContentPane().add(hotelLabel);

        hotelComboBox = new JComboBox<>();
        hotelComboBox.setBounds(160, 150, 440, 30);
        loadHotelData();
        hotelComboBox.addActionListener(e -> selectedHotel = (Hotel) hotelComboBox.getSelectedItem());
        getContentPane().add(hotelComboBox);

        JLabel durationLabel = new JLabel("Duration (nights):");
        durationLabel.setBounds(70, 200, 120, 30);
        getContentPane().add(durationLabel);

        hotelDurationField = new JTextField();
        hotelDurationField.setBounds(200, 200, 100, 30);
        getContentPane().add(hotelDurationField);
    }

    private void addTaxiSection() {
    	/**
    	 * Adds the Taxi section's GUI components
    	 */
        JLabel taxiLabel = new JLabel("Select a Taxi:");
        taxiLabel.setBounds(30, 250, 120, 30);
        getContentPane().add(taxiLabel);

        taxiComboBox = new JComboBox<>();
        taxiComboBox.setBounds(160, 250, 440, 30);
        loadTaxiData();
        taxiComboBox.addActionListener(e -> selectedTaxi = (Taxi) taxiComboBox.getSelectedItem());
        getContentPane().add(taxiComboBox);

        JLabel kmLabel = new JLabel("Distance (km):");
        kmLabel.setBounds(70, 300, 120, 30);
        getContentPane().add(kmLabel);

        taxiKmField = new JTextField();
        taxiKmField.setBounds(200, 300, 100, 30);
        getContentPane().add(taxiKmField);
    }

    private void addCommissionField() {
    	/**
    	 * Adds the Comission section's GUI components and seperators
    	 */
        JLabel commissionLabel = new JLabel("Commission (%):");
        commissionLabel.setBounds(30, 350, 120, 30);
        getContentPane().add(commissionLabel);

        commissionField = new JTextField();
        commissionField.setBounds(160, 350, 100, 30);
        getContentPane().add(commissionField);
        
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(620, 100, 10, 600);
        getContentPane().add(separator);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(40, 128, 500, 10);
        getContentPane().add(separator_1);
        
        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setBounds(52, 550, 500, 10);
        getContentPane().add(separator_1_1);
        
        JSeparator separator_1_1_1 = new JSeparator();
        separator_1_1_1.setBounds(700, 598, 500, 10);
        getContentPane().add(separator_1_1_1);
        
    }

    private void addComponents() {
    	/**
    	 * Adds GUI components and handles the operations related to them
    	 */
        JButton createPackageButton = new JButton("Create Travel Package");
        createPackageButton.setFont(new Font("Arial", Font.PLAIN, 20));
        createPackageButton.setBounds(120, 450, 350, 80);
        createPackageButton.addActionListener(e -> saveTravelPackage());
        getContentPane().add(createPackageButton);

        JButton viewReservationsButton = new JButton("View All Reservations");
        viewReservationsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewReservationsButton.setBounds(120, 680, 350, 80);
        viewReservationsButton.addActionListener(e -> showAllReservations());
        getContentPane().add(viewReservationsButton);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setBounds(30, 20, 200, 40);
        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });
        getContentPane().add(logoutButton);
        
        JButton userStatsButton = new JButton("See all Users with Stats");
        userStatsButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		seeAllUserStats();
        	}
        });
        userStatsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        userStatsButton.setBounds(800, 650, 350, 60);
        getContentPane().add(userStatsButton);
        
        JLabel flightLabel = new JLabel("Select a Flight:");
        flightLabel.setBounds(650, 178, 120, 30);
        getContentPane().add(flightLabel);
        
        userFlightComboBox = new JComboBox<Flight>();
        userFlightComboBox.setBounds(750, 181, 440, 30);
        userFlightComboBox.addActionListener(e -> userSelectedFlight = (Flight) userFlightComboBox.getSelectedItem());
        getContentPane().add(userFlightComboBox);
        
        JLabel hotelLabel = new JLabel("Select a Hotel:");
        hotelLabel.setBounds(650, 257, 100, 30);
        getContentPane().add(hotelLabel);
        
        userHotelComboBox = new JComboBox<Hotel>();
        userHotelComboBox.setBounds(750, 260, 440, 30);
        userHotelComboBox.addActionListener(e -> userSelectedHotel = (Hotel) userHotelComboBox.getSelectedItem());
        getContentPane().add(userHotelComboBox);
        
        JLabel durationLabel = new JLabel("Duration (nights):");
        durationLabel.setBounds(700, 300, 120, 30);
        getContentPane().add(durationLabel);
        
        userHotelDurationField = new JTextField();
        userHotelDurationField.setBounds(832, 300, 100, 30);
        getContentPane().add(userHotelDurationField);
        
        JLabel taxiLabel = new JLabel("Select a Taxi:");
        taxiLabel.setBounds(650, 360, 100, 30);
        getContentPane().add(taxiLabel);
        
        userTaxiComboBox = new JComboBox<Taxi>();
        userTaxiComboBox.setBounds(750, 360, 440, 30);
        userTaxiComboBox.addActionListener(e -> userSelectedTaxi = (Taxi) userTaxiComboBox.getSelectedItem());
        getContentPane().add(userTaxiComboBox);
        
        JLabel kmLabel = new JLabel("Distance (km):");
        kmLabel.setBounds(700, 400, 120, 30);
        getContentPane().add(kmLabel);
        
        userTaxiKmField = new JTextField();
        userTaxiKmField.setBounds(832, 400, 100, 30);
        getContentPane().add(userTaxiKmField);
        
        
        JButton detailedCostButton = new JButton("Reserve for User");
        detailedCostButton.setFont(new Font("Arial", Font.PLAIN, 20));
        detailedCostButton.setLocation(800, 500);
        detailedCostButton.setSize(350,60);
        String user = (String) userComboBox.getSelectedItem();
        detailedCostButton.addActionListener(e -> {
            if (user == null || (userSelectedFlight == null && userSelectedHotel == null && userSelectedTaxi == null)) {
                JOptionPane.showMessageDialog(this, "Please select a user and least one flight, hotel or taxi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double hotelCost = 0;
            if (userSelectedHotel != null) {
                try {
                    int nights = Integer.parseInt(userHotelDurationField.getText().trim());
                    hotelCost = nights * userSelectedHotel.getPrice();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid duration (nights) for hotel.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            double taxiCost = 0;
            if (userSelectedTaxi != null) {
                try {
                    double estimatedDistance = Double.parseDouble(userTaxiKmField.getText().trim());
                    taxiCost = userSelectedTaxi.getPrice() + (estimatedDistance * userSelectedTaxi.getPerKmFare());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid estimated distance for taxi.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            StringBuilder costBreakdown = new StringBuilder("Detailed Cost Breakdown:\n");
            if (userSelectedFlight != null) {
				costBreakdown.append("Flight: ").append(userSelectedFlight);
			}
            if (userSelectedHotel != null) {
                costBreakdown.append("\nHotel: ").append(userSelectedHotel)
                             .append("\n     Nights: ").append(userHotelDurationField.getText())
                             .append("\n     Cost: $").append(String.format("%.2f", hotelCost)).append("\n");
            }
            if (userSelectedTaxi != null) {
                costBreakdown.append("Taxi: ").append(userSelectedTaxi)
                             .append("\n     Estimated Distance: ").append(userTaxiKmField.getText())
                             .append(" km\n     Cost: $").append(String.format("%.2f", taxiCost)).append("\n");
            }
            costBreakdown.append("Total Cost: $" + String.format("%.2f", (userSelectedFlight.getPrice() + hotelCost + taxiCost)));
            int result = JOptionPane.showConfirmDialog(this, costBreakdown.toString(),
                    "Confirm Reservation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                	String selectedUser = (String) userComboBox.getSelectedItem();
                	String selectedUsername = selectedUser.split(" ")[0];
                    FileWriter writer = new FileWriter("reservations.txt", true);

                    if (userSelectedFlight != null) {
                        writer.write(selectedUsername + ",Flight," + userSelectedFlight + " reserved by Admin" + "\n");
                    }
                    if (userSelectedHotel != null) {
                        writer.write(selectedUsername + ",Hotel," + userSelectedHotel + ", " + userHotelDurationField.getText() + " nights" + ", Cost:$" + String.format("%.2f", hotelCost)+ " reserved by Admin" + "\n");
                    }
                    if (userSelectedTaxi != null) {
                        writer.write(selectedUsername + ",Taxi," + userSelectedTaxi + ", " + userTaxiKmField.getText() + ", Cost:$" + String.format("%.2f", taxiCost)+ " reserved by Admin" + "\n");
                    }


                    writer.close();
                    JOptionPane.showMessageDialog(this, "Reservation confirmed and saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving reservation details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getContentPane().add(detailedCostButton);
    }

    private void loadFlightData() {
    	/**
    	 * loads Flight data to the combo box
    	 */
        try {
            List<Flight> flights = DatasetParser.parseFlightDataset();
            for (Flight flight : flights) {
                flightComboBox.addItem(flight);
                userFlightComboBox.addItem(flight);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading flight data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHotelData() {
    	/**
    	 * loads Hotel data to the combo box
    	 */
        try {
            List<Hotel> hotels = DatasetParser.parseHotelDataset();
            for (Hotel hotel : hotels) {
                hotelComboBox.addItem(hotel);
                userHotelComboBox.addItem(hotel);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading hotel data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTaxiData() {
    	/**
    	 * loads Taxi data to the combo box
    	 */
        try {
            List<Taxi> taxis = DatasetParser.parseTaxiDataset();
            for (Taxi taxi : taxis) {
                taxiComboBox.addItem(taxi);
                userTaxiComboBox.addItem(taxi);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading taxi data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTravelPackage() {
    	/**
    	 * Saves the travel package that admin created
    	 */
        if (selectedFlight == null || selectedHotel == null || selectedTaxi == null ||
                hotelDurationField.getText().isEmpty() || taxiKmField.getText().isEmpty() || commissionField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields to create a package.", "Incomplete Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int duration = Integer.parseInt(hotelDurationField.getText());
        int km = Integer.parseInt(taxiKmField.getText());
        commissionRate = Double.parseDouble(commissionField.getText());

        double totalCost = selectedFlight.getPrice() + (selectedHotel.getPrice() * duration) + ((selectedTaxi.getPerKmFare() * km + selectedTaxi.getPrice()));
        double commission = totalCost * (commissionRate / 100);
        double finalCost = totalCost + commission;
        String formattedFinalCost = String.format("%.2f", finalCost);

        int confirmation = JOptionPane.showConfirmDialog(this, String.format(
                "Package Details:\nFlight: %s\nHotel: %s (for %d nights)\nTaxi: %s (for %d km)\n" +
                        "Total Cost: $%.2f\nCommission (%.2f%%): $%.2f\nFinal Cost: $%.2f\n\nConfirm this package?",
                selectedFlight, selectedHotel, duration, selectedTaxi, km, totalCost, commissionRate, commission, finalCost),
                "Confirm Package", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("travel-packages.txt", true))) {
                writer.write(selectedFlight + ";" + selectedHotel + "," + duration + ";" + selectedTaxi + "," + km + ";" + commissionRate + ";" + formattedFinalCost);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Travel package created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving travel package.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAllReservations() {
    	/**
    	 * Shows all the reservations belongs to the all users
    	 */
        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            List<String> reservations = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                reservations.add(line);
            }

            if (reservations.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No reservations found.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JList<String> reservationList = new JList<>(reservations.toArray(new String[0]));
            reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(reservationList);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            int option = JOptionPane.showConfirmDialog(this, scrollPane, "All Reservations", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int selectedIndex = reservationList.getSelectedIndex();
                if (selectedIndex != -1) {
                    reservations.remove(selectedIndex);
                    saveUpdatedReservations(reservations);
                    JOptionPane.showMessageDialog(this, "Reservation canceled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading reservations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveUpdatedReservations(List<String> reservations) {
    	/**
    	 * Saves the updated version of users reservations
    	 */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", false))) {
            for (String reservation : reservations) {
                writer.write(reservation);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving updated reservations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
     
        
    }

    private void addUserSection() {
    	/**
    	 * Adds user section GUI components
    	 */
        JLabel userLabel = new JLabel("Select a User:");
        userLabel.setBounds(650, 100, 120, 30);
        getContentPane().add(userLabel);

        userComboBox = new JComboBox<>();
        userComboBox.setBounds(750, 100, 440, 30);
        loadUserData();
        getContentPane().add(userComboBox);
    }

    private void loadUserData() {
    	/**
    	 * Adds user data to the combo box
    	 */
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                userComboBox.addItem(line);
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading user data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void handleTravelPackagesButton() {
    	/**
    	 * Handles the travel package creation and deletion process
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

        JButton deleteButton = new JButton("Delete Selected Package");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteTravelPackage(packageList.getSelectedValue()));
        travelPackagesFrame.getContentPane().add(deleteButton, BorderLayout.SOUTH);

        packageList.addListSelectionListener(e -> deleteButton.setEnabled(packageList.getSelectedValue() != null));

        try (BufferedReader reader = new BufferedReader(new FileReader("travel-packages.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                packageListModel.addElement(line);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(travelPackagesFrame, "Error loading travel packages.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        travelPackagesFrame.setVisible(true);
    }
    private void deleteTravelPackage(String travelPackage) {
    	/**
    	 * Deletes the travel package given the string of the package information
    	 */
    	File inputFile = new File("travel-packages.txt");
        File tempFile = new File("travel-packages-temp.txt");
    	try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(travelPackage)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            JOptionPane.showMessageDialog(this, "Travel Package deleted successfully.", "Delete Travel Package", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error updating travel-packages file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
    	
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(this, "Error updating travel-packages file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    private void seeAllUserStats() {
    	/**
    	 * Shows all users stats in a JOptionPane
    	 */
       
        Map<String, int[]> userStats = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0]; 
              
                userStats.putIfAbsent(username, new int[]{0, 0}); 

                int[] stats = userStats.get(username);
                stats[0]++;
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
                } else if (line.contains("Flight")) {
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
                stats[1] += cost;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading reservation data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        StringBuilder statsDisplay = new StringBuilder();
        for (Map.Entry<String, int[]> entry : userStats.entrySet()) {
            String username = entry.getKey();
            int totalReservations = entry.getValue()[0];
            double totalCost = entry.getValue()[1];
            statsDisplay.append(String.format("%s, %d reservations, $%.2f total cost\n", username, totalReservations, totalCost));
        }
        JOptionPane.showMessageDialog(null, statsDisplay.toString(), "User Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

}
