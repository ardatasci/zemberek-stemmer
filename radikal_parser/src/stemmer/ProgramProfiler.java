package stemmer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.zemberek.araclar.turkce.*;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;

public class ProgramProfiler {
	
	
	
	public List<String>  getNounsFromDesc(String desc) {
		String lowercase_desc = desc.toLowerCase();
		List<String> noun_list = new ArrayList<String>();
		
		List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(lowercase_desc);
	    Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
	    for (int i = 0; i < analizDizisi.size(); i++) {
	        if (analizDizisi.get(i).tip == YaziBirimiTipi.KELIME) {
	            Kelime[] k = zemberek.kelimeCozumle(analizDizisi.get(i).icerik);
	            if (k.length > 0 && k[0].kok().tip() == KelimeTipi.ISIM) {
	            	noun_list.add(k[0].kok().icerik());
	            }
	            
	    }}
	    return noun_list;
			
	}
	
	
	public List<String>  dropStopWords(String desc) {
		String lowercase_desc = desc.toLowerCase();
				
		List<String> stop_words = new ArrayList<String>();
		
		// get stop words
		try (BufferedReader br = new BufferedReader(new FileReader("src/stop_words.txt")))
		{
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				stop_words.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> term_list = new ArrayList<String>(Arrays.asList(lowercase_desc.split("\\s")));
		
		for(int i = 0; i < term_list.size(); i++) 
			if(stop_words.contains(term_list.get(i))) 
				term_list.remove(i);
				
		
	    return term_list;
			
	}
	
	
	
	
	
	
	

}
