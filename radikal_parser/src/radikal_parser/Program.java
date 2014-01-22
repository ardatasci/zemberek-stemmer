package radikal_parser;

import java.util.ArrayList;

public class Program {
	private String date;
	private String time;
	private int duration; 
	private int linuxTime; // IMPORTANT!! to be calculated during parsing
	private String genre;
	private String summary;
	private String long_description;
	private ArrayList<String> directors;
	private ArrayList<String> actors;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getLinuxTime() {
		return linuxTime;
	}
	public void setLinuxTime(int linuxTime) {
		this.linuxTime = linuxTime;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLong_description() {
		return long_description;
	}
	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}
	public ArrayList<String> getDirectors() {
		return directors;
	}
	public void setDirectors(ArrayList<String> directors) {
		this.directors = directors;
	}
	public ArrayList<String> getActors() {
		return actors;
	}
	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}

}
