package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RulePossibleNames extends Rule{

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String[] capitalLetters = { "A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ",
			"H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S",
			"Ş", "T", "U", "Ü", "V", "Y", "Z" };

	String clearedContent = "";

	public RulePossibleNames() {

	}

	public ArrayList<Word> containsName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		boolean capitalLetterFound = false;
		boolean cik = false;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (int j = 0; j < clearedContent.length(); j++) {
				for (int k = 0; k < capitalLetters.length; k++) {
					if ((clearedContent.substring(j, j + 1))
							.equals(capitalLetters[k])) {
						capitalLetterFound = true;
						break;
					}

				}
				if (!capitalLetterFound) {
					break;
				}

			}

			if (capitalLetterFound) {
				wordsList.get(i).setType(WordType.POSSIBLE);
			}
			capitalLetterFound = false;
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}

	@Override
	void findEntitiesInDictionary(ArrayList<Word> wordsList,
			ArrayList<String> entities, WordType entityType, int sentenceNumber) {
		// TODO Auto-generated method stub
		
	}

	/*public ArrayList<Word> determinePossibleNames(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
		
		
		int length = wordsList.size();
		for (int i = 0; i < length; i++) {
			if (wordsList.get(i).getClearedContent().matches(pattern)) {
				wordsList.get(i).setType("possibleName");
			}
		}
		
		return wordsList;
	}*/
	
	/*private boolean doesStartWithCapitalLetter(String word){
		if()
	}*/
}
