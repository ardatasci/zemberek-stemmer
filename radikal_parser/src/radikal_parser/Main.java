package radikal_parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	

	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ChannelPageParser channelPageParser = new ChannelPageParser();
		ArrayList<String> programLinks = channelPageParser.extractChannelProgramLinks("kanald");
		System.out.println(programLinks);
		String url = "http://www.radikal.com.tr/tvrehberi/eve_donus/243628";
		channelPageParser.extractProgramInfo(url);

	}

}
