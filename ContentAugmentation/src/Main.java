import java.util.Vector;

import KeywordAugmentation.Freebase.MQLAPI.FreebaseTVProgramRequest;
import KeywordAugmentation.Freebase.SearchAPI.FreebaseProgramSearchRequest;
import KeywordExtraction.DBPediaSptlight.AnnotatedKeyword;
import KeywordExtraction.DBPediaSptlight.DBPediaSpotlight;
import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.ModifiedListCreator;
import KeywordExtraction.NamedEntityTurkish.SentenceCreator;
import ResourceCollection.DigiturkEPG.EPGStatisticalExtraction;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String text = "Egemen Bağış, Kuzey Güney Kanal D de yayınlanan dizidir, Meltem Cumbul ve Kıvanç Tatlıtuğ da orada oynamaktadır. Çalıkuşu dizisi de bu kanalda yayınlanmaya başlayacaktır. Abdullah Gül, Recep Tayyip Erdoğan Türkiye de siyaset yapmaktadırlar. Mehmet Ali Erbil Fox TV de yayınlanan Çarkıfelek yarışma programında Demet Akalın ile birlikte sunuculuk yapmaktadır. Ankara ve Istanbul un Gaziantep ile olan benzerliği konu olmaktadır. Atakule nin ve Anıtkabir in eşsiz benzerliği ile baklava nın lezetti konuşuldu.";
		//String text = "YENİ BÖLÜM/YAZ TATİLİ BİTMİŞ CİHANGİR SAKİNLERİ SEMTLERİNE GERİ DÖNMÜŞTÜR.KOCABAŞLAR ANTAKYA DAN,DENİZLER ÇEŞME DEN DÖNERLER.";
		//String text = "Recep Tayyip Erdoğan, Mehmet Ali Erbil, Kıvanç Tatlıtuğ, Anadolu, Ankara";
		DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		//spottedKeywords = dbPediaSpotlight.getAnnotatedProperNames(text);
		//DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		//Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		//spottedKeywords = dbPediaSpotlight.getAnnotatedProperNames(text);
		//dbPediaSpotlight.getTypeOfKeyword("DBpedia:Agent,Schema:Organization,DBpedia:Organisation,DBpedia:Broadcaster,Schema:TelevisionStation,DBpedia:TelevisionStation");
		//for (AnnotatedKeyword annotatedKeyword : spottedKeywords) {
		//	annotatedKeyword.printAnnotatedKeyword();
		//}
		//System.out.println(spottedKeywords);
		//dbPediaSpotlight.getSpottedProperNames(text.toLowerCase());
		EPGStatisticalExtraction digiturkExtraction = new EPGStatisticalExtraction();
		/*Vector<String> channels = new Vector<>();
		channels.add("star_tv");
		channels.add("trt1_hd");
		channels.add("atv_hd");
		channels.add("show_tv");
		channels.add("kanal_d");
		for (String string : channels) {
			digiturkExtraction.collectProgramInfos(string, 1384196400);
		}*/
		//digiturkExtraction.collectProgramInfos("star_tv", 1384196400);
		//digiturkExtraction.processProgramInfos("trt1_hd_21");
		
		FreebaseTVProgramRequest programRequest = new FreebaseTVProgramRequest();
		programRequest.generateInstance();
		//System.out.println(programRequest.findActorsbyMid("/m/01xrzk0"));
		//System.out.println(result);
		
		FreebaseProgramSearchRequest programSearch = new FreebaseProgramSearchRequest();
		//programSearch.generateInstance();
		/*String mid = programSearch.getProgramMid("Muhteşem Yüzyıl", "Haber");
		if(mid != null){
			System.out.println(mid);
			System.out.println(programRequest.findActorsbyMid(mid));	
		}*/
		
		SentenceCreator sentenceCreater = new SentenceCreator();
		//sentenceCreater.createSentenceBySentenceText("input.txt", "sentenceBySentence.txt");
		sentenceCreater.extractSentencesFromText("short_input.txt", "sentenceBySentence.txt");
		int[] wantedEntities = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		String outputType = "TXT";
		ModifiedListCreator modifiedListCreator = new ModifiedListCreator();
		modifiedListCreator.createModifiedLitst(wantedEntities, outputType);
		System.out.println(AnnotatedWordListCreator.getInstance().getTotalSentenceSize());
		//modifiedListCreator.printWordListToFile();
 
	}
	

}
