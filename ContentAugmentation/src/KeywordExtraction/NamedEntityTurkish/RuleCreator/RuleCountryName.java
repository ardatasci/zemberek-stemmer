package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleCountryName extends Rule{
	ArrayList<String> countries = new ArrayList<String>();
	ArrayList<String> countryWords = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	Boolean countryWordFound = false;
	String clearedContent = "";

	public RuleCountryName() {
		MinioReader fileReader = MinioReader.getFileReader("res/countries");
		while (fileReader.inputAvailable()) {
			String country = fileReader.readLine();
			countries.add(country);
			//System.out.println(country);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/countrywords");
		while (fileReader.inputAvailable()) {
			String countryWord = fileReader.readLine();
			countryWords.add(countryWord);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsCountryName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
		
		findEntitiesInDictionary(wordsList, countries, WordType.COUNTRY);
		
		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();
			clearedContent = wordsList.get(i).getClearedContent();

			if (countries.contains(clearedContent)) {
				wordsList.get(i).setType(WordType.COUNTRY);
			}

			for (String countryWord : countryWords) {
				if (clearedContent.contains(countryWord)) {
					//System.out.println("----" + countryWord);
					countryWordFound = true;
				}
			}

			if (countryWordFound) {
				//System.out.println("country word found");
				try {
					int k = 1;
					while ((wordsList.get(i - k).getType()).equals(WordType.POSSIBLE)
							|| (wordsList.get(i - k).getType())
									.equals(WordType.CONTINENT)
							|| (wordsList.get(i - k).getType())
									.equals(WordType.COUNTRY)) {
						//System.out.println("country word found mal herhalde bunu yazan: " + k);
						wordsList.get(i).setType(WordType.COUNTRY);
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							//System.out.println("bu da neyse: " + k);
							wordsList.get(i - k).setType(WordType.COUNTRY);
							break;
						}
						wordsList.get(i - k).setType(WordType.COUNTRY);
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				countryWordFound = false;
			}
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
	
//	public void findEntitiesInDictionary(ArrayList<Word> wordsList, ArrayList<String> entities, WordType entityType) {
//		String sentence = "";
//		for (int i = 0; i < wordsList.size(); i++){
//			wordsList.get(i).cleareContent(); 
//			sentence = sentence + " " + wordsList.get(i).getClearedContent();
//		}
//		
//		for(int i=0; i< entities.size(); i++){
//			if (sentence.contains(entities.get(i))) {
//				Word word = new Word();
//				word.setClearedContent(entities.get(i));
//				word.setContent(entities.get(i));
//				word.setType(entityType);
//				annotatedWordListCreator.addAnnotatedWord(word);
//			}	
//		}
//
//		
//	}

}
