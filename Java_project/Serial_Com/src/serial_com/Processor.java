package serial_com;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Processor {
	static String response = "";
	List<JSONObject> initialList = null;
	JsonServices js = new JsonServices();

	public String process(String requested_Id) throws JSONException {
		String s = "";
		try {
			DbConnector db = new DbConnector();
			ResultSetMetaData rsmd = db.myRs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			initialList = js.getFormattedResult(db.myRs);
			for (JSONObject obj : initialList) {
				if ((obj.get("CARD_ID")).equals(requested_Id)) {
					s = s + "Pacientul a carui card id este :" + obj.get("CARD_ID") + " " + "Nume: " + obj.get("NUME")
							+ " " + "Prenume: " + obj.get("PRENUME") + " " + "Afectiuni: " + obj.get("AFECTIUNI") + " "
							+ "Medicamente: " + obj.get("MEDICAMENTE") + "\n";
					System.out.print(s);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "a";
	}

}
