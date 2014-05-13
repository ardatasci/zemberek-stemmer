package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleAbbreviation extends Rule{

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	
	String[] capitalLetters = {"A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z"};
 	
	String clearedContent = "";
	
	public RuleAbbreviation()
	{

	}
	
	
	public ArrayList<Word> containsAbbreviation(ArrayList<Word> wordsList)
	{
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		boolean capitalLetterFound = false;
		boolean notAbbreviation =false;
		
		for (int i = 0; i < wordsList.size(); i++)
		{
			wordsList.get(i).cleareContent();
			
			clearedContent = wordsList.get(i).getClearedContent();
			
			
			for (int j = 0; j < clearedContent.length(); j++)
			{
				for (int k = 0; k < capitalLetters.length; k++)
				{
					if( (clearedContent.substring(j, j+1)).equals(capitalLetters[k]) )
					{
						capitalLetterFound = true;
						break;
					}
					else if ( (clearedContent.substring(j, j+1)).equals(".") && j > 0 )
					{
						capitalLetterFound = true;
						break;
					}
				}
				
				if (!capitalLetterFound)
				{
					notAbbreviation=true;
					break;
				}
				capitalLetterFound = false;
			}
			
			if (!notAbbreviation)
			{
				wordsList.get(i).setType(WordType.ABBREVIATION);
			}
			capitalLetterFound = false;
			notAbbreviation = false;
		}
		modifiedWordsList = wordsList;
		
		return modifiedWordsList;
		
	}


	@Override
	void findEntitiesInDictionary(ArrayList<Word> wordsList,
			ArrayList<String> entities, WordType entityType, int sentenceNumber) {
		// TODO Auto-generated method stub
		
	}
}
