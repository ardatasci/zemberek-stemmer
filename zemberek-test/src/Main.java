import java.util.List;

import net.zemberek.araclar.turkce.YaziBirimi;
import net.zemberek.araclar.turkce.YaziBirimiTipi;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;




public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String metin = "doğal dil işleme, doğal dillerin kural yapisini çözümleyerek anlaşılmasını sağlar";
		List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(metin);
		Zemberek z = new Zemberek(new TurkiyeTurkcesi());
		for (int i = 0; i < analizDizisi.size(); i++) {
	        if (analizDizisi.get(i).tip == YaziBirimiTipi.KELIME) {
	            Kelime[] k = z.kelimeCozumle(analizDizisi.get(i).icerik);
	            for (int t=0 ; t < k.length; t++)
	            {
	            	System.out.println(k[t].kok());
	            }
	        }
	    }
		
	}

}
