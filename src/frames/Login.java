package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fileHandling.Logger;
import user.User;
import java.awt.SystemColor;

public class Login extends JFrame implements ActionListener{
	private JFrame frame = new JFrame();
	private JButton loginButton = new JButton("Login");
	private JButton signUpButton = new JButton("Signup");
	private JTextField usernameInputField = new JTextField();
	private JPasswordField passwordInputField = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JLabel loginMessage = new JLabel();
	private JLabel welcomeLabel = new JLabel("KU Travel Agency");
	private HashMap<String, User> loginInfo = new HashMap<String, User>();
	
	public Login() {
		/**
		 * Initializing Login components
		 */
		this.loginInfo = UserDataOperations.loginInfo;
		
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		usernameLabel.setBounds(32,92,86,40);
		frame.setLocation(600,130);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		passwordLabel.setBounds(34,152,91,37);
		
		loginMessage.setBounds(112,297,189,35);
		loginMessage.setFont(new Font("Arial", Font.BOLD, 18));
		loginButton.setForeground(SystemColor.controlHighlight);
		loginButton.setBackground(new Color(222, 184, 135));
		loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
		
		loginButton.setBounds(102, 226, 101, 51);
		loginButton.setFocusable(false); 
		loginButton.addActionListener(this);
		signUpButton.setForeground(SystemColor.controlHighlight);
		signUpButton.setBackground(new Color(222, 184, 135));
		signUpButton.setFont(new Font("Arial", Font.PLAIN, 18));
		
		
		signUpButton.setBounds(205,226,96,51);
		signUpButton.setFocusable(false);
		signUpButton.addActionListener(this);
		
		
		frame.getContentPane().add(usernameLabel);
		frame.getContentPane().add(passwordLabel);
		frame.getContentPane().add(loginMessage);
		frame.getContentPane().add(loginButton);
		frame.getContentPane().add(signUpButton);
		frame.getContentPane().add(loginButton);
		frame.getContentPane().add(signUpButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.getContentPane().setLayout(null);
		usernameInputField.setFont(new Font("Arial", Font.PLAIN, 15));
		
		usernameInputField.setBounds(135, 94, 140, 37);
		frame.getContentPane().add(usernameInputField);
		usernameInputField.setColumns(10);
		passwordInputField.setFont(new Font("Arial", Font.PLAIN, 15));
		
		passwordInputField.setBounds(135, 153, 140, 37);
		frame.getContentPane().add(passwordInputField);
		
		welcomeLabel.setText("KU Travel Agency");
		welcomeLabel.setForeground(SystemColor.controlHighlight);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
		welcomeLabel.setBounds(102, 6, 178, 64);
		frame.getContentPane().add(welcomeLabel);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		/**
		 * When clicked on login, checks the data and validates or not accordingly
		 */
		if (e.getSource() == loginButton) {
			String username = usernameInputField.getText();
			String password = String.valueOf(passwordInputField.getPassword());
			
			if (loginInfo.containsKey(username)) {
				if (loginInfo.get(username).getPassword().equals(password)) {
					frame.dispose();
					if (username.equals("admin")) {
						AdminMenu adminMenu = new AdminMenu();
					}
					else {
						MainMenu mainMenu = new MainMenu(loginInfo.get(username));
					}
					try {
						FileWriter logWriter = new FileWriter("logs.txt", true);
						logWriter.write(username + " has been logged in at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
			            logWriter.close();
						
					} catch (IOException e1) {
						Logger.log("logs.txt file not found at the expected location at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
					}
				}	
				else {
					loginMessage.setForeground(Color.red);
					loginMessage.setText("Wrong password");
					loginMessage.setVisible(true);
				}
			}
			
			else {
				loginMessage.setForeground(Color.red);
				loginMessage.setText("Invalid Username");
				loginMessage.setVisible(true);
			}
		}
		
		if (e.getSource() == signUpButton) {
			frame.dispose();
			SignUp signUp = new SignUp();
		}
	}
}
