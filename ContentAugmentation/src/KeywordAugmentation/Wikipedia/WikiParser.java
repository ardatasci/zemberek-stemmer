package KeywordAugmentation.Wikipedia;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WikiParser {

	MediaWikiBot mediaWikiBot;
	WikiConfig wikiConfig;
	String wikiPage;
	Article article;
	// String infoRegex = "\\{(.*?)\\}";
	// String infoRegex = "\\{(.+)\\{(.+)\\}\\}";
	String infoRegex = "{{(.+){{(.+)}}}}";
	String INFOBOX_CONST_STR = "bilgi kutusu";

	public WikiParser() {
		wikiConfig = new WikiConfig();
	}

	public void initWikiParser() {
		mediaWikiBot = new MediaWikiBot(wikiConfig.getWikiURL());
		mediaWikiBot.login(wikiConfig.getUsername(), wikiConfig.getPassword());
	}

	private String getWikiPage(String title) {
		article = mediaWikiBot.getArticle(title);
		wikiPage = article.getText();
		removeHTMLTags();
		removeLinkTags();
		return wikiPage;
	}

	public String getRawInfoBox() {
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
	
	public String getShortDefinition(String title){
		getWikiPage(title);
		String shortDefinition = "";
		String shortDefRegex = "'''" + article.getTitle()  + "'''";
		//System.out.println(wikiPage);
		int startPos = wikiPage.indexOf(shortDefRegex);
		int endPos = 0;
		if (startPos < 0)
			return shortDefinition;
		startPos = startPos + shortDefRegex.length();
		endPos = wikiPage.indexOf("==", startPos);
		if(endPos < 0){ // anlam ayırımı varmı yokmu kontrol et
			return shortDefinition;
		}
		shortDefinition = wikiPage.substring(startPos, endPos);
		shortDefinition.replaceAll("'''", "");
		return shortDefinition;
	}
}
