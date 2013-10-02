import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SemanticEnhancement {
	
	public void IMDBEnhancement(String programName) throws IOException {
		URL imdb;
		HttpURLConnection imdb_connection;
		try {
			
			
			imdb = new URL("http://deanclatworthy.com/imdb/");
			imdb_connection = (HttpURLConnection) imdb.openConnection();
			imdb_connection.setDoOutput(true);
			imdb_connection.setRequestMethod("GET");
			imdb_connection.setRequestProperty("Accept", "application/json");
			String urlParameters = "q=";
			urlParameters = urlParameters.concat(URLEncoder.encode(programName));
			System.out.println(urlParameters);
			imdb_connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(imdb_connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();	
			imdb_connection.connect();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                		imdb_connection.getInputStream()));
	        String inputLine;
	        String output = "";
	        while ((inputLine = in.readLine()) != null) {
	            output += inputLine;
	        	System.out.println(inputLine);
	        }
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
        
        return;
		
	}
}
