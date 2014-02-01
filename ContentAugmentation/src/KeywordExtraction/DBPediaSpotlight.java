package KeywordExtraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBPediaSpotlight {

	DBPediaSpotlightAnnotationRequest annotationRequest;
	Vector<String> spottedKeywordTypes;
	
	public DBPediaSpotlight()
	{
		annotationRequest = new DBPediaSpotlightAnnotationRequest();	
		spottedKeywordTypes = new Vector<>();
	}
	
	public Vector<String> getSpottedProperNames(String text) throws Exception
	{
		String spottedKeyword = null;
		Vector<String> spottedKeywords = new Vector<>();
		if(!text.isEmpty())
		{
			String response = annotationRequest.spotlightSpotRequest(text);
			//System.out.println(response);
			JSONObject jsonObj = new JSONObject(response);
			//System.out.println(jsonObj.getJSONObject("annotation"));
			if(jsonObj.getJSONObject("annotation").has("surfaceForm"))
			{
				JSONArray arr = jsonObj.getJSONObject("annotation").getJSONArray("surfaceForm");
				int length = arr.length();
				for (int i = 0; i < length; i++)
				{
				    spottedKeyword = arr.getJSONObject(i).getString("@name");
				    //System.out.println(spottedKeyword);
				    spottedKeywords.add(spottedKeyword);
				}	
			}	
		}

		

		return spottedKeywords;
	}
	public Vector<AnnotatedKeyword> getAnnotatedProperNames(String text) throws Exception
	{
		Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		if(!text.isEmpty())
		{
			//for original text
			this.addAnnotatedNamestoList(spottedKeywords, text);
			//for uppercase text
			//this.addAnnotatedNamestoList(spottedKeywords, text.toUpperCase());
			//for lowercase text
			//this.addAnnotatedNamestoList(spottedKeywords, text.toLowerCase());
			//for only first letter uppercase text
			//this.addAnnotatedNamestoList(spottedKeywords, WordUtils.capitalize(text.toLowerCase()));
		}
		

		return spottedKeywords;
	}
	
	private void addAnnotatedNamestoList(Vector<AnnotatedKeyword> spottedKeywords, String text) throws Exception
	{
		String spottedKeyword = null;
		String typeResponse = null;
		String response = annotationRequest.spotlightAnnotateRequest(text);
		//System.out.println(response);
		JSONObject jsonObj = new JSONObject(response);		
		if(jsonObj.has("Resources"))
		{
			System.out.println(jsonObj.getJSONArray("Resources"));
			JSONArray arr = jsonObj.getJSONArray("Resources");
			System.out.println(arr.length());
			int length = arr.length();
			for (int i = 0; i < length; i++)
			{
			    spottedKeyword = arr.getJSONObject(i).getString("@surfaceForm");
			    typeResponse = arr.getJSONObject(i).getString("@types");
			    AnnotatedKeyword annotatedKeyword = new AnnotatedKeyword(spottedKeyword, getTypeOfKeyword(typeResponse));
			   // System.out.println(spottedKeyword);
			    if(!isIncludeAnnotatedKeyword(spottedKeywords, annotatedKeyword))
			    	spottedKeywords.add(annotatedKeyword);
			}	
		}	
	}
	
	public Vector<String> getTypeOfKeyword(String typeResponse)
	{
		Vector<String> types = new Vector<>();
		List<String> typeList = Arrays.asList(typeResponse.split(","));
		for (String string : typeList) {
			List<String> type = Arrays.asList(string.split(":"));
			if(type.get(0).equalsIgnoreCase("DBpedia"))
				types.add(type.get(1));
		}

		return types;
	}
	
	private boolean isIncludeAnnotatedKeyword(Vector<AnnotatedKeyword> spottedKeywords, AnnotatedKeyword annotatedKeyword)
	{
		for (AnnotatedKeyword keyword : spottedKeywords) {
			if(keyword.getKeyword().equalsIgnoreCase(annotatedKeyword.getKeyword()))
				return true;
		}
		return false;
	}
	
	
}
