package radikal_parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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
	
	public Program extractProgramInfo(String programLink) throws MalformedURLException, IOException
	{
		Program program = null;
		Document doc = Jsoup.parse(new URL(programLink).openStream(), "ISO-8859-9", programLink);
		Element info_area = doc.getElementsByClass("program-ozet").first();
		System.out.println(transformAlperLetters(info_area.toString()));
		return program;
	}
	
	private String transformAlperLetters(String badString)
	{
		System.out.println("beggining");
		System.out.println(badString);
		badString = badString.replaceAll("&uuml;", "ü");
		badString = badString.replaceAll("&Uuml;", "Ü");
		badString = badString.replaceAll("&ccedil;", "ç");
		badString = badString.replaceAll("&Ccedil;", "Ç");
		badString = badString.replaceAll("&ouml;", "ö");
		badString = badString.replaceAll("&Ouml;", "Ö");
		System.out.println("end");
		System.out.println(badString); 
		return badString;
	}
}