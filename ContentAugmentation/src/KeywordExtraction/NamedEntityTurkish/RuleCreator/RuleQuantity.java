package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleQuantity extends Rule{
	ArrayList<String> quantityWords = new ArrayList<String>();
	ArrayList<String> numbers = new ArrayList<String>();
	
	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	
	String clearedContent = "";

	public RuleQuantity()
	{
		MinioReader fileReader = MinioReader.getFileReader("res/quantitywords");
		while (fileReader.inputAvailable()) {
			String quantityWord = fileReader.readLine();
			quantityWords.add(quantityWord);
		}

		fileReader.close();
		
		fileReader = MinioReader.getFileReader("res/numbers");
		while (fileReader.inputAvailable()) {
			String number = fileReader.readLine();
			numbers.add(number);
		}

		fileReader.close();
	}
	
	
	public ArrayList<Word> containsQuantity(ArrayList<Word> wordsList)
	{
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		boolean quantityWordFound = false;
		boolean writtenNumberFound = false;
		for (int i = 0; i < wordsList.size(); i++)
		{
			wordsList.get(i).cleareContent();
			
			clearedContent = wordsList.get(i).getClearedContent();
			
			for (String quantityWord: quantityWords) {

					if ( clearedContent.equals(quantityWord) )
					{
						quantityWordFound = true;
					}
				}
	        	
			if(quantityWordFound)
			{
				wordsList.get(i).setType(WordType.QUANTITY);
				try {
					int k = 1;
					while ( true )
					{
						
				       for (String number : numbers) {
								if( (wordsList.get(i-k).getClearedContent()).contains(number) )
								{
									if ( wordsList.get(i-k).getType() == null ) {
										wordsList.get(i-k).setType(WordType.QUANTITY);
										writtenNumberFound = true;
										break;	
									}
								}
								else
								{
									try {
										int l = Integer.parseInt(wordsList.get(i-k).getClearedContent().substring(0,wordsList.get(i-k).getClearedContent().length()-1));
										if ( wordsList.get(i-k).getType() == null ) {
											wordsList.get(i-k).setType(WordType.QUANTITY);
											writtenNumberFound = true;		
										}
									} catch (Exception e2) {
										// TODO: handle exception
										writtenNumberFound = false;
									}
								}
								
							}
		        	
						if (writtenNumberFound == false) {
							break;
						}
						
						k++;
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				quantityWordFound = false;
			}
			
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
