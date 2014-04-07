package ResourceCollection.DigiturkEPG;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Vector;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import KeywordAugmentation.Freebase.MQLAPI.FreebaseTVProgramRequest;
import KeywordExtraction.DBPediaSptlight.AnnotatedKeyword;
import KeywordExtraction.DBPediaSptlight.DBPediaSpotlight;


public class ProgramInfo {
	String programID;
	long timeOfDay;
	String title;
	String genre;
	String channelName;
	String description;
	
	Vector<AnnotatedKeyword> annotatedKeywords;
	Vector<String> actors;
	
	FreebaseTVProgramRequest programRequest;
	
	public ProgramInfo()
	{
		this.programID = "";
		this.timeOfDay = 0;
		this.genre = "";
		this.channelName = "";
		this.description ="";
		this.annotatedKeywords = new Vector<>();
		this.actors = new Vector<>();
		programRequest = new FreebaseTVProgramRequest();
	}

	public String getProgramID() {
		return programID;
	}

	public void setProgramID(String programID) {
		this.programID = programID;
	}

	public long getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(long timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Vector<AnnotatedKeyword> getAnnotatedKeywords() {
		return annotatedKeywords;
	}
	
	public Vector<String> getActors() {
		return actors;
	}
	
	public void extractKeywordsFromDescription() throws Exception
	{
		DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		Vector<AnnotatedKeyword> extractedKeywords = new Vector<>();
		//System.out.println("description:");
		//System.out.println(description);
		if(!description.isEmpty())
		{
			String filtered_description = description.replace("'", " ");
			extractedKeywords = dbPediaSpotlight.getAnnotatedProperNames(filtered_description);	
		}
			
		//System.out.println(extractedKeywords);
		for (AnnotatedKeyword spottedKeyword : extractedKeywords) {
			 if(!isIncludeAnnotatedKeyword(annotatedKeywords, spottedKeyword))
		    	this.annotatedKeywords.add(spottedKeyword);
		}
	}
	
	
	private boolean isIncludeAnnotatedKeyword(Vector<AnnotatedKeyword> spottedKeywords, AnnotatedKeyword annotatedKeyword)
	{
		for (AnnotatedKeyword keyword : spottedKeywords) {
			if(keyword.getKeyword().equalsIgnoreCase(annotatedKeyword.getKeyword()))
				return true;
		}
		return false;
	}
	
	public void collectProgramActors() throws IOException, ParseException, JSONException
	{
		programRequest.generateInstance();
		System.out.println(title);
		String name = title.replace("İ", "i");
		name = name.replace("I", "ı");
		System.out.println(name);
		int pos = name.indexOf("/");
		if(pos != -1)
			name = (String) name.subSequence(0, pos);
		System.out.println(name);
		System.out.println(name.toLowerCase());
		actors = programRequest.findActorsbyName(name.toLowerCase());
	}
	
	public void printProgramInfoToFile(FileWriter file) throws IOException
	{
		
		file.write("Program Name: " + this.title + "\n");
		file.write("Genre: " + this.genre + "\n");
		file.write("Description: " + this.description + "\n");
		file.write("Annotated Keywords: ");
		for (AnnotatedKeyword ak : annotatedKeywords) {
			file.write(ak.getKeyword());
			file.write("- [");
			Vector<String> types = ak.getKeywordTypes();
			for (int i=0;i<types.size();i++) {
				file.write(types.get(i));
				if(i != types.size()-1)
					file.write(", ");
			}
			file.write(" ],");
		}
		file.write("\n");
		file.write("Actors: ");
		for (int i=0;i<actors.size();i++) {
			file.write(actors.get(i));
			if(i != actors.size()-1)
				file.write(", ");
		}
		file.write("\n");
		file.write("-------------------------------------------");
		file.write("\n");
		

	}

}
