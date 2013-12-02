import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


public class ProgramProfile {
	
	String pID;
	String genre;
	String title;
	String channel;
	TimeOfDay time_of_day;
	List <String> bag_of_words;
	
	ProgramProfile() {
		this.pID = "";
		this.genre = "";
		this.title = "";
		this.channel = "";
		this.bag_of_words = new ArrayList<String>();
	}
	
	ProgramProfile(JSONObject obj) throws JSONException {
		this.pID = obj.getString("pID");
		this.genre = obj.getString("genre");
		this.title = obj.getString("title");
		this.channel = obj.getString("channel");
		this.bag_of_words = new ArrayList<String>();

	}
	
	
	public void setPID(String pID) {
		this.pID = pID; 
	}
	
	public String getChannel() {
		return this.channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel; 
	}
	
	public String getPID() {
		return this.pID;
	}
	
	
	public void setTimeOfDay(int start_time) {
		this.time_of_day = TimeOfDay.AFTERNOON; 
	}
	
	public TimeOfDay getTimeOfDay() {
		return this.time_of_day; 
	}	
	
	public void setGenre(String genre) {
		this.genre = genre.toLowerCase();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title.toLowerCase();
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public void setBagOfWords(List<String> bagOfWords) {
		this.bag_of_words = bagOfWords;
	}
	
	public List <String> getBagOfWords() {
		return this.bag_of_words;
	}
	
	public void printProfile() {
		System.out.println(
				"ID : " + this.pID + " ,\n" +
				"Title : " + this.title + " ,\n" +
				"Genre : " + this.genre + " ,\n" +
				"TimeOfDay : " + this.time_of_day + " ,\n" +
				"Bag Of Words : < " + this.bag_of_words + " >");
	}	
	
	
	public void saveProfile() {
		try {	  
			File file = new File("/tmp/program_profiles/"+this.pID.toString() + ".dat");
			if (!file.exists())  file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(JSONObject.wrap(this).toString());
			bw.close();
			System.out.println(this.pID.toString() + "....Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
