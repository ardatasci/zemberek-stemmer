package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleCityName extends Rule {
	ArrayList<String> cities = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleCityName() {
		MinioReader fileReader = MinioReader.getFileReader("res/cities");
		while (fileReader.inputAvailable()) {
			String city = fileReader.readLine();
			cities.add(city);
		}
		
		fileReader.close();
	}

	public ArrayList<Word> containsCityName(ArrayList<Word> wordsList, int sentenceNumber) {
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		
		findEntitiesInDictionary(wordsList, cities, WordType.CITY, sentenceNumber);

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();
			boolean exist = cities.contains(clearedContent);
			if (exist && (( wordsList.get(i).getType() != null && !wordsList.get(i).getType().equals(WordType.LOCATION)
					&& !wordsList.get(i).getType().equals(WordType.ORGANIZATION)) || wordsList.get(i).getType() == null)) {
				wordsList.get(i).setType(WordType.CITY);
				wordsList.get(i).setSubType(WordType.CITY);
			}
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}

	@Override
	void findEntitiesInDictionary(ArrayList<Word> wordsList,
			ArrayList<String> entities, WordType entityType, int sentenceNumber) {
		String sentence = "";
		for (int i = 0; i < wordsList.size(); i++){
			wordsList.get(i).cleareContent(); 
			sentence = sentence + " " + wordsList.get(i).getClearedContent();
		}
		
		for(int i=0; i< entities.size(); i++){
			if (sentence.contains(entities.get(i))) {
				Word word = new Word();
				word.setClearedContent(entities.get(i));
				word.setContent(entities.get(i));
				word.setType(entityType);
				word.setPosition(sentenceNumber*(i+1));
				annotatedWordListCreator.addAnnotatedWord(word);
				//word listesinde tek tek bulunan bu ülke isimlerinin tipini set et
			}	
		}
		
	}
}
