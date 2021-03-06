package serial_com;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class AddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JLabel lblId;
	private JLabel lblMedicamente;
	private JLabel lblA;
	private JLabel lblDozaj;
	private JLabel lblStareGenerala;
	private JTextField textField_7;
	private JLabel lblLogs;

	/**
	 * Create the frame.
	 * 
	 */
	private Boolean invalidValue = false;
	private JTextField textField_8;

	public boolean checkFields(JTextField nume, JTextField prenume, JTextField id, JTextField afectiuni,
			JTextField medicamente, JTextField dozaj, JTextField stare_generala) {

		List<String> l = Arrays.asList(id.getText(), nume.getText(), prenume.getText(), afectiuni.getText(),
				medicamente.getText(), dozaj.getText(), stare_generala.getText());

		for (String s : l) {
			if (s.isEmpty()) {
				return false;
			}
		}
		if ((id.getText().matches("[0-9]+") == false) || (dozaj.getText().matches("[0-9]+") == false)) {
			this.invalidValue = true;
			return false;
		}
		return true;
	}

	public String getText(JTextField nume, JTextField prenume, JTextField id, JTextField afectiuni,
			JTextField medicamente, JTextField dozaj, JTextField stare_generala, JTextField card_id) {

		String result = "";
		List<String> l = Arrays.asList(id.getText(), nume.getText(), prenume.getText(), afectiuni.getText(),
				medicamente.getText(), dozaj.getText(), stare_generala.getText(),card_id.getText());

		for (String s : l) {
			if (s.isEmpty()) {
				return "-1";
			}

		}
		result = l.get(0) + " ,'" + l.get(1) + "'" + " ,'" + l.get(2) + "'" + " ,'" + l.get(3) + "'" + " ,'" + l.get(4)
				+ "'" + " ," + l.get(5) + " ,'" + l.get(6) + "'" + " ,'" + l.get(7)+ "'";
		return result;
	}

	public AddFrame() throws SQLException {

		// get db connection
		final DbConnector db = new DbConnector();

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Adauga Pacient");
		setBounds(100, 100, 561, 652);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(0, 27, 541, 31);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(0, 85, 541, 31);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(0, 143, 541, 31);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(0, 198, 541, 31);
		contentPane.add(textField_3);

		JButton btnAdd = new JButton("Adauga");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (checkFields(textField, textField_1, textField_2, textField_3, textField_4, textField_5,
						textField_6)) {
					try {
						Statement st = db.myConn.createStatement();
						String s = getText(textField, textField_1, textField_2, textField_3, textField_4, textField_5,
								textField_6, textField_8);
						System.out.println("INSERT INTO medicalschema.patients " + "VALUES(" + s + ")");
						st.executeUpdate("INSERT INTO medicalschema.patients " + "VALUES(" + s + ")");
						textField_7.setText("Pacientul a fost adaugat cu succes");
						textField_7.setForeground(Color.GREEN);
						
					} catch (java.sql.SQLIntegrityConstraintViolationException e2) {
						textField_7.setText("ID-ul pacientului trebuie sa fie unic");
						textField_7.setForeground(Color.RED);
					}
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					if (invalidValue) {
						textField_7
								.setText("Campul pentru ID si cel pentru DOZAJ trebuie sa contina o valoare numerica");
						textField_7.setForeground(Color.RED);
					} else {
						textField_7.setText("Completati toate capurile libere");
						textField_7.setForeground(Color.RED);
					}
				}
			}
		});
		btnAdd.setBounds(226, 568, 97, 25);
		contentPane.add(btnAdd);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(0, 257, 541, 31);
		contentPane.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(0, 315, 541, 31);
		contentPane.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(0, 369, 541, 31);
		contentPane.add(textField_6);

		JLabel lblNumePacient = new JLabel("Nume ");
		lblNumePacient.setBounds(0, 13, 97, 16);
		contentPane.add(lblNumePacient);

		JLabel lblPrenumePacient = new JLabel("Prenume "); 
		lblPrenumePacient.setBounds(0, 71, 97, 16);
		contentPane.add(lblPrenumePacient);

		lblId = new JLabel("ID");
		lblId.setBounds(0, 127, 97, 16);
		contentPane.add(lblId);

		lblMedicamente = new JLabel("Afectiuni");
		lblMedicamente.setBounds(0, 180, 97, 16);
		contentPane.add(lblMedicamente);

		lblA = new JLabel("Medicamente");
		lblA.setBounds(0, 238, 97, 16);
		contentPane.add(lblA);

		lblDozaj = new JLabel("Dozaj");
		lblDozaj.setBounds(0, 301, 97, 16);
		contentPane.add(lblDozaj);

		lblStareGenerala = new JLabel("Stare generala");
		lblStareGenerala.setBounds(0, 352, 97, 16);
		contentPane.add(lblStareGenerala);

		textField_7 = new JTextField();
		textField_7.setBounds(0, 518, 541, 37);
		contentPane.add(textField_7);
		textField_7.setColumns(10);

		lblLogs = new JLabel("Logs :");
		lblLogs.setBounds(0, 489, 56, 16);
		contentPane.add(lblLogs);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(0, 439, 541, 37);
		contentPane.add(textField_8);
		
		JLabel lblCardId = new JLabel("Card ID:");
		lblCardId.setBounds(0, 413, 56, 16);
		contentPane.add(lblCardId);
	}
}
