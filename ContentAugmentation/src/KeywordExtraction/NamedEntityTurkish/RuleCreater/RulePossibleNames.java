package KeywordExtraction.NamedEntityTurkish.RuleCreater;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.Word;

public class RulePossibleNames {

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	
	String[] capitalLetters = {"A", "B", "C", "Ç", "D", "E", "F", "G", "Ğ", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "V", "Y", "Z"};
 	
	String clearedContent = "";
	
	public RulePossibleNames()
	{

	}
	
	
	public ArrayList<Word> containsName(ArrayList<Word> wordsList)
	{
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		boolean capitalLetterFound = false;
		boolean cik =false;
		
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
					
				}
				if (!capitalLetterFound)
				{
					break;
				}
				
			}
			
			if (capitalLetterFound)
			{
				wordsList.get(i).setType("possibleName");
			}
			capitalLetterFound = false;
		}
		
		
		modifiedWordsList = wordsList;
		
		return modifiedWordsList;
		
	}
}
