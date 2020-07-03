package serial_com;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String username = "";
	private String password = "";
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.setTitle("Login");
		
		textField = new JTextField();
		textField.setBounds(133, 66, 179, 35);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputUser = textField.getText();
				@SuppressWarnings("deprecation")
				String inputPassword = passwordField.getText();
				
				if (inputUser.equals("user") && inputPassword.equals("password")) {
					
					try {
						MainFrame mf = new MainFrame();
						mf.setVisible(true);
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
				}
				else {
				JOptionPane.showMessageDialog(contentPane,"failure");
				}
			}
		});
		loginBtn.setBounds(156, 206, 128, 35);
		contentPane.add(loginBtn);
		
		JLabel lblNumeUtilizator = new JLabel("Nume utilizator :");
		lblNumeUtilizator.setBounds(26, 68, 109, 30);
		contentPane.add(lblNumeUtilizator);
		
		JLabel lblParola = new JLabel("Parola :");
		lblParola.setBounds(74, 147, 61, 26);
		contentPane.add(lblParola);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(133, 138, 179, 35);
		contentPane.add(passwordField);
	}
}
