package KeywordAugmentation.Freebase.SearchAPI;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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



public class FreebaseSearchRequest {

	  public static Properties properties = new Properties();
	  public HttpTransport httpTransport = new NetHttpTransport();
	  public HttpRequestFactory requestFactory;
	  JSONParser parser = new JSONParser();
	  GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/search");
	
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
	    url.put("output","(type)");
	    url.put("lang", "tr");
	    url.put("key", properties.get("API_KEY"));
	    System.out.println(url);
	    HttpRequest request = requestFactory.buildGetRequest(url);
	    HttpResponse httpResponse = request.execute();
	    JSONObject response = (JSONObject) parser.parse(httpResponse.parseAsString());
	    JSONArray results = (JSONArray)response.get("result");
	    if(results != null)
	      return results;
	    else return null;
	  }
	  
	  protected List<String> parseResults(JSONArray results, String parseType)
	  {
	    List<String> ret = new ArrayList<String>();
	    int i = 0;
	    String temp;
	    for (Object result : results)
	    {
	      temp = JsonPath.read(result,parseType).toString();
	      ret.add(temp);
	    }
	    return ret;
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
