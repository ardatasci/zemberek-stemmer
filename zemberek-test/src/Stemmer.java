import java.util.List;

import net.zemberek.araclar.turkce.YaziBirimi;
import net.zemberek.araclar.turkce.YaziBirimiTipi;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;


public class Stemmer {
	
	
	public void zemberek(String text)
	{
		List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(text);
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		
		for (int i = 0; i < analizDizisi.size(); i++) {
	        if (analizDizisi.get(i).tip == YaziBirimiTipi.KELIME) {
	            
	            Kelime[] k = zemberek.kelimeCozumle(analizDizisi.get(i).icerik);
	            for (int t=0 ; t < k.length; t++)
	            {
	            	System.out.println(k[t].kok().tip());
	            	System.out.println(k[t].kok().icerik());
	            }
	        }
	    }
	}

}
