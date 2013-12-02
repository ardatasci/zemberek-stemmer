import java.net.*;
import java.io.*;

import org.json.*;


public class Main {
	


	public static void main(String[] args) throws JSONException {
		
		
		
		try {
			ProgramProfiler profiler = new ProgramProfiler();
			ProgramProfile pp = new ProgramProfile();
			JSONObject program;
			
			// New Class for Program
			String channel = "show_tv";
			int start = 1378671000;
			for(int i = 0; i < 30; i++) {
			program = getProgramInfo(channel, Integer.toString(start));
			pp.setPID(((JSONObject)program.get("broadcast")).getString("program_id"));
			pp.setTimeOfDay(((JSONObject)program.get("broadcast")).getInt("pStart"));
			pp.setTitle(((JSONObject)program.get("broadcast")).getString("pName"));
			pp.setGenre(((JSONObject)program.get("broadcast")).getString("pGenreStr"));
			pp.setTimeOfDay((((JSONObject)program.get("broadcast")).getInt("pStart")));
			pp.setChannel(channel);
			//System.out.println(profiler.dropStopWords(((JSONObject)program.get("broadcast")).getString("sDesc")).toString());
			pp.setBagOfWords(profiler.getNounsFromDesc(profiler.dropStopWords(((JSONObject)program.get("broadcast")).getString("sDesc")).toString()));
			pp.saveProfile();
			start = start - 86400;
			}
			
			
			
			//String desc = ((JSONObject)program.get("broadcast")).getString("sDesc");
			//System.out.println(((JSONObject)program.get("broadcast")));*/
		
		
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
		
		
		

		
		
	}
	
	
	
	
	// to get program info from our epg source
	public static JSONObject getProgramInfo(String channel_name, String time) throws IOException, JSONException {
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
		
		epgSource_connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(epgSource_connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();	
		epgSource_connection.connect();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                		epgSource_connection.getInputStream()));
        JSONObject jsonObj = new JSONObject( in.readLine()); 
        in.close();
        
        System.out.println("Program description is " + ((JSONObject)jsonObj.get("broadcast")).getString("sDesc"));
        
        return jsonObj;	
	}
	
	
	

}


