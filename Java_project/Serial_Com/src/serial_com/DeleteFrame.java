package serial_com;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.event.ActionEvent;
import org.json.*;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class DeleteFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextPane textPane = new JTextPane();
	List<JSONObject> initialList = null;
	List<JSONObject> deletionList = null;
	JsonServices js = new JsonServices();
	private boolean idCompleted = true;
	private boolean emptyFields = false;
	private boolean patientNotFound = true;

	public void patientNotFound() throws JSONException {
		for (JSONObject obj : initialList) {
			if (obj.get("ID").equals(textField_2.getText()) || obj.get("NUME").equals(textField.getText())
					|| obj.get("PRENUME").equals(textField_1.getText())) {
				patientNotFound = false;
			}
		}
	}

	public void dispaly(List<JSONObject> initialList) throws JSONException {
		String s = "VA RUGAM COMPLETATI CAMPUL ID SI INCERCATI DIN NOU !\n"
				+ "URMATORII PACIENTI CORESPUND CRITERIILOR SLECTATE\n";

		if (idCompleted) {
			for (JSONObject obj : initialList) {
				if (obj.get("ID").equals(textField_2.getText())) {
					textPane.setForeground(Color.green);
					textPane.setText("Pacientul " + obj.get("NUME") + " " + obj.get("PRENUME") + " cu ID-ul:"
							+ obj.get("ID") + "\n" + "A fost sters cu succes din baza de date");
					textPane.setForeground(Color.black);

				}
			}
		} else if (emptyFields == false) {
			for (JSONObject obj : initialList) {
				if ((obj.get("NUME").equals(textField.getText()))
						|| (obj.get("PRENUME").equals(textField_1.getText()))) {
					s = s + obj.get("NUME") + " " + obj.get("PRENUME") + " cu ID-ul:" + obj.get("ID") + "\n";
					textPane.setText(s);
				}
			}
		} else {
			emptyFields = true;
			textPane.setForeground(Color.red);
			textPane.setText("VA RUGAM COMPLETATI CEL PUTIN UN CAMP PENTRU STERGERE !\n");
		}
		// }
	}

	public DeleteFrame() throws JSONException, SQLException {

		final DbConnector db = new DbConnector();

		this.setTitle("Sterge Pacient");
		setBounds(100, 100, 559, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(0, 23, 541, 36);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(0, 81, 541, 36);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(0, 141, 541, 36);
		contentPane.add(textField_2);
		Statement st = db.myConn.createStatement();
		initialList = JsonServices.getFormattedResult(db.myRs);

		JButton btnUrmatorulRezultat = new JButton("Sterge");
		btnUrmatorulRezultat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					patientNotFound();
					if (patientNotFound) {
						patientNotFound = true;
						textPane.setForeground(Color.red);
						textPane.setText("Nu a fost gasit nici un pacient cu aceste date");
					} else {
						if (textField_1.getText().isEmpty() && textField_2.getText().isEmpty()
								&& textField.getText().isEmpty()) {
							emptyFields = true;
						} else {
							emptyFields = false;
						}
						if (textField_2.getText().isEmpty()) {
							idCompleted = false;
						} else {
							idCompleted = true;
						}
						dispaly(initialList);
						if (idCompleted && (emptyFields == false) && (patientNotFound == false)) {
							st.executeUpdate("DELETE FROM patients " + "WHERE ID = " + textField_2.getText());
						}
					}
				} catch (SQLException | JSONException e) {
					e.printStackTrace();
				}
			}
		});
		btnUrmatorulRezultat.setBounds(191, 393, 137, 25);
		contentPane.add(btnUrmatorulRezultat);

		JLabel lblNume = new JLabel("Nume");
		lblNume.setBounds(0, 0, 75, 29);
		contentPane.add(lblNume);

		JLabel lblPrenume = new JLabel("Prenume");
		lblPrenume.setBounds(0, 54, 75, 29);
		contentPane.add(lblPrenume);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(0, 116, 75, 29);
		contentPane.add(lblId);

		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(0, 181, 62, 25);
		contentPane.add(lblLogs);

		textPane.setBounds(22, 203, 507, 177);
		contentPane.add(textPane);

	}
}
