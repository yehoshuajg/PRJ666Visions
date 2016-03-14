package vision;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private Employees employee;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Log in");
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null); //Center's the frame, regardless of screen resolution
		initialize();
	}
	public void initialize(){
		JLabel lblNewLabel_1 = new JLabel("One Stop Dollarama");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(127, 31, 217, 54);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblUsername.setBounds(127, 84, 116, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblPassword.setBounds(127, 112, 89, 14);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(196, 82, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(196, 110, 86, 20);
		contentPane.add(passwordField);
		
		JButton btnLogIn = new JButton("Log in");
		getRootPane().setDefaultButton(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					employee = new Employees();
					String pass = null;
					if(passwordField.getPassword() != null){
						char[] password = passwordField.getPassword();
						pass = new String(password);
					}
					if(employee.validateEmpty(textField.getText()) == false && employee.validateEmpty(pass) == false){
						JOptionPane.showMessageDialog(null,"Please enter a username and a password.");
					}
					else if(employee.validateEmpty(textField.getText()) == false){
						JOptionPane.showMessageDialog(null,"Please enter a username.");
					}
					else if(employee.validateEmpty(pass) == false){
						JOptionPane.showMessageDialog(null,"Please enter a password.");
					}
					else{
						if(employee.login(textField.getText(),pass) == true){
							dispose();
							Home home = new Home();
							home.setEmployee(employee);					
							home.setVisible(true);
						}
						else{
							JOptionPane.showMessageDialog(null,"Incorrect Username or Password.");
						}
					}
				}catch(Exception e){
				}
			}
		});
		btnLogIn.setBounds(165, 140, 89, 23);
		contentPane.add(btnLogIn);
	}
}
