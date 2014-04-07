package KeywordExtraction.NamedEntityTurkish.RuleCreater;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;

public class RuleCountryName {
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
		
		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();
			clearedContent = wordsList.get(i).getClearedContent();

			if (countries.contains(clearedContent)) {
				wordsList.get(i).setType("countryName");
			}

			for (String countryWord : countryWords) {
				if (clearedContent.contains(countryWord)) {
					System.out.println("----" + countryWord);
					countryWordFound = true;
				}
			}

			if (countryWordFound) {
				System.out.println("country word found");
				try {
					int k = 1;
					while ((wordsList.get(i - k).getType()).equals("possibleName")
							|| (wordsList.get(i - k).getType())
									.equals("continentName")
							|| (wordsList.get(i - k).getType())
									.equals("countryName")) {
						System.out.println("country word found mal herhalde bunu yazan: " + k);
						wordsList.get(i).setType("countryName");
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							System.out.println("bu da neyse: " + k);
							wordsList.get(i - k).setType("countryName");
							break;
						}
						wordsList.get(i - k).setType("countryName");
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
}
