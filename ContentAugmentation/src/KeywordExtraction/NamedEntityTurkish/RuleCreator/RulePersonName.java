package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RulePersonName extends Rule {
	ArrayList<String> personNames = new ArrayList<String>();
	ArrayList<String> preNouns = new ArrayList<String>();
	ArrayList<String> postNouns = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	ArrayList<Word> myModifiedWordsList = new ArrayList<Word>();
	ArrayList<Word> preFoundPersonList = new ArrayList<Word>();
	String clearedContent = "";
	String content = "";

	boolean preNounFound = false;
	boolean postNounFound = false;

	public RulePersonName() {
		MinioReader fileReader = MinioReader
				.getFileReader("res/person_dictionary");
		while (fileReader.inputAvailable()) {
			String personName = fileReader.readLine();
			personNames.add(personName);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/prenoun");
		while (fileReader.inputAvailable()) {
			String preNoun = fileReader.readLine();
			preNouns.add(preNoun);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/postnoun");
		while (fileReader.inputAvailable()) {
			String postNoun = fileReader.readLine();
			postNouns.add(postNoun);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsPersonName(ArrayList<Word> wordsList,
			int sentenceNumber) {
		this.wordsList = wordsList;

		findEntitiesInDictionary(wordsList, personNames, WordType.PERSON,
				sentenceNumber);

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}

	@Override
	void findEntitiesInDictionary(ArrayList<Word> wordsList,
			ArrayList<String> entities, WordType entityType, int sentenceNumber) {
		String personToBeAdded = "";
		boolean punctiationFound = true;
		boolean namedEntityCompleted = false;
		boolean namedEntityFound = false;
		boolean possibleNameFound = false;
		for (int i = 0; i < wordsList.size(); i++) {
			
			namedEntityFound = false;
			wordsList.get(i).cleareContent();
			String processedContent = wordsList.get(i).getClearedContent();
			String nonProcessedcontent = wordsList.get(i).getContent();

			for (int j = 0; j < entities.size(); j++) {

				if (processedContent.equals(entities.get(j))) {
					if (!wordsList.get(i).getType()
									.equals(WordType.COUNTRY) && !wordsList
									.get(i).getType().equals(WordType.LOCATION)) {
						namedEntityFound = true;
						wordsList.get(i).setType(WordType.PERSON);
						wordsList.get(i).setSubType(WordType.NAME);
						if (personToBeAdded.equals(""))
							personToBeAdded = wordsList.get(i)
									.getClearedContent();
						else
							personToBeAdded += " "
									+ wordsList.get(i).getClearedContent();
						if (!punctiationFound) {

						}
						break;

					}

				}

			}

			if (!namedEntityFound) {
				
				if (wordsList.get(i).getType() != null) {
					if (wordsList.get(i).getType()
									.equals(WordType.POSSIBLE)) {
						possibleNameFound = true;
					}
				}
				
				if (!personToBeAdded.equals("")) {
					if(possibleNameFound){
						personToBeAdded += " "
								+ wordsList.get(i).getClearedContent();
						possibleNameFound = false;
					}

					Word word = new Word();
					word.setClearedContent(personToBeAdded);
					word.setContent(personToBeAdded);
					word.setType(entityType);
					word.setSentenceNumber(sentenceNumber);
					annotatedWordListCreator.addAnnotatedWord(word);
					namedEntityCompleted = false;
					personToBeAdded = "";
				}
				else{
					possibleNameFound = false;
				}
			}
			if (namedEntityCompleted) {
				Word word = new Word();
				word.setClearedContent(personToBeAdded);
				word.setContent(personToBeAdded);
				word.setType(entityType);
				word.setSentenceNumber(sentenceNumber);
				annotatedWordListCreator.addAnnotatedWord(word);
				namedEntityCompleted = false;
				personToBeAdded = "";

			}
			if (isContainAPunctuation(nonProcessedcontent)) {
				punctiationFound = true;
				if (!personToBeAdded.equals("")) {
					Word word = new Word();
					word.setClearedContent(personToBeAdded);
					word.setContent(personToBeAdded);
					word.setType(entityType);
					word.setSentenceNumber(sentenceNumber);
					annotatedWordListCreator.addAnnotatedWord(word);
					namedEntityCompleted = false;
					personToBeAdded = "";
				}

			}
		}
		if(namedEntityFound && !personToBeAdded.equals("")){
			Word word = new Word();
			word.setClearedContent(personToBeAdded);
			word.setContent(personToBeAdded);
			word.setType(entityType);
			word.setSentenceNumber(sentenceNumber);
			annotatedWordListCreator.addAnnotatedWord(word);
			namedEntityCompleted = false;
			personToBeAdded = "";
		}

	}

	private boolean isContainAPunctuation(String content) {
		int length = content.length();
		if (content.substring(length - 2, length - 1).equals(",")
				|| content.substring(length - 2, length - 1).equals(";"))
			return true;
		else
			return false;
	}

}
