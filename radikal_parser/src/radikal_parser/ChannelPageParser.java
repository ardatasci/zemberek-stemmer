package radikal_parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import KeywordExtraction.AnnotatedKeyword;
import KeywordExtraction.DBPediaSpotlight;
import stemmer.ProgramProfiler;


public class ChannelPageParser
{
	
	public ArrayList<String> extractChannelProgramLinks(String channelName){
		ArrayList<String> lst= new ArrayList<>();
		try {
			Document doc = Jsoup.connect("http://www.radikal.com.tr/tvrehberi/" + channelName + "/").get();
			Elements title = doc.getElementsByClass("show-list");
			for (Element e: title)
			{
				Elements headlines= e.getElementsByTag("h4");
				for (Element headline : headlines)
				{
					Elements links = headline.getElementsByTag("a");
					for (Element link : links )
					{
						lst.add(link.attr("href"));
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
		
	}
	
	public Program extractProgramInfo(String programLink,String channelName) throws Exception
	{
		Program program = new Program();
		program.setChannelName(channelName);
		ProgramProfiler profiler = new ProgramProfiler();
		Document doc = Jsoup.parse(new URL(programLink).openStream(), "ISO-8859-9", programLink);
		Element info_area = doc.getElementsByClass("program-ozet").first();
		if (info_area == null)
			return null ;
		Element image_area = info_area.previousElementSibling();
		program.setImageUrl(image_area.attr("src"));
		// long description area is third next element node after summary area
		Element long_description_area = info_area.nextElementSibling().nextElementSibling().nextElementSibling();
		Element longDescriptionParagraph = long_description_area.child(0); // long description area has only one paragraph child
		String longDescription = transformAlperLetters(longDescriptionParagraph.text().trim());
		String programName = transformAlperLetters(info_area.getElementsByTag("h1").first().html().trim());
		program.setName(programName); //set program name
		program.setLongDescription(longDescription); // set long description of the program
		
		// get the stemmed nouns inside long description
		List<String> wordList = profiler.getNounsFromDesc(profiler.dropStopWords(longDescription).toString());
		ArrayList<String> wordArrayList = new ArrayList<String>(wordList.size());
		wordArrayList.addAll(wordList);
		program.setStemmedWords(wordArrayList);	
		// get the annotated entities using dbpedia spotlight
		DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		spottedKeywords = dbPediaSpotlight.getAnnotatedProperNames(longDescription);
		ArrayList<AnnotatedKeyword> spottedKeywordsList = new ArrayList<AnnotatedKeyword>(spottedKeywords);
		program.setAnnotatedEntities(spottedKeywordsList);
		ArrayList<String> info_liste = new ArrayList<String>();
		for (Node child : info_area.childNodes()) {
		    if (child instanceof TextNode) {
		    	info_liste.add(((TextNode) child).text());
		    	System.out.println(((TextNode) child).text());
		    }
		}
		System.out.println("Size of info liste");
		System.out.println(info_liste.size());
		if (info_liste.size() < 5) // not a proper program definition
			return null;
		
		program.setDate(info_liste.get(1).trim());
		
		String date = program.getDate();
		String dayAndYear = date.replaceAll("\\D",""); //get rid of nondigit things
		int day = Integer.parseInt(dayAndYear.substring(0, 2));
		int year = Integer.parseInt(dayAndYear.substring(2));
		int month = Integer.parseInt(transformTurkishMonths(date));
		String time = info_liste.get(2).trim(); // saat
		System.out.println("Time: " + time);
		String[] tempArray = time.split("-"); 
//		// divide string into two by - eg. 02:00 - 04:00 (120 dakika)
		String[] startTimeTempArray = tempArray[0].split(":");
//		// divide 02:00 by :
		int startTimeHour = Integer.parseInt(startTimeTempArray[0].trim()) ; // get 02
		int startTimeMinute = Integer.parseInt(startTimeTempArray[1].trim()); // get 00
		String startTimeStr = startTimeTempArray[0].trim() + ":" + startTimeTempArray[1].trim() ;
		String[] endTimeTempArray = tempArray[1].split("\\(");
		String endTimeStr = endTimeTempArray[0].trim();
		int duration = Integer.parseInt(endTimeTempArray[1].replaceAll("\\D+",""));
		DateTime start = new DateTime(year, month, day,startTimeHour,startTimeMinute );
		DateTimeZone dtz = DateTimeZone.forID("Europe/Istanbul");
		DateTime midnight = new DateTime(year,month,day,0,0,dtz);
		long startTime = start.getMillis() / 1000L;
		long endTime = startTime + (duration * 60) ; // add duration to calculate endTime 
		program.setStartTime(startTime);
		program.setEndTime(endTime);
		program.setStartTimeStr(startTimeStr);
		program.setEndTimeStr(endTimeStr);
		program.setDuration(duration);
		program.calculateTimeOfDay(midnight);
		
		String genre = info_liste.get(3).trim(); // genre
		String[] genreArray = genre.split("&");
		ArrayList<String> genreList = new ArrayList<String>();
		for (String g : genreArray)
		{
			if (g.contains("(")) // if we have a genre like this Dizi (Komedi)
			{
				String[] splittedGenre = g.split("\\(");
				genreList.add(splittedGenre[0].trim()); // add Dizi
				genreList.add(splittedGenre[1].replaceAll("\\)", "").trim());
			}
			else
				genreList.add(g.trim());
		}
		System.out.println(genreList);
		program.setGenre(genreList);
		
		if(info_liste.size() == 5) // no directors and actors exist
			program.setSummary(info_liste.get(4));
		else if (info_liste.size() == 6) // only actors are added.
		{
			String actors = info_liste.get(4);
			ArrayList<String> actorsList = getListOfCommaSeparated(actors);
			program.setActors(actorsList);
			program.setSummary(info_liste.get(5));
		}
		else if (info_liste.size() == 7)
		{
			String directors = info_liste.get(4);
			ArrayList<String> directorsList = getListOfCommaSeparated(directors);
			program.setDirectors(directorsList);
			String actors = info_liste.get(5);
			ArrayList<String> actorsList = getListOfCommaSeparated(actors);
			program.setActors(actorsList);
			program.setSummary(info_liste.get(6));
		}
//		for (Element inf :info_list)
//		{
//			String identifier = transformAlperLetters(inf.getElementsByTag("strong").html().trim());
//			if (identifier.contains("Tarih"))
//			{
//				System.out.println("Tarih bulundu");
//				String date = inf.text().trim().split(":")[1];
//				program.setDate(date);
//				System.out.println(date);
//			}
//			else if (identifier.contains("Saat"))
//			{
//				System.out.println("Saat bulundu");
//				String date = program.getDate();
//				//String[] dateTempArray = date.split("."); somehow our string is not splittable with. 
//				// i canceled this method
//				String dayAndYear = date.replaceAll("\\D",""); //get rid of nondigit things
//				int day = Integer.parseInt(dayAndYear.substring(0, 2));
//				int year = Integer.parseInt(dayAndYear.substring(2));
//				int month = Integer.parseInt(transformTurkishMonths(date));
//				String time = inf.text().trim();
//				String[] tempArray = time.split("-"); 
//				// divide string into two by - eg. Saat :02:00 - 04:00 (120 dakika)
//				String[] startTimeTempArray = tempArray[0].split(":");
//				// divide Saat :02:00 by :
//				int startTimeHour = Integer.parseInt(startTimeTempArray[1].trim()) ; // get 02
//				int startTimeMinute = Integer.parseInt(startTimeTempArray[2].trim()); // get 00
//				String startTimeStr = startTimeTempArray[1].trim() + ":" + startTimeTempArray[2].trim() ;
//				System.out.println(startTimeStr);
//				String[] endTimeTempArray = tempArray[1].split("\\("); 
//				// divide  04:00 (120 dakika) by (
//				String endTimeStr = endTimeTempArray[0].trim();
//				int duration = Integer.parseInt(endTimeTempArray[1].replaceAll("\\D+","")); 
//				//only get digits to take the duration from 120 dakika)
//				
//				DateTime start = new DateTime(year, month, day,startTimeHour,startTimeMinute );
//				DateTimeZone dtz = DateTimeZone.forID("Europe/Istanbul");
//				DateTime midnight = new DateTime(year,month,day,0,0,dtz);
//				long startTime = start.getMillis() / 1000L;
//				long endTime = startTime + (duration * 60) ; // add duration to calculate endTime 
//				program.setStartTime(startTime);
//				program.setEndTime(endTime);
//				program.setStartTimeStr(startTimeStr);
//				program.setEndTimeStr(endTimeStr);
//				program.setDuration(duration);
//				program.calculateTimeOfDay(midnight);
//			}
//			else if(identifier.contains("Tür"))
//			{
//				System.out.println("Tür bulundu");
//				if (inf.text().trim().split(":").length > 1 ) //sometimes no genre is included
//				{
//					String genre = inf.text().trim().split(":")[1];
//					String[] genreArray = genre.split("&");
//					ArrayList<String> genreList = new ArrayList<String>();
//					for (String g : genreArray)
//					{
//						if (g.contains("(")) // if we have a genre like this Dizi (Komedi)
//						{
//							String[] splittedGenre = g.split("\\(");
//							genreList.add(splittedGenre[0].trim()); // add Dizi
//							genreList.add(splittedGenre[1].replaceAll("\\)", "").trim());
//						}
//						else
//							genreList.add(g.trim());
//					}
//					System.out.println(genreList);
//					program.setGenre(genreList);
//				}
//			}
//			else if(identifier.contains("Yönetmen"))
//			{
//				System.out.println("Yönetmen bulundu");
//				String director = inf.text().trim().split(":")[1];
//				System.out.println(director);
//				ArrayList<String> directors = getListOfCommaSeparated(director);
//				program.setDirectors(directors);
//			}
//			else if(identifier.contains("Oyuncular"))
//			{
//				System.out.println("Oyuncular bulundu");
//				String actors = inf.text().trim().split(":")[1];
//				System.out.println(actors);
//				ArrayList<String> actorsList = getListOfCommaSeparated(actors);
//				program.setActors(actorsList);
//			}
//			else if(identifier.contains("Özet"))
//			{
//				System.out.println("Özet bulundu");
//				String summary = inf.text().trim().split(":")[1];
//				System.out.println(summary);
//				program.setSummary(summary);
//			}
//			
//		}
		return program;
	}
	
	public ArrayList<Program> getDailyProgramsOfChannel(String channelName) throws Exception
	{
		ArrayList<String> channelProgramLinks = extractChannelProgramLinks(channelName);
		ArrayList<Program> dailyPrograms = new ArrayList<Program>();
		for (String channelProgramLink : channelProgramLinks)
		{
			System.out.println("Program info will be extracted for link: " + channelProgramLink);
			Program p = extractProgramInfo(channelProgramLink,channelName);
			int sameProgramIndex = sameProgramExists(p,dailyPrograms);
			if (sameProgramIndex == -1)
				dailyPrograms.add(p);
			else
			{
				System.out.println("*************Same program found************");
				String longDescription = dailyPrograms.get(sameProgramIndex).getLongDescription();
				ArrayList<String> stemmedWords = dailyPrograms.get(sameProgramIndex).getStemmedWords();
				stemmedWords.addAll(p.getStemmedWords());
				dailyPrograms.get(sameProgramIndex).setLongDescription(longDescription + " " + p.getLongDescription());
				dailyPrograms.get(sameProgramIndex).setStemmedWords(stemmedWords);
				ArrayList<AnnotatedKeyword> annotatedEntities =  dailyPrograms.get(sameProgramIndex).getAnnotatedEntities();
				annotatedEntities.addAll(p.getAnnotatedEntities());
				dailyPrograms.get(sameProgramIndex).setAnnotatedEntities(annotatedEntities);
			}
		}
		return dailyPrograms ;
	}
	
	public ArrayList<Program> getProgramsOfADayForAChannel(String dayString,int channelId,String channelName) throws Exception
	{
		ArrayList<String> channelProgramLinks = extractChannelProgramLinksForASpecificDay(channelId,dayString,channelName);
		System.out.println(channelProgramLinks);
		ArrayList<Program> dailyPrograms = new ArrayList<Program>();
		ArrayList<Program> filteredDailyPrograms = new ArrayList<Program>() ;
		for (String channelProgramLink : channelProgramLinks)
		{
			System.out.println("Program info will be extracted for link: " + channelProgramLink);
			Program p = extractProgramInfo(channelProgramLink,channelName);
			System.out.println(p);
			if (p == null)
				continue ;
			else
				dailyPrograms.add(p);
//			int sameProgramIndex = sameProgramExists(p,dailyPrograms);
//			if (sameProgramIndex == -1)
//				dailyPrograms.add(p);
//			else
//			{
//				System.out.println("*************Same program found************");
//				String longDescription = dailyPrograms.get(sameProgramIndex).getLongDescription();
//				ArrayList<String> stemmedWords = dailyPrograms.get(sameProgramIndex).getStemmedWords();
//				stemmedWords.addAll(p.getStemmedWords());
//				dailyPrograms.get(sameProgramIndex).setLongDescription(longDescription + " " + p.getLongDescription());
//				dailyPrograms.get(sameProgramIndex).setStemmedWords(stemmedWords);
//				ArrayList<AnnotatedKeyword> annotatedEntities =  dailyPrograms.get(sameProgramIndex).getAnnotatedEntities();
//				//annotatedEntities.addAll(p.getAnnotatedEntities());
//				//dailyPrograms.get(sameProgramIndex).setAnnotatedEntities(annotatedEntities);
//			}
		}
		if (dailyPrograms.size() == 0)
			return dailyPrograms;
		Collections.sort(dailyPrograms);
		filteredDailyPrograms.add(dailyPrograms.get(0)) ; // add the first program first
		for (int i = 1 ; i< dailyPrograms.size(); i++)
		{
			Program lp = filteredDailyPrograms.get(filteredDailyPrograms.size() -1) ; // get the last program from list
			if (dailyPrograms.get(i).getStartTime() == lp.getEndTime())
				filteredDailyPrograms.add(dailyPrograms.get(i)) ;
		}
		//System.out.println(dailyPrograms);
		//System.out.println(filteredDailyPrograms);
//		return dailyPrograms ;
		return filteredDailyPrograms;
	}
	
	public ArrayList<String> extractChannelProgramLinksForASpecificDay(int channelId,String dayString,String channelName)
	{
		ArrayList<String> lst= new ArrayList<>();
		try {
			String url = "http://www.radikal.com.tr/tvrehberi.aspx?cmd=dailylist_ajax&channelid=" + 
					Integer.toString(channelId)+"&typeid=&day="+ dayString +"&fc=" ;
			System.out.println(url);
			Document doc = Jsoup.connect(url).get();
			//Document doc = Jsoup.connect("http://www.radikal.com.tr/tvrehberi/" + channelName + "/#!" + dayString).get();
			Elements title = doc.getElementsByClass("show-list");
			for (Element e: title)
			{
				Elements headlines= e.getElementsByTag("h4");
				for (Element headline : headlines)
				{
					Elements links = headline.getElementsByTag("a");
					for (Element link : links )
					{
						lst.add(link.attr("href"));
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lst);
		return lst;
	}
	
	// returns the index of the same program if exists, returns -1 other wise
	private int sameProgramExists (Program p,ArrayList<Program> programList)
	{
		for (int i = 0; i < programList.size(); i++)
		{
			if (programList.get(i).getName().equals(p.getName()) && 
					programList.get(i).getStartTime() == p.getStartTime()) //method to check the same program listed twice.
				return i ;
		}
		return -1 ; // no program in the list
	}
	private String transformTurkishMonths(String string) {
		// TODO Check for the month names
		if (string.contains("Ocak"))
			return "01";
		else if (string.contains("Şubat"))
			return "02";
		else if (string.contains("Mart"))
			return "03";
		else if (string.contains("Nisan"))
			return "04";
		else if (string.contains("Mayıs"))
			return "05";
		else if (string.contains("Haziran"))
			return "06";
		else if (string.contains("Temmuz"))
			return "07";
		else if (string.contains("Ağustos"))
			return "08";
		else if (string.contains("Eylül"))
			return "09";
		else if (string.contains("Ekim"))
			return "10";
		else if (string.contains("Kasım"))
			return "11";
		else if (string.contains("Aralık"))
			return "12";
		else 
			return null ;
	}

	private ArrayList<String> getListOfCommaSeparated(String commaSeparated)
	{
		ArrayList<String> lst = new ArrayList<String>(Arrays.asList(commaSeparated.split(",")));
		return lst;	
	}
	private String transformAlperLetters(String badString)
	{
		badString = badString.replaceAll("&uuml;", "ü");
		badString = badString.replaceAll("&Uuml;", "Ü");
		badString = badString.replaceAll("&ccedil;", "ç");
		badString = badString.replaceAll("&Ccedil;", "Ç");
		badString = badString.replaceAll("&ouml;", "ö");
		badString = badString.replaceAll("&Ouml;", "Ö");
		return badString;
	}
	
	public String incrementDay(String dayString)
	{
		String[] splittedDayString = dayString.split("\\.") ;
		int day = Integer.parseInt(splittedDayString[0]) ;
		int month = Integer.parseInt(splittedDayString[1]) ;
		int year = Integer.parseInt(splittedDayString[2]) ;
		DateTimeZone dtz = DateTimeZone.forID("Europe/Istanbul");
		DateTime midnightOfThatDay = new DateTime(year,month,day,0,0,dtz);
		DateTime midnightOfNextDay = midnightOfThatDay.plusDays(1);
		// trannsform numbers to string again
		String nextDayStr,nextDaysMonthStr, nextDaysYearStr;
		int nextDay = midnightOfNextDay.getDayOfMonth();
		if (nextDay < 10)
			nextDayStr = "0" + Integer.toString(nextDay);
		else
			nextDayStr = Integer.toString(nextDay);
		int nextDaysMonth = midnightOfNextDay.getMonthOfYear();
		if (nextDaysMonth < 10)
			nextDaysMonthStr = "0" + Integer.toString(nextDaysMonth);
		else
			nextDaysMonthStr = Integer.toString(nextDaysMonth);
		int nextDaysYear = midnightOfNextDay.getYear();
		nextDaysYearStr = Integer.toString(nextDaysYear);
		return nextDayStr + "." + nextDaysMonthStr + "." + nextDaysYearStr ;
	}
}