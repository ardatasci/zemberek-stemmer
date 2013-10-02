import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class NamedEntitiyRecognition {
	
	public String dbPedia(String metin) throws IOException {
		URL dbpedia;
		HttpURLConnection dbpedia_connection;
		dbpedia = new URL("http://spotlight.dbpedia.org/rest/annotate");
		dbpedia_connection = (HttpURLConnection) dbpedia.openConnection();
		dbpedia_connection.setDoOutput(true); 
		dbpedia_connection.setRequestMethod("GET");
		dbpedia_connection.setRequestProperty("Accept", "application/json");
		String urlParameters = "confidance=0.5&support=80&text=";
		urlParameters = urlParameters.concat(URLEncoder.encode(metin));
		
		dbpedia_connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(dbpedia_connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();	
		dbpedia_connection.connect();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                		dbpedia_connection.getInputStream()));
        String inputLine;
        String output = "";
        while ((inputLine = in.readLine()) != null) {
            output += inputLine;
        	System.out.println(inputLine);
        }
        in.close();
        return output;
	}

}
