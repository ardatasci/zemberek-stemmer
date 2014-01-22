package radikal_parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static ArrayList<String> channelProgramLinks(String channelName){
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
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			Document doc = Jsoup.connect("http://www.radikal.com.tr/tvrehberi/bir_zamanlar_haberler/243007").get();
			Element info_area = doc.getElementsByClass("program-ozet").first();
			System.out.println(info_area.children());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
