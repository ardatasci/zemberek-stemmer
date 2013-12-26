package ResourceCollection.DigiturkEPG;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class DigiturkEPGRequest {
	
	public DigiturkEPGRequest()
	{
		
	}
	
	public JSONObject getProgramInfo(String channel_name, String time) throws IOException, JSONException {
		System.out.println("Getting Program info of channel : " + channel_name + " at " + time);
		URL epgSource;
		HttpURLConnection epgSource_connection;
		epgSource = new URL("http://10.155.10.14/program_detail.json");
		epgSource_connection = (HttpURLConnection) epgSource.openConnection();
		epgSource_connection.setDoOutput(true); 
		epgSource_connection.setRequestMethod("GET");
		epgSource_connection.setRequestProperty("Accept", "application/json");
		String urlParameters = "cIdentifier=";
		urlParameters += channel_name;
		urlParameters += "&time=";
		urlParameters += time;
		System.out.println(epgSource_connection);
		epgSource_connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(epgSource_connection.getOutputStream());
		
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();	
		epgSource_connection.connect();
		System.out.println(epgSource_connection);
		System.out.println("Post parameters : " + urlParameters);
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                		epgSource_connection.getInputStream()));
        JSONObject jsonObj = new JSONObject( in.readLine()); 
        in.close();
        
        System.out.println("Program description is " + ((JSONObject)jsonObj.get("broadcast")).getString("sDesc"));
        
        return jsonObj;	
	}
	
}
