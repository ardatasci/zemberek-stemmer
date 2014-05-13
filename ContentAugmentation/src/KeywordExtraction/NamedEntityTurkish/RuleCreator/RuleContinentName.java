package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleContinentName extends Rule{
	ArrayList<String> continents = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleContinentName() {
		MinioReader fileReader = MinioReader.getFileReader("res/continents");
		while (fileReader.inputAvailable()) {
			String continent = fileReader.readLine();
			continents.add(continent);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsContinentName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String continent : continents) {

				if (clearedContent.startsWith(continent)) {

					if (wordsList.get(i).getClearedContent().equals("Amerika")) {
						try {

							if (wordsList.get(i + 1).getType().equals(WordType.COUNTRY)) {
								wordsList.get(i).setType(WordType.COUNTRY);
								wordsList.get(i + 1).setType(WordType.COUNTRY);
							} else if (i != 0
									&& (wordsList.get(i - 1).getClearedContent()
											.equals("Kuzey") || wordsList
											.get(i - 1).getClearedContent()
											.equals("Güney"))) {
								wordsList.get(i - 1).setType(WordType.CONTINENT);
								wordsList.get(i - 1).setContent(
										wordsList.get(i - 1).getContent() + " "
												+ wordsList.remove(i).getClearedContent());
								i--;
							} else {
								wordsList.get(i).setType(WordType.CONTINENT);
							}
						} catch (Exception e) {
							if (i != 0
									&& (wordsList.get(i - 1).getClearedContent()
											.equals("Kuzey") || wordsList
											.get(i - 1).getClearedContent()
											.equals("Güney"))) {
								wordsList.get(i - 1).setType(WordType.CONTINENT);
								wordsList.get(i - 1).setContent(
										wordsList.get(i - 1).getContent() + " "
												+ wordsList.remove(i).getContent());
								i--;
							} else {
								wordsList.get(i).setType(WordType.CONTINENT);
							}
						}

					} else if (i != wordsList.size() - 1
							&& wordsList.get(i + 1).getClearedContent()
									.contains("kıta")) {
						wordsList.get(i).setType(WordType.CONTINENT);
						// wordsList.get(i+1).setType("continentName");
					} else if (wordsList.get(i).getType().equals(WordType.COUNTRY)
							|| wordsList.get(i).getType().equals(WordType.CONTINENT)) {
						if (wordsList.get(i + 1).getType().equals(WordType.COUNTRY)) {
							wordsList.get(i).setType(WordType.COUNTRY);
						}
					} else {
						wordsList.get(i).setType(WordType.CONTINENT);
					}
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
