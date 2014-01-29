package radikal_parser;

import java.util.ArrayList;

import org.joda.time.DateTime;

import stemmer.TimeOfDay;
import KeywordExtraction.AnnotatedKeyword;

public class Program implements Comparable<Program>{
	private String name;
	private String date;
	private String startTimeStr;
	private String endTimeStr;
	private int duration; 
	private long startTime;
	private long endTime;// IMPORTANT!! to be calculated during parsing
	private ArrayList<String> genreList;
	private String summary;
	private String longDescription;
	private ArrayList<String> directors;
	private ArrayList<String> actors;
	private ArrayList<String> stemmedWords;
	private ArrayList<AnnotatedKeyword> annotatedEntities;
	private ArrayList<TimeOfDay> timeOfDay;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public ArrayList<String> getGenre() {
		return genreList;
	}
	public void setGenre(ArrayList<String> genreList) {
		this.genreList = genreList;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLong_description() {
		return longDescription;
	}
	public void setLong_description(String long_description) {
		this.longDescription = long_description;
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
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public ArrayList<String> getStemmedWords() {
		return stemmedWords;
	}
	public void setStemmedWords(ArrayList<String> stemmedWords) {
		this.stemmedWords = stemmedWords;
	}
	public ArrayList<AnnotatedKeyword> getAnnotatedEntities() {
		return annotatedEntities;
	}
	public void setAnnotatedEntities(ArrayList<AnnotatedKeyword> annotatedEntities) {
		this.annotatedEntities = annotatedEntities;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	public int compareTo(Program p) {
		// TODO Auto-generated method stub
	    return Long.compare(startTime, p.startTime) ;
	}
	
	private boolean intervalOverlap(long firstStart,long firstEnd,long secondStart,long secondEnd)
	{
		return (firstStart < secondEnd) && (firstEnd > secondStart);
	}
	// gets the current days' zero as input, and calculate&set the timeOfDay for a program.
	public void calculateTimeOfDay(DateTime midnight)
	{
		// first calculate the interval end points for the dayparts
		// EARLY MORNING 04-00-07:00
		// BREAKFEAST 07:00-09:00
		// LATE MORNING 09:00-13:00
		// DAYTIME 13:00-18:00
		// EVENING 18:00-20:30
		// PRIME TIME 20:30-00:00
		// NIGHT 00:00-04:00
		ArrayList<TimeOfDay> timeOfDays = new ArrayList<TimeOfDay>();
		long midnightTimeStamp = midnight.getMillis() / 1000L ;
		long nightStart = midnightTimeStamp;
		long nightEnd = nightStart + (240*60) ; // 4 hours
		long earlyMorningStart = nightEnd;
		long earlyMorningEnd = earlyMorningStart + (180*60) ; //3 hours
		long breakfeastStart = earlyMorningEnd ;
		long breakfeastEnd = breakfeastStart + (120*60) ; //2 hours
		long lateMorningStart = breakfeastEnd ;
		long lateMorningEnd = lateMorningStart + (240*60) ; // 4 hours 
		long daytimeStart = lateMorningEnd ;
		long daytimeEnd = daytimeStart + (300*60) ; // 5 hours 
		long eveningStart = daytimeEnd;
		long eveningEnd = eveningStart + (150*60); // 2.5 hours
		long primeTimeStart = eveningEnd ;
		long primeTimeEnd = primeTimeStart + (210*60); //3.5 hours
		if (intervalOverlap(startTime,endTime,nightStart,nightEnd))
			timeOfDays.add(TimeOfDay.NIGHT);
		if (intervalOverlap(startTime,endTime,earlyMorningStart,earlyMorningEnd))
			timeOfDays.add(TimeOfDay.EARLY_MORNING);
		if (intervalOverlap(startTime,endTime,breakfeastStart,breakfeastEnd))
			timeOfDays.add(TimeOfDay.BREAKFEAST);
		if (intervalOverlap(startTime,endTime,lateMorningStart,lateMorningEnd))
			timeOfDays.add(TimeOfDay.LATE_MORNING);
		if (intervalOverlap(startTime,endTime,daytimeStart,daytimeEnd))
			timeOfDays.add(TimeOfDay.DAYTIME);
		if (intervalOverlap(startTime,endTime,eveningStart,eveningEnd))
			timeOfDays.add(TimeOfDay.EVENING);
		if (intervalOverlap(startTime,endTime,primeTimeStart,primeTimeEnd))
			timeOfDays.add(TimeOfDay.PRIME_TIME);
		
		
		System.out.println(timeOfDays);
		this.setTimeOfDay(timeOfDays);	
	}

	public ArrayList<TimeOfDay> getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(ArrayList<TimeOfDay> timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	@Override
	public String toString() {
		return "Program [name=" + name + ", date=" + date + ", startTimeStr="
				+ startTimeStr + ", endTimeStr=" + endTimeStr + ", duration="
				+ duration + ", startTime=" + startTime + ", endTime="
				+ endTime + ", genreList=" + genreList + ", summary=" + summary
				+ ", longDescription=" + longDescription + ", directors="
				+ directors + ", actors=" + actors + ", stemmedWords="
				+ stemmedWords + ", annotatedEntities=" + annotatedEntities
				+ ", timeOfDay=" + timeOfDay + "]";
	}

}
