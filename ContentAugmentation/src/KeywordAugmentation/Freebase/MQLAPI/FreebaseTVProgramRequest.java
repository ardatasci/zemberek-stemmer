package KeywordAugmentation.Freebase.MQLAPI;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;





public class FreebaseTVProgramRequest extends FreebaseMQLRequest{
	 
	String queryType;
	
	public String findDirector(String filmName) throws IOException, ParseException
	  {
	    
	    String ret="";
	    return ret;
	  }

	  public Vector<String> findActorsbyName(String programName) throws IOException, ParseException, JSONException
	  {
		  	Vector<String> actors = new Vector<>();
		  	Vector<String> persons = new Vector<>();
		  	Vector<String> totalCast = new Vector<>();
		  	String query = "";
		  	JSONArray results;
			queryType = "/tv/tv_program/regular_cast";
			
			  /*String query = "[{" +
			      "\"name\":\"" + programName + "\"," +
			      "\"" + queryType+ "\": [{" +
			      "\"actor\": []}]"+
			      "}]";*/
			query = "[{" +
				      "\"name\":\"" + programName + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			/*String query = "[{type:" + queryType +
							"id:null" +
							"name:" + programName +
							"/tv/tv_program/regular_cast:[{actor: []}]}] ";*/
			
			System.out.println(query);
			results = executeQuery(query);
			actors = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(actors);
			queryType = "/tv/tv_program/regular_personal_appearances";	
			query = "[{" +
				      "\"name\":\"" + programName + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			
			System.out.println(query);
			results = executeQuery(query);
			persons = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(persons);
			return totalCast;
	  }
	  
	  public Vector<String> findActorsbyId(String id) throws IOException, ParseException, JSONException
	  {
		  	Vector<String> actors = new Vector<>();
		  	Vector<String> persons = new Vector<>();
		  	Vector<String> totalCast = new Vector<>();
		  	String query = "";
		  	JSONArray results;
			queryType = "/tv/tv_program/regular_cast";
			
			  /*String query = "[{" +
			      "\"name\":\"" + programName + "\"," +
			      "\"" + queryType+ "\": [{" +
			      "\"actor\": []}]"+
			      "}]";*/
			query = "[{" +
				      "\"id\":\"" + id + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			/*String query = "[{type:" + queryType +
							"id:null" +
							"name:" + programName +
							"/tv/tv_program/regular_cast:[{actor: []}]}] ";*/
			
			System.out.println(query);
			results = executeQuery(query);
			actors = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(actors);
			queryType = "/tv/tv_program/regular_personal_appearances";	
			query = "[{" +
				      "\"id\":\"" + id + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			
			System.out.println(query);
			results = executeQuery(query);
			persons = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(persons);
			return totalCast;
	  }
	  
	  public Vector<String> findActorsbyMid(String mId) throws IOException, ParseException, JSONException
	  {
		  	Vector<String> actors = new Vector<>();
		  	Vector<String> persons = new Vector<>();
		  	Vector<String> totalCast = new Vector<>();
		  	String query = "";
		  	JSONArray results;
			queryType = "/tv/tv_program/regular_cast";
			
			  /*String query = "[{" +
			      "\"name\":\"" + programName + "\"," +
			      "\"" + queryType+ "\": [{" +
			      "\"actor\": []}]"+
			      "}]";*/
			query = "[{" +
				      "\"mid\":\"" + mId + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			/*String query = "[{type:" + queryType +
							"id:null" +
							"name:" + programName +
							"/tv/tv_program/regular_cast:[{actor: []}]}] ";*/
			
			System.out.println(query);
			results = executeQuery(query);
			actors = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(actors);
			queryType = "/tv/tv_program/regular_personal_appearances";	
			query = "[{" +
				      "\"mid\":\"" + mId + "\"," +
				      "\"" + queryType+ "\": [{" +
				      "\"*\": []}]"+
				      "}]";
			
			System.out.println(query);
			results = executeQuery(query);
			persons = parseResults(results, queryType);
			System.out.println(results);
			totalCast.addAll(persons);
			return totalCast;
	  }
}
