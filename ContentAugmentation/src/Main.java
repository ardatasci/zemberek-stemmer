import KeywordExtraction.DBPediaSpotlight;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String text = "Egemen Bağış, Kuzey Güney Kanal D de yayınlanan dizidir, Meltem Cumbul ve Kıvanç Tatlıtuğ da orada oynamaktadır. Çalıkuşu dizisi de bu kanalda yayınlanmaya başlayacaktır. Abdullah Gül, Recep Tayyip Erdoğan Türkiye'de siyaset yapmaktadırlar. Mehmet Ali Erbil Fox TV de yayınlanan Çarkıfelek yarışma programında Demet Akalın ile birlikte sunuculuk yapmaktadır.";
		DBPediaSpotlight dbPediaSpotlight = new DBPediaSpotlight();
		dbPediaSpotlight.getProperNames(text);
	}

}
