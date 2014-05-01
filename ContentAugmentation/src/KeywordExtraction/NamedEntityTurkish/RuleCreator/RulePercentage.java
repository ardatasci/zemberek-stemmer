package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RulePercentage extends Rule{
	ArrayList<String> percentagePrefixes = new ArrayList<String>();
	ArrayList<String> numbers = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RulePercentage() {
		MinioReader fileReader = MinioReader
				.getFileReader("res/percentageprefix");
		while (fileReader.inputAvailable()) {
			String percentagePrefix = fileReader.readLine();
			percentagePrefixes.add(percentagePrefix);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/numbers");
		while (fileReader.inputAvailable()) {
			String number = fileReader.readLine();
			numbers.add(number);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsPercentage(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		boolean percentagePrefixFound = false;
		boolean writtenNumberFound = false;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String percentagePrefix : percentagePrefixes) {

				if (clearedContent.contains(percentagePrefix)) {
					percentagePrefixFound = true;
					break;
				}
			}

			if (percentagePrefixFound) {
				try {
					int j = Integer
							.parseInt(wordsList.get(i + 1).getClearedContent()
									.substring(0,
											wordsList.get(i + 1).getClearedContent()
													.length()));
					if (j >= 0) {
						wordsList.get(i).setType(WordType.PERCENTAGE);
						wordsList.get(i + 1).setType(WordType.PERCENTAGE);
					}
				} catch (Exception e) {
					// TODO: handle exception
					try {
						int k = 1;
						while (true) {
							for (String number : numbers) {
								if ((wordsList.get(i + k).getClearedContent())
										.contains(number)) {
									wordsList.get(i).setType(WordType.PERCENTAGE);
									wordsList.get(i + k).setType(WordType.PERCENTAGE);
									writtenNumberFound = true;
									break;
								} else {
									writtenNumberFound = false;
								}

							}

							if (writtenNumberFound == false) {
								break;
							}

							k++;
						}

					} catch (Exception e1) {
						// TODO: handle exception
					}

				}

				percentagePrefixFound = false;
			}

			try {
				if ((clearedContent.substring(0, 1)).equals("%")) {
					int l = Integer.parseInt(clearedContent.substring(1,
							clearedContent.length() - 1));
					wordsList.get(i).setType(WordType.PERCENTAGE);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
