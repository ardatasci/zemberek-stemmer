package KeywordAugmentation.Freebase.SearchAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class FreebaseProgramSearchRequest extends FreebaseSearchRequest {
	  public Vector<String> getProgramSearchResults(String programName) throws IOException, ParseException, JSONException
	  {
		  	Vector<String> actors = new Vector<>();
		  	String query = programName;
		  	JSONArray result = executeQuery(query);
			System.out.println(result.get(0));
		  	
		  	return actors;
	  }
}
