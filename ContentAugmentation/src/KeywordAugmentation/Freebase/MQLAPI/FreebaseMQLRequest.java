package KeywordAugmentation.Freebase.MQLAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;



public class FreebaseMQLRequest {

	  public static Properties properties = new Properties();
	  public HttpTransport httpTransport = new NetHttpTransport();
	  public HttpRequestFactory requestFactory;
	  JSONParser parser = new JSONParser();
	  GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/mqlread");
	
	  public void generateInstance() throws FileNotFoundException, IOException
	  {
		  //System.out.println("begin");
	      properties.load(new FileInputStream("freebase.properties"));
	      requestFactory = httpTransport.createRequestFactory();
	      //System.out.println("end");
	  }
	  
	  
	  public JSONArray executeQuery(String query) throws IOException, ParseException
	  {
	    url.put("query", query);
	    url.put("key", properties.get("API_KEY"));
	    
	    HttpRequest request = requestFactory.buildGetRequest(url);
	    HttpResponse httpResponse = request.execute();
	    JSONObject response = (JSONObject)parser.parse(httpResponse.parseAsString());
	    JSONArray results = (JSONArray)response.get("result");
	    if(results != null)
	      return results;
	    else return null;
	  }
	  
	  protected Vector<String> parseResults(JSONArray results, String parseType)
	  {
		
		String personType = "";
		if(parseType == "/tv/tv_program/regular_cast")
			personType = "actor";
		else if(parseType == "/tv/tv_program/regular_personal_appearances")
			personType = "person";
		Vector<String> actors = new Vector<>();
		if(results.size() == 0)
			return actors;
		JSONObject result_obj = (JSONObject) results.get(0);
		//System.out.println(result_obj);
		JSONArray actorArray = (JSONArray) result_obj.get(parseType);
		//System.out.println("actor array");
		//System.out.println(actorArray);
		for(int i=0;i<actorArray.size();i++)
		{
			JSONObject actor = (JSONObject) actorArray.get(i);
			JSONArray actorArrayInside = (JSONArray) actor.get(personType);	
			//System.out.println(actorArrayInside);
			if(actorArrayInside.size() != 0)
			{
				String pure_actor = (String) actorArrayInside.get(0);
				actors.add(pure_actor);
			}
		}
		
		return actors;
	  }
	  
	  protected String removeUnnecessaryChars(String inp)
	  {
	    if(inp == null)
	      return null;
	   // inp = inp.replaceAll("\\\"", "");
	    inp = inp.replaceAll("\\[\\[", "");
	    inp = inp.replaceAll("\\]\\]", "");
	    return inp;
	          
	  }
}
