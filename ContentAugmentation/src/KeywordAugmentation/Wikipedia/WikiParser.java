package KeywordAugmentation.Wikipedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Disambiguation.DocumentParser;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WikiParser {

	MediaWikiBot mediaWikiBot;
	WikiConfig wikiConfig;
	String wikiPage;
	Article article;
	String firstTitle;
	private String queryDocument;
	
	int infoBoxEndPosition;

	String INFOBOX_CONST_STR = "bilgi kutusu";
	String REDIRECT_EN1_CONST_STR = "#redirect";
	String REDIRECT_EN2_CONST_STR = "#REDIRECT";
	String REDIRECT_TR1_CONST_STR = "#YÖNLENDİR";
	String REDIRECT_TR2_CONST_STR = "#YÖNLENDİRME";
	String REDIRECT_TR3_CONST_STR = "#yönlendir";
	String REDIRECT_TR4_CONST_STR = "#yönlendirme";
	String DISAMBIGUATION1_CONST_STR = "{{anlam ayrımı}}";
	String DISAMBIGUATION11_CONST_STR = "{{Anlam ayrımı}}";
	String DISAMBIGUATION2_CONST_STR = "(anlam ayrımı)";
	
	static final int NOT_DISAMBIGUATION_PAGE = 0;
	static final int DISAMBIGUATION_PAGE = 1;
	static final int POSSIBLE_DISAMBIGUATION_PAGE = 2;

	public WikiParser() {
		wikiConfig = new WikiConfig();
		infoBoxEndPosition = 0;
	}

	public void initWikiParser() {
		mediaWikiBot = new MediaWikiBot(wikiConfig.getWikiURL());
		mediaWikiBot.login(wikiConfig.getUsername(), wikiConfig.getPassword());
	}

	public String downloadWikiPage(String title) {
		firstTitle = title;
		article = mediaWikiBot.getArticle(title);
		wikiPage = article.getText();
		removeHTMLTags();
		removeLinkTags();
		int redirectIndex = getRedirectPageTitleIndex();
		if(redirectIndex > -1){
			String newTitle = wikiPage.substring(redirectIndex);
			article = mediaWikiBot.getArticle(newTitle);
			wikiPage = article.getText();
			removeHTMLTags();
			removeLinkTags();
		}
		
//		if(isDisambiguation() == 1){
//			ArrayList<String> disambiguationTitles = getDisambiguationPages(title);
//			ArrayList<String> disambiguationWikiPageDefinitions = new ArrayList<>();
//			HashMap<String, String> titleWikiPageMap = new HashMap<>();
//			disambiguationWikiPageDefinitions.add(queryDocument);
//			for (String disambiguationTitle : disambiguationTitles) {
//				Article disambiguationArticle = mediaWikiBot.getArticle(disambiguationTitle);
//				String disambiguationWikiPage = disambiguationArticle.getText();
//				titleWikiPageMap.put(disambiguationTitle, disambiguationWikiPage);
//				String willBeAddedDefinition = getShortDefinition(disambiguationWikiPage);
//				if(willBeAddedDefinition != null){
//					disambiguationWikiPageDefinitions.add(willBeAddedDefinition);
//				}
//				else{
//					disambiguationWikiPageDefinitions.add("");
//				}
//					
//			}
//			DocumentParser documentParser = new DocumentParser();
//			documentParser.parseFiles(disambiguationWikiPageDefinitions);
//			documentParser.tfIdfCalculator();
//			int index = documentParser.getMostSimilarIndex();
//			System.out.println(disambiguationWikiPageDefinitions.get(index));
//			
//		}
		
		return wikiPage;
	}

	public String getRawInfoBox() {
		infoBoxEndPosition = 0;
		String rawInfoBox = "";
		int startPos = wikiPage.indexOf(INFOBOX_CONST_STR);
		if (startPos < 0)
			return rawInfoBox;
		int bracketCount = 2;
		int endPos = startPos + INFOBOX_CONST_STR.length();
		for (; endPos < wikiPage.length(); endPos++) {
			switch (wikiPage.charAt(endPos)) {
			case '}':
				bracketCount--;
				break;
			case '{':
				bracketCount++;
				break;
			default:
			}
			if (bracketCount == 0)
				break;
		}
		rawInfoBox = wikiPage.substring(startPos, endPos-1);
		infoBoxEndPosition = endPos;
		String[] infoboxElements = rawInfoBox.split("\\| ");
		return rawInfoBox;

	}

	private void removeHTMLTags() {
		wikiPage = wikiPage.replaceAll("&gt;", ">");
		wikiPage = wikiPage.replaceAll("&lt;", "<");
		wikiPage = wikiPage.replaceAll("<ref.*?>.*?</ref>", " ");
		wikiPage = wikiPage.replaceAll("</?.*?>", " ");
	}

	private void removeLinkTags() {
		wikiPage = wikiPage.replaceAll("\\[\\[", "");
		wikiPage = wikiPage.replaceAll("\\]\\]", "");
	}
	
	public String getShortDefinition(){
		String shortDefinition = "";
		//String shortDefRegex = "'''" + article.getTitle()  + "'''";
		String shortDefRegex = "'''";
		//System.out.println(wikiPage);
		int startPos = wikiPage.indexOf(shortDefRegex, infoBoxEndPosition);
		int endPos = 0;
		if (startPos < 0){
			shortDefRegex = "'''" + firstTitle  + "'''";
			startPos = wikiPage.indexOf(shortDefRegex, infoBoxEndPosition);
			if (startPos < 0)
				return shortDefinition;
		}
			
		//startPos = startPos + shortDefRegex.length();
		endPos = wikiPage.indexOf("==", startPos);
		if(endPos < 0){ // anlam ayırımı varmı yokmu kontrol et
			return shortDefinition;
		}
		shortDefinition = wikiPage.substring(startPos, endPos);
		shortDefinition = shortDefinition.replaceAll("'''", "");
		shortDefinition = shortDefinition.replaceAll("''", "");
		return shortDefinition;
	}
	
	private String getShortDefinition(String disambiguationWikiPage){
		if(disambiguationWikiPage.equals("")){
			return null;
		}
		removeHTMLTags();
		removeLinkTags();
		int redirectIndex = getRedirectPageTitleIndex();
		if(redirectIndex > -1){
			String newTitle = disambiguationWikiPage.substring(redirectIndex);
			Article disambiguationArticle = mediaWikiBot.getArticle(newTitle);
			disambiguationWikiPage = disambiguationArticle.getText();
			removeHTMLTags();
			removeLinkTags();
		}
		String shortDefinition = "";
		String shortDefRegex = "'''" + firstTitle + "'''";
		//System.out.println(wikiPage);
		int startPos = disambiguationWikiPage.indexOf(shortDefRegex, infoBoxEndPosition);
		int endPos = 0;
		if (startPos < 0)
			return shortDefinition;
		//startPos = startPos + shortDefRegex.length();
		endPos = disambiguationWikiPage.indexOf("==", startPos);
		if(endPos < 0){ // anlam ayırımı varmı yokmu kontrol et
			return shortDefinition;
		}
		shortDefinition = disambiguationWikiPage.substring(startPos, endPos);
		shortDefinition = shortDefinition.replaceAll("'''", "");
		shortDefinition = shortDefinition.replaceAll("''", "");
		return shortDefinition;
	}
	
	private int getRedirectPageTitleIndex(){
		int returnIndex = 0;
		if(wikiPage.indexOf(REDIRECT_EN1_CONST_STR) > -1){
			returnIndex = wikiPage.indexOf(REDIRECT_EN1_CONST_STR) + REDIRECT_EN1_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}
		else if(wikiPage.indexOf(REDIRECT_EN2_CONST_STR) > -1){
			returnIndex = wikiPage.indexOf(REDIRECT_EN2_CONST_STR) + REDIRECT_EN2_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}
		else if(wikiPage.indexOf(REDIRECT_TR4_CONST_STR) > -1){ //bu şartın önce olması önemli
			returnIndex = wikiPage.indexOf(REDIRECT_TR4_CONST_STR) + REDIRECT_TR4_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}
		else if(wikiPage.indexOf(REDIRECT_TR3_CONST_STR) > -1){
			returnIndex = wikiPage.indexOf(REDIRECT_TR3_CONST_STR) + REDIRECT_TR3_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}
		else if(wikiPage.indexOf(REDIRECT_TR2_CONST_STR) > -1){ //bu şartın önce olması önemli
			returnIndex = wikiPage.indexOf(REDIRECT_TR2_CONST_STR) + REDIRECT_TR2_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}
		else if(wikiPage.indexOf(REDIRECT_TR1_CONST_STR) > -1){
			returnIndex = wikiPage.indexOf(REDIRECT_TR1_CONST_STR) + REDIRECT_TR1_CONST_STR.length();
			if(wikiPage.charAt(returnIndex) == ' '){
				return returnIndex + 1;
			}
			else
				return returnIndex;
		}

		else
			return -1;
		
	}
	
	public int isDisambiguation(){
		if(wikiPage.indexOf(DISAMBIGUATION1_CONST_STR) > -1 || wikiPage.indexOf(DISAMBIGUATION11_CONST_STR) > -1){
			return DISAMBIGUATION_PAGE;
		}
		else if (wikiPage.indexOf(DISAMBIGUATION2_CONST_STR) > -1){
			
			return POSSIBLE_DISAMBIGUATION_PAGE;
		}
		else {
			return NOT_DISAMBIGUATION_PAGE;
		}
	}
	
	private ArrayList<String> getDisambiguationPages(String title){
		ArrayList<String> disambiguationTitles = new ArrayList<>();
		
		int fromIndex = 0;
		
		String disambiguationTitleRegex1 = title + " ("; 
		String disambiguationTitleRegex2 = title + ","; 
		
		while(true){
			if(wikiPage.indexOf(disambiguationTitleRegex1, fromIndex) > -1){
				fromIndex = wikiPage.indexOf(disambiguationTitleRegex1, fromIndex);
				int tempEndIndex = wikiPage.indexOf(")", fromIndex) + 1;
				String disambiguationTitle = wikiPage.substring(fromIndex, tempEndIndex);
				disambiguationTitles.add(disambiguationTitle);
				fromIndex = tempEndIndex;
			}
			else{
				break;
			}
				
		}
		
		fromIndex = 0;
		
		while(true){
			if(wikiPage.indexOf(disambiguationTitleRegex2, fromIndex) > -1){
				fromIndex = wikiPage.indexOf(disambiguationTitleRegex2, fromIndex);
				int tempEndIndex = wikiPage.indexOf("|", fromIndex);
				// virgulde olabilir diye boyle bir kontrol yapıyoruz
				if((tempEndIndex - fromIndex) > 20 || tempEndIndex == -1 ){
					tempEndIndex = wikiPage.indexOf(",", fromIndex + disambiguationTitleRegex2.length());
				}
				if((tempEndIndex - fromIndex) < 20 || tempEndIndex != -1 ){
					String disambiguationTitle = wikiPage.substring(fromIndex, tempEndIndex);
					disambiguationTitles.add(disambiguationTitle);
					fromIndex = tempEndIndex;
				}

			}
			else{
				break;
			}
				
		}
		
		
		return disambiguationTitles;
	}

	public String getQueryDocument() {
		return queryDocument;
	}

	public void setQueryDocument(String queryDocument) {
		this.queryDocument = queryDocument;
	}
}
