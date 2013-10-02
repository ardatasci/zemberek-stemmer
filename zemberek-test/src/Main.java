import java.net.*;
import java.io.*;

import java.io.IOException;



import org.json.*;


public class Main {
	


	public static void main(String[] args) throws JSONException {
		NamedEntitiyRecognition ner = new NamedEntitiyRecognition();
		SemanticEnhancement se = new SemanticEnhancement();
		ProgramProfiler pp = new ProgramProfiler();
		// TODO Auto-generated method stub
		/*String metin = "BELGESEL PROGRAM, HÂLEN GİZEMİNİ KORUYAN İMPARATOR PENGUENLERİN YAŞAMLARINI VE TÜRLERİNİN DEVAMI İÇİN VERDİKLERİ MÜCADELEYİ EKRANA GETİRİYOR. YERYÜZÜNÜN EN ISSIZ VE ACIMASIZ";
		System.out.println(metin.toLowerCase());
		List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(metin);
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());*/
		/*
		for (int i = 0; i < analizDizisi.size(); i++) {
	        if (analizDizisi.get(i).tip == YaziBirimiTipi.KELIME) {
	            
	            Kelime[] k = zemberek.kelimeCozumle(analizDizisi.get(i).icerik);
	            for (int t=0 ; t < k.length; t++)
	            {
	            	System.out.println(k[t].kok().tip());
	            	System.out.println(k[t].kok().icerik());
	            }
	        }
	    }*/
		
		try {
			JSONObject program = getProgramInfo("atv", "1376347236");
			String desc = ((JSONObject)program.get("broadcast")).getString("sDesc");
			
			se.IMDBEnhancement(((JSONObject)program.get("broadcast")).getString("pName"));
			desc += " ";
			desc += ((JSONObject)program.get("broadcast")).getString("pName");
			desc += " ";
			desc += ((JSONObject)program.get("broadcast")).getString("pGenreStr");
			System.out.println(desc);
			
			pp.printProfile("atv", "1234", ner.dbPedia(desc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	
	
	// to get program info from our epg source
	public static JSONObject getProgramInfo(String channel_name, String time) throws IOException, JSONException {
		URL epgSource;
		HttpURLConnection epgSource_connection;
		epgSource = new URL("http://10.155.10.14/program_detail.json");
		epgSource_connection = (HttpURLConnection) epgSource.openConnection();
		epgSource_connection.setDoOutput(true); 
		epgSource_connection.setRequestMethod("GET");
		epgSource_connection.setRequestProperty("Accept", "application/json");
		String urlParameters = "cIdentifier=";
		urlParameters += channel_name;
		urlParameters += "&time=";
		urlParameters += time;
		
		epgSource_connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(epgSource_connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();	
		epgSource_connection.connect();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                		epgSource_connection.getInputStream()));
        JSONObject jsonObj = new JSONObject( in.readLine()); 
        in.close();
        return jsonObj;	
	}
	
	
	

}


