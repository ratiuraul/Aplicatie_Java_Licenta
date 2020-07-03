package serial_com;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.event.ActionEvent;
import org.json.*;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class SearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String chosenCriteria;
	List<JSONObject> initialList = null;
	List<JSONObject> resultList = null;
	List<JSONObject> partialList = null;
	JsonServices js = new JsonServices();
	JTextPane textPane = new JTextPane();


	public List<JSONObject> search(List<JSONObject> initialList) throws JSONException {

		List<JSONObject> sortedList = new ArrayList<JSONObject>();
		for (JSONObject obj : initialList) {
			if ((obj.get(chosenCriteria).toString().toUpperCase()).equals(textField.getText().toUpperCase())) {
				sortedList.add(obj);
			}
		}
		return sortedList;
	}

	public void displayResults() throws JSONException {

		String formatted_string = "";
		int i = 1;
		int spacesToIndentEachLevel = 2;
		if (resultList.isEmpty()) {
			textPane.setText("Nici un rezultat pentru cautarea aleasa");
			textPane.setForeground(Color.RED);
		} else {
			for (JSONObject obj : initialList) {
				formatted_string = formatted_string + "[" + i + "]" + "." + obj.toString(spacesToIndentEachLevel)
						+ '\n';
				i++;
			}
			textPane.setText(formatted_string);
			textPane.setForeground(Color.BLACK);
		}
	} 

	public SearchFrame() {
		this.setTitle("Cauta Pacient");
		setBounds(100, 100, 560, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnUrmatorulRezultat = new JButton("Urmatorul rezultat");
		btnUrmatorulRezultat.setBounds(194, 449, 137, 25);
		contentPane.add(btnUrmatorulRezultat);

		JLabel lblRezultatCautare = new JLabel("Rezultat cautare:");
		lblRezultatCautare.setBounds(12, 115, 126, 25);
		contentPane.add(lblRezultatCautare);

		JLabel lblCautareDupa = new JLabel("Cautare dupa : ");
		lblCautareDupa.setBounds(12, 34, 98, 25);
		contentPane.add(lblCautareDupa);

		JComboBox serchList = new JComboBox();
		serchList.setBounds(108, 35, 206, 22);
		contentPane.add(serchList);

		try {
			DbConnector db = new DbConnector();
			ResultSetMetaData rsmd = db.myRs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			initialList = js.getFormattedResult(db.myRs);
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				serchList.addItem(name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JButton btnSearch = new JButton("Cauta");
		btnSearch.setBounds(361, 34, 169, 71);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chosenCriteria = serchList.getSelectedItem().toString();
				try {
					partialList = initialList;
					initialList = search(initialList);
					System.out.println(initialList);
					resultList = initialList;
					displayResults();
					initialList = partialList;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnSearch);

		textField = new JTextField();
		textField.setBounds(108, 80, 206, 25);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblValoare = new JLabel("Valoare :");
		lblValoare.setBounds(12, 86, 56, 16);
		contentPane.add(lblValoare);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 146, 518, 279);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(textPane);
	}
}
