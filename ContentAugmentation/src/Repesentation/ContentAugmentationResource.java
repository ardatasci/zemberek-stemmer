package Repesentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import KeywordAugmentation.Wikipedia.WikiParser;
import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.ModifiedListCreator;
import KeywordExtraction.NamedEntityTurkish.SentenceCreator;
import KeywordExtraction.NamedEntityTurkish.Word;

public class ContentAugmentationResource {

	HashMap<String, String> entityInfoMap;
	WikiParser wikiParser;
	
	public ContentAugmentationResource(){
		entityInfoMap = new HashMap<>();
		wikiParser = new WikiParser();
		wikiParser.initWikiParser();
	}
	
	private ArrayList<Word> getAnnotatedWords(String text) throws IOException{
		AnnotatedWordListCreator.getInstance().getAnnotatedWordList().clear();
		 SentenceCreator sentenceCreater = new SentenceCreator();
		 //sentenceCreater.createSentenceBySentenceText("input.txt","sentenceBySentence.txt");
		 sentenceCreater.extractSentencesFromWindowTextField(text,"sentenceBySentence.txt");
		 int[] wantedEntities = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		 String outputType = "TXT";
		 ModifiedListCreator modifiedListCreator = new ModifiedListCreator();
		 modifiedListCreator.createModifiedLitst(wantedEntities, outputType);
		 ArrayList<Word> wordList = AnnotatedWordListCreator.getInstance().getAnnotatedWordList();
		 return wordList;
	}
	
	public void createEntityInfoMap(String text) throws IOException{
		entityInfoMap.clear();
		ArrayList<Word> annotatedWords = getAnnotatedWords(text);
		String info;
		for (Word word : annotatedWords) {
			if(!entityInfoMap.containsKey(word.getClearedContent())){
				wikiParser.downloadWikiPage(word.getClearedContent());
				wikiParser.getRawInfoBox();
				info = wikiParser.getShortDefinition();
				entityInfoMap.put(word.getClearedContent(), info);
			}
		}
	}
	
	public ArrayList<String> getEntityList(){
		ArrayList<String> entityList = new ArrayList<>();
		Set<String> keySet = entityInfoMap.keySet();
		for (String string : keySet) {
			entityList.add(string);
		}
		return entityList;
	}
	
	public String getEntityInfo(String entity){
		return entityInfoMap.get(entity);
	}
	
	public void setQueryTextToWikiParser(String queryText){
		wikiParser.setQueryDocument(queryText);
	}
	
	
	
}
