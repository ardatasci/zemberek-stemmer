package KeywordAugmentation.Freebase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;




public class FreebaseTVProgramRequest extends FreebaseRequest{
	 
	String queryType;
	
	public String findDirector(String filmName) throws IOException, ParseException
	  {
	    
	    String ret;
	    List<String> newList = new ArrayList<String>();
	    
	      String query = "[{" +
	          "\"name\":\"" + filmName + "\"," +
	          "\"" + queryType+ "/directed_by\": [{" +
	          "\"name\": []}]"+
	          "}]";
	      System.out.println(query);
	      JSONArray result = executeQuery(query);
	      newList.addAll(parseResults(result,"$./film/film/directed_by"));
	      
	    ret = newList.toString();
	    return removeUnnecessaryChars(ret);
	  }

	  public Vector<String> findActors(String programName) throws IOException, ParseException, JSONException
	  {
		  	Vector<String> actors = new Vector<>();
			queryType = "/tv/tv_program/regular_cast";
			String ret;
			List<String> newList = new ArrayList<String>();
			
			  String query = "[{" +
			      "\"name\":\"" + programName + "\"," +
			      "\"" + queryType+ "\": [{" +
			      "\"actor\": []}]"+
			      "}]";
			/*String query = "[{type:" + queryType +
							"id:null" +
							"name:" + programName +
							"/tv/tv_program/regular_cast:[{actor: []}]}] ";*/
			
			System.out.println(query);
			JSONArray result = executeQuery(query);
			System.out.println(result);
			if(result.size() == 0)
				return actors;
			JSONObject result_obj = (JSONObject) result.get(0);
			System.out.println(result_obj);
			JSONArray actorArray = (JSONArray) result_obj.get("/tv/tv_program/regular_cast");
			System.out.println("actor array");
			System.out.println(actorArray);
			for(int i=0;i<actorArray.size();i++)
			{
				JSONObject actor = (JSONObject) actorArray.get(i);
				JSONArray actorArrayInside = (JSONArray) actor.get("actor");	
				System.out.println(actorArrayInside);
				if(actorArrayInside.size() != 0)
				{
					String pure_actor = (String) actorArrayInside.get(0);
					actors.add(pure_actor);
				}
			}
			
			/*System.out.println(actorArrayInside.get(0));
			newList.addAll(parseResults(result,"$./tv/tv_program/regular_cast"));
			System.out.println("new list");
			System.out.println(newList);
			ret = newList.toString();
			return removeUnnecessaryChars(ret);*/
			return actors;
	    
	  }
}
