package KeywordExtraction;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public class DBPediaSpotlight {

	public Vector<String> getProperNames(String text) throws Exception
	{
		String spottedKeyword = null;
		Vector<String> spottedKeywords = new Vector<>();
		DBPediaSpotlightAnnotationRequest annotationRequest = new DBPediaSpotlightAnnotationRequest();
		String response = annotationRequest.spotlightRequest(text);
		System.out.println(response);
		JSONObject jsonObj = new JSONObject(response);
		JSONArray arr = jsonObj.getJSONObject("annotation").getJSONArray("surfaceForm");
		int length = arr.length();
		for (int i = 0; i < length; i++)
		{
		    spottedKeyword = arr.getJSONObject(i).getString("@name");
		    System.out.println(spottedKeyword);
		    spottedKeywords.add(spottedKeyword);
		}
		return spottedKeywords;
	}
}
