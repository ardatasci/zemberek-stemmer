package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleLocationName extends Rule{
	ArrayList<String> addressIdentifiers = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleLocationName() {
		MinioReader fileReader = MinioReader
				.getFileReader("res/addressidentifier");
		while (fileReader.inputAvailable()) {
			String addressIdentifier = fileReader.readLine();
			addressIdentifiers.add(addressIdentifier);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsLocationName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		boolean addressIdentifierFound = false;
		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String addressIdentifier : addressIdentifiers) {
				if (clearedContent.startsWith(addressIdentifier)) {
					addressIdentifierFound = true;
				}
			}

			if (addressIdentifierFound) {
				wordsList.get(i).setType(WordType.LOCATION);
				try {
					int k = 1;
					while ((wordsList.get(i - k).getType()).equals(WordType.POSSIBLE)) {
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							break;
						}
						wordsList.get(i - k).setType(WordType.LOCATION);
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				addressIdentifierFound = false;
			}

			for (int j = 0; j < wordsList.get(i).getContent().length(); j++) {
				try {
					if ((wordsList.get(i).getContent().substring(j, j + 1))
							.equals("/")) {
						if ((wordsList.get(i).getType()).equals(WordType.POSSIBLE)) {
							wordsList.get(i).setType(WordType.LOCATION);
						}

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
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
