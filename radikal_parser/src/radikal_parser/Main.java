package radikal_parser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	

	public static void main(String[] args) throws Exception {
		String[] channelNameArray = {"kanald","cnn_turk","startv","atv","trt1","ntv","cnbc_e","fox","tv8",
				"skyturk360", "bloomberg_ht","a_haber","e2","kanal7","24","tv2","haberturk","flashtv",
				"beyaztv","cine5","kanalturk","animal_planet","samanyolutv","discovery_channel",
				"national_geographic_adventure","national_geographic_wild_hd","bbc_world","sinematv",
				"eurosport","eurosport2","discovery_world_channel","trt3_trtspor","ntvspor",
				"investigation_discovery","national_geographic_channel", "ligtv", "yumurcaktv",
				"discovery_science_channel"};
		ChannelPageParser channelPageParser = new ChannelPageParser();
		//Program program = new Program();
		//DateTimeZone dtz = DateTimeZone.forID("Europe/Istanbul");
		//DateTime midnight = new DateTime(2014,1,29,0,0,dtz);
		//program.calculateTimeOfDay(midnight);
		//ArrayList<Program> programs = channelPageParser.getDailyProgramsOfChannel("kanald");
		//System.out.println(programs);
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
		long epoch = System.currentTimeMillis() / 1000 ;
		long surplus = epoch % (3600 * 24);
		long fileTimestamp = epoch -surplus ; // now i get the start of the day
		for (String channelName : channelNameArray)
		{
			ArrayList<Program> programs = channelPageParser.getDailyProgramsOfChannel(channelName);
			Collections.sort(programs);
			String jsonString = gson.toJson(programs);
			System.out.println(programs);
			FileWriter writer = new FileWriter(fileTimestamp + "_" + channelName + ".json");
			writer.write(jsonString);
			writer.close();
			System.out.println("********************");
			
		}
	}

}
