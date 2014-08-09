import java.util.ArrayList;
import java.util.Vector;

import tr.edu.hacettepe.cs.minio.MinioReader;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import Database.DatabaseManager;
import Disambiguation.DocumentParser;
import Evaluation.NEREvaluation;
import Evaluation.RadikalDataEvaluation;
import KeywordAugmentation.Freebase.MQLAPI.FreebaseTVProgramRequest;
import KeywordAugmentation.Freebase.SearchAPI.FreebaseProgramSearchRequest;
import KeywordAugmentation.Wikipedia.WikiParser;
import KeywordExtraction.DBPediaSptlight.AnnotatedKeyword;
import KeywordExtraction.DBPediaSptlight.DBPediaSpotlight;
import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.ModifiedListCreator;
import KeywordExtraction.NamedEntityTurkish.SentenceCreator;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.util.UtilFunctions;
import Repesentation.ContentAugmentationResource;
import Repesentation.ContentAugmentationWİndow;
import ResourceCollection.DigiturkEPG.EPGStatisticalExtraction;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String text = "Egemen Bağış, Kuzey Güney Kanal D de yayınlanan dizidir, Meltem Cumbul ve Kıvanç Tatlıtuğ da orada oynamaktadır. Çalıkuşu dizisi de bu kanalda yayınlanmaya başlayacaktır. Abdullah Gül, Recep Tayyip Erdoğan Türkiye de siyaset yapmaktadırlar. Mehmet Ali Erbil Fox TV de yayınlanan Çarkıfelek yarışma programında Demet Akalın ile birlikte sunuculuk yapmaktadır. Ankara ve Istanbul un Gaziantep ile olan benzerliği konu olmaktadır. Atakule nin ve Anıtkabir in eşsiz benzerliği ile baklava nın lezetti konuşuldu.";
		// String text =
		// "YENİ BÖLÜM/YAZ TATİLİ BİTMİŞ CİHANGİR SAKİNLERİ SEMTLERİNE GERİ DÖNMÜŞTÜR.KOCABAŞLAR ANTAKYA DAN,DENİZLER ÇEŞME DEN DÖNERLER.";
		// String text =
		// "Recep Tayyip Erdoğan, Mehmet Ali Erbil, Kıvanç Tatlıtuğ, Anadolu, Ankara";
		DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		// spottedKeywords = dbPediaSpotlight.getAnnotatedProperNames(text);
		// DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		// Vector<AnnotatedKeyword> spottedKeywords = new Vector<>();
		// spottedKeywords = dbPediaSpotlight.getAnnotatedProperNames(text);
		// dbPediaSpotlight.getTypeOfKeyword("DBpedia:Agent,Schema:Organization,DBpedia:Organisation,DBpedia:Broadcaster,Schema:TelevisionStation,DBpedia:TelevisionStation");
		// for (AnnotatedKeyword annotatedKeyword : spottedKeywords) {
		// annotatedKeyword.printAnnotatedKeyword();
		// }
		// System.out.println(spottedKeywords);
		// dbPediaSpotlight.getSpottedProperNames(text.toLowerCase());
		EPGStatisticalExtraction digiturkExtraction = new EPGStatisticalExtraction();
		/*
		 * Vector<String> channels = new Vector<>(); channels.add("star_tv");
		 * channels.add("trt1_hd"); channels.add("atv_hd");
		 * channels.add("show_tv"); channels.add("kanal_d"); for (String string
		 * : channels) { digiturkExtraction.collectProgramInfos(string,
		 * 1384196400); }
		 */
		// digiturkExtraction.collectProgramInfos("star_tv", 1384196400);
		// digiturkExtraction.processProgramInfos("trt1_hd_21");

		FreebaseTVProgramRequest programRequest = new FreebaseTVProgramRequest();
		programRequest.generateInstance();
		// System.out.println(programRequest.findActorsbyMid("/m/01xrzk0"));
		// System.out.println(result);

		FreebaseProgramSearchRequest programSearch = new FreebaseProgramSearchRequest();
		// programSearch.generateInstance();
		/*
		 * String mid = programSearch.getProgramMid("Muhteşem Yüzyıl", "Haber");
		 * if(mid != null){ System.out.println(mid);
		 * System.out.println(programRequest.findActorsbyMid(mid)); }
		 */

		// UtilFunctions utilFunctions = new UtilFunctions();
		// utilFunctions.removeDuplicatesInResource("res/cities");

		// ner denemeleri

		// SentenceCreator sentenceCreater = new SentenceCreator();
		// //sentenceCreater.createSentenceBySentenceText("input.txt","sentenceBySentence.txt");
		// sentenceCreater.extractSentencesFromText("shortest_input.txt",
		// "sentenceBySentence.txt");
		// int[] wantedEntities = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		// String outputType = "TXT";
		// ModifiedListCreator modifiedListCreator = new ModifiedListCreator();
		// modifiedListCreator.createModifiedLitst(wantedEntities, outputType);
		// System.out.println(AnnotatedWordListCreator.getInstance().getTotalSentenceSize());
		// modifiedListCreator.printWordListToFile();

		// //ner denemeleri
		// ContentAugmentationWİndow myWindow = new ContentAugmentationWİndow();
		// myWindow.main();

		// evaluation

		 NEREvaluation nerEvaluation = new NEREvaluation();
		// nerEvaluation.evaluateAlgorithm();

		ContentAugmentationResource resource = new ContentAugmentationResource();

		ArrayList<Word> annotatedWords = resource.getAnnotatedWords(nerEvaluation.getArticleText(0));
		for (Word word : annotatedWords) {
			word.writeToConsole();
		}

		// RadikalDataEvaluation radikalDataEvaluation = new
		// RadikalDataEvaluation();
		// radikalDataEvaluation.evaluateRadikalData();

		// database operations

		// DatabaseManager databaseManager = new DatabaseManager();
		// ArrayList<String> programDescriptionsOfChannel =
		// databaseManager.getProgramDescriptionsOfChannel(1);
		// System.out.println(programDescriptionsOfChannel.size());

		// ArrayList<String> disambiguationWikiPageDefinitions = new
		// ArrayList<>();
		//
		// String s1 =
		// "Manisa'nın Soma ilçesindeki maden faciasında hayatını kaybeden 282 işçiden 28 yaşındaki Mithat Özdirik'in cenazesi, memleketi Fatih'de toprağa verildi.";
		// disambiguationWikiPageDefinitions.add(s1);
		//
		// String s2 =
		// "Fatih, Tarihi yarımada (Suriçi) denen İstanbul şehrinin kurulduğu ve geliştiği bölgenin tamamını kaplayan ve İstanbul'un merkezi sayılan ilçe. Güneybatıdan Zeytinburnu, kuzeybatıdan Eyüp ilçeleri ile kuzeyden Haliç, doğudan İstanbul Boğazı ve güneyden Marmara Denizi ile çevrilidir. Fatih İlçesi, 1928'den 2008'e kadar Eminönü'yle beraber Tarihi Yarımadadaki iki ilçeden biri olmuştur. 2008'de Eminönü İlçesi'nin varlığının ortadan kaldırıp Fatih İlçesi'ne katılmasından beri tüm Tarihi yarımada üzerindeki tek ilçe haline gelmiştir. Kırsal yerleşimi olmayan ve 15.62 km²'lik (1562 hektar) bir alanı kaplayan Fatih İlçesi 57 mahalleden oluşmaktadır.";
		// disambiguationWikiPageDefinitions.add(s2);
		//
		// String s3 =
		// "Fatih, Osmanlı Padişahı II. Mehmed'in hayatını konu alan, 2013 yapımı Türk dizisi. Reyting azlığı sebebiyle Kanal D yönetimince saat 22:00'a alınması diziyi kanalın finans desteğinden mahrum bırakmıştır. Yapımcı diziyi bitirme kararı almış, 4 Kasım 2013 tarihinde yayınlanan 5. bölümüyle dizi final yapmıştır.";
		// disambiguationWikiPageDefinitions.add(s3);
		//
		// String s4 =
		// "Fatih, Ankara'nın Sincan ilçesine bağlı, Ankara - Ayaş Yolu üzerinde bulunan bir semttir. Avrupa'nın en büyük parkı Harikalar Diyarı bu semtte bulunmaktadır. Semtte 6 tane lise, 2 tane metro istasyonu ve bir adet büyük hastane bulunmaktadır. Mahallede ağırlıklı olarak memur ve işçi kesimi ikamet eder.";
		// disambiguationWikiPageDefinitions.add(s4);
		//
		// DocumentParser documentParser = new DocumentParser();
		// documentParser.parseFiles(disambiguationWikiPageDefinitions);
		// documentParser.tfIdfCalculator();
		// int index = documentParser.getMostSimilarIndex();
		// System.out.println(disambiguationWikiPageDefinitions.get(index));

		// WikiParser wikiParser = new WikiParser();
		// wikiParser.initWikiParser();
		// String page = wikiParser.downloadWikiPage("Fatih, Pendik");
		// wikiParser.getRawInfoBox();
		// System.out.println(wikiParser.getRawInfoBox());
		// System.out.println(page);

		// System.out.println(page);
		// wikiParser.getInfoBox();

	}

}
