package serial_com;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class JsonServices {

	public static List<JSONObject> getFormattedResult(ResultSet rs) {
		List<JSONObject> resList = new ArrayList<JSONObject>();

		try {
			//get column names
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCnt = rs.getMetaData().getColumnCount();
			
			List<String> columnNames = new ArrayList<String>();
			
			for (int i = 1 ; i<=columnCnt;i++) {
				columnNames.add(rsMeta.getColumnName(i).toUpperCase());
			}
			while(rs.next())
			{
				// convert each object into an human readable JSON object
				JSONObject obj = new JSONObject();
				for(int i = 1; i<=columnCnt; i++)
				{
					String key = columnNames.get(i-1);
					String value = rs.getString(i);
					obj.put(key, value);
				}
				resList.add(obj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resList;
	}
}
