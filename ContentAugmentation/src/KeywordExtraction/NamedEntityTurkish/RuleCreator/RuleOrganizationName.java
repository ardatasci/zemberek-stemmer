package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.yapi.Kelime;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleOrganizationName extends Rule {
	ArrayList<String> organizationPostfixes = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleOrganizationName() {
		MinioReader fileReader = MinioReader
				.getFileReader("res/organizationpostfix");
		while (fileReader.inputAvailable()) {
			String organizationPostfix = fileReader.readLine();
			organizationPostfixes.add(organizationPostfix);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsOrganizationName(ArrayList<Word> wordsList,
			int sentenceNumber) {
		this.wordsList = wordsList;

		boolean organizationPostFixesFound = false;
		boolean possibleOrganizationFound = false;
		String organizationToBeAdded = "";

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();
//			Kelime[] kelimeler = zemberek.kelimeCozumle(wordsList.get(i)
//					.getContent(), strateji);
			Kelime[] kelimeler = zemberek.kelimeCozumle(clearedContent, strateji);

			for (String organizationPostfix : organizationPostfixes) {
				// if (clearedContent.startsWith(organizationPostfix)) {
				// organizationPostFixesFound = true;
				// }

				for (Kelime kelime2 : kelimeler) {
					if (kelime2.kok().icerik().equals(organizationPostfix)) {
						// organizationPostFixesFound = true;
						// System.out.println(kelime2.kok());
						if (!organizationToBeAdded.equals("")) {
							String[] splited = organizationToBeAdded.split(" ");
							if (splited.length == 0) {
								organizationPostFixesFound = false;
								possibleOrganizationFound = false;
								organizationToBeAdded = "";
								
							} else
								organizationPostFixesFound = true;
						}
						break;

					}

				}
			}
			// && !wordsList.get(i).isLastElementAPunctuation() &&
			// (possibleOrganizationFound && wordsList.get(i).getAffix() !=
			// null))
			if (wordsList.get(i).getType() != null) {
				if (wordsList.get(i).isLastElementAPunctuation()
						&& !organizationPostFixesFound) {
					possibleOrganizationFound = false;
					organizationToBeAdded = "";
				}
				else if(wordsList.get(i).getAffix() != null  &&!organizationPostFixesFound){
					possibleOrganizationFound = false;
					organizationToBeAdded = "";
				}
				else {
					if (wordsList.get(i).getType().equals(WordType.POSSIBLE)
							|| wordsList.get(i).getType().equals(WordType.CITY)
							|| wordsList.get(i).getType()
									.equals(WordType.COUNTRY)
							|| wordsList.get(i).getType()
									.equals(WordType.PERSON)) {
						possibleOrganizationFound = true;
						if (organizationToBeAdded.equals(""))
							organizationToBeAdded = wordsList.get(i)
									.getClearedContent();
						else
							organizationToBeAdded += " "
									+ wordsList.get(i).getClearedContent();
					}
				}
			} else {
				possibleOrganizationFound = false;
				organizationToBeAdded = "";
			}

			if (organizationPostFixesFound) {
				if (!organizationToBeAdded.equals("")) {
					Word word = new Word();
					word.setClearedContent(organizationToBeAdded);
					word.setContent(organizationToBeAdded);
					word.setType(WordType.ORGANIZATION);
					word.setSentenceNumber(sentenceNumber);
					annotatedWordListCreator.addAnnotatedWord(word);
					organizationPostFixesFound = false;
					possibleOrganizationFound = false;
					organizationToBeAdded = "";
				}
			} else if (!possibleOrganizationFound) {
				organizationToBeAdded = "";
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
