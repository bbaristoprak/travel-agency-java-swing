package frames;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import fileHandling.Logger;
import user.Customer;

import javax.swing.JPasswordField;
import java.awt.SystemColor;

public class SignUp extends JFrame implements ActionListener {
	
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField IDfield;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JTextArea warningText;
    private JButton goBackButton;
    private JLabel imageLabel;
    
    public SignUp() {
    	/**
    	 * Initializes sign up GUI components
    	 */
        getContentPane().setLayout(null);
        setSize(800, 450);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel signupLabel = new JLabel("Sign Up to KU Travel Agency");
        signupLabel.setForeground(SystemColor.controlHighlight);
        signupLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        signupLabel.setBounds(199, 6, 451, 46);
        getContentPane().add(signupLabel);
        
        signupButton = new JButton("Sign Up Now!");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 24));
        signupButton.setBounds(6, 291, 259, 46);
        getContentPane().add(signupButton);
        signupButton.addActionListener(this);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(6, 85, 111, 30);
        getContentPane().add(usernameLabel);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setBounds(6, 127, 118, 30);
        getContentPane().add(passwordLabel);
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setBounds(6, 169, 118, 30);
        getContentPane().add(nameLabel);
        
        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        surnameLabel.setBounds(6, 211, 118, 30);
        getContentPane().add(surnameLabel);
        
        
        usernameField = new JTextField();
        usernameField.setBounds(124, 87, 130, 26);
        getContentPane().add(usernameField);
        usernameField.setColumns(10);
        
        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setBounds(124, 171, 130, 26);
        getContentPane().add(nameField);
        
        surnameField = new JTextField();
        surnameField.setColumns(10);
        surnameField.setBounds(124, 215, 130, 26);
        getContentPane().add(surnameField);
        
        
        IDfield = new JTextField();
        IDfield.setColumns(10);
        IDfield.setBounds(124, 253, 130, 26);
        getContentPane().add(IDfield);
       
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 129, 130, 28);
		getContentPane().add(passwordField);
		
		warningText = new JTextArea();
		warningText.setForeground(Color.RED);
		warningText.setFont(new Font("Arial", Font.PLAIN, 15));
		warningText.setText("Invalid Registration!\nMake sure your username and password are at least 4 characters.");
		warningText.setBounds(17, 349, 516, 49);
		getContentPane().add(warningText);
		warningText.setVisible(false);
		
		goBackButton = new JButton("Go Back");
		goBackButton.setFont(new Font("Arial", Font.PLAIN, 19));
		goBackButton.setBounds(6, 18, 111, 46);
		getContentPane().add(goBackButton);
		
		JLabel IDLabel = new JLabel("ID:");
		IDLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		IDLabel.setBounds(6, 253, 118, 30);
		getContentPane().add(IDLabel);
		
		imageLabel = new JLabel();
		imageLabel.setBounds(266, 61, 528, 276);
		getContentPane().add(imageLabel);
		goBackButton.addActionListener(this);
		
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("travel.png"));
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        imageLabel.setIcon(resizedIcon);

        imageLabel.setIcon(resizedIcon);
		
    }
public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == signupButton) {
			if (usernameField.getText().length() >= 4 && passwordField.getPassword().length >= 4) {
				try {
					FileWriter userDataWriter = new FileWriter("users.txt", true);
		            FileWriter logWriter = new FileWriter("logs.txt", true);
		            String passwordString = new String(passwordField.getPassword());
		            userDataWriter.write(usernameField.getText() + " " + passwordString + " " + nameField.getText() + " " + surnameField.getText() + " " + IDfield.getText() + "\n");
		            userDataWriter.close();
		            logWriter.write("New customer with username "+ usernameField.getText() + " has been registered at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");	            
		            logWriter.close();
		            this.dispose();
		            UserDataOperations userDataOperations = new UserDataOperations();
		            Customer customer = new Customer(usernameField.getText(), passwordString, nameField.getText(), surnameField.getText(), IDfield.getText());
		            Login login = new Login();
		          
		        } catch (IOException exception) {
		        	Logger.log("users.txt file not found at the expected location at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
		        }
			}
			else {
				warningText.setVisible(true);
			}
		}
		
		else if (e.getSource() == goBackButton) {
			this.dispose();
			UserDataOperations userDataOperations = new UserDataOperations();
			Login login = new Login();
		}
	}
}
