package KeywordExtraction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DBPediaSpotlightAnnotationRequest {

	// HTTP POST request
	public String spotlightSpotRequest(String text) throws Exception {
 
		String confidence = "0.1";
		String support = "12";
		//String spotter = "CoOccurrenceBasedSelector";
		//String spotter = "SpotXmlParser";
		String spotter = "Default";
		//String url = " http://spotlight.sztaki.hu:2235/rest/spot";
		//String url = "http://spotlight.dbpedia.org/rest/spot";
		//String url = "http://spotlight.dbpedia.org/rest/annotate";
		String url = "http://localhost:2222/rest/spot";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept","application/json");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		//String text = "Egemen Bağış, Kuzey Güney Kanal D de yayınlanan dizidir, Meltem Cumbul ve Kıvanç Tatlıtuğ da orada oynamaktadır. Çalıkuşu dizisi de bu kanalda yayınlanmaya başlayacaktır. Abdullah Gül, Recep Tayyip Erdoğan Türkiye'de siyaset yapmaktadırlar. Mehmet Ali Erbil Fox TV de yayınlanan Çarkıfelek yarışma programında Demet Akalın ile birlikte sunuculuk yapmaktadır.";
		//String text = "CANLI. 2014 DÜNYA KUPASI ELEME GRUBU MAÇINDA TÜRKİYE, ANDORRA KARŞISINDA. MİLLİLER, TEKNİK DİREKTÖR FATİH TERİM YÖNETEMİNDE İLK MAÇINA ÇIKIYOR.";

		text = URLEncoder.encode(text.toString(),"UTF-8");
		//String urlParameters = "text=" + text + "&confidence=" + confidence + "&support=" + support;
		String urlParameters = "text=" + text + "&spotter=" + spotter;
		//String urlParameters = "text=Ankara Türkiye Cumhuriyeti'nin başkentidir. Başbakanı Recep Tayyip Erdoğan'dır. Cumhurbaşkanı Abdullah Gül.&confidence=0.2&support=20";
		//String urlParameters_first = "text=President Recep Tayyip Erdoğan called Wednesday on Congress to extend a tax break for students included in last year's economic stimulus package, arguing that the policy provides more generous assistance.&confidence=0.2&support=20";
		//String urlParameters = URLEncoder.encode(urlParameters_first.toString(),"UTF-8"); 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		return response.toString();
 
	}
	public String spotlightAnnotateRequest(String text) throws Exception {
		 
		String confidence = "0";
		String support = "0";
		String spotter = "CoOccurrenceBasedSelector";
		//String url = "http://spotlight.sztaki.hu:2222/rest/spot";
		//String url = "http://spotlight.dbpedia.org/rest/spot";
		//String url = "http://spotlight.dbpedia.org/rest/annotate";
		String url = "http://localhost:2222/rest/annotate";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept","application/json");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		//String text = "Egemen Bağış, Kuzey Güney Kanal D de yayınlanan dizidir, Meltem Cumbul ve Kıvanç Tatlıtuğ da orada oynamaktadır. Çalıkuşu dizisi de bu kanalda yayınlanmaya başlayacaktır. Abdullah Gül, Recep Tayyip Erdoğan Türkiye'de siyaset yapmaktadırlar. Mehmet Ali Erbil Fox TV de yayınlanan Çarkıfelek yarışma programında Demet Akalın ile birlikte sunuculuk yapmaktadır.";
		//String text = "CANLI. 2014 DÜNYA KUPASI ELEME GRUBU MAÇINDA TÜRKİYE, ANDORRA KARŞISINDA. MİLLİLER, TEKNİK DİREKTÖR FATİH TERİM YÖNETEMİNDE İLK MAÇINA ÇIKIYOR.";

		text = URLEncoder.encode(text.toString(),"UTF-8");
		String urlParameters = "text=" + text + "&confidence=" + confidence + "&support=" + support;
		//String urlParameters = "text=" + text + "&spotter=" + spotter;
		//String urlParameters = "text=Ankara Türkiye Cumhuriyeti'nin başkentidir. Başbakanı Recep Tayyip Erdoğan'dır. Cumhurbaşkanı Abdullah Gül.&confidence=0.2&support=20";
		//String urlParameters_first = "text=President Recep Tayyip Erdoğan called Wednesday on Congress to extend a tax break for students included in last year's economic stimulus package, arguing that the policy provides more generous assistance.&confidence=0.2&support=20";
		//String urlParameters = URLEncoder.encode(urlParameters_first.toString(),"UTF-8"); 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//System.out.println(response.toString());
		return response.toString();
 
	}
}
