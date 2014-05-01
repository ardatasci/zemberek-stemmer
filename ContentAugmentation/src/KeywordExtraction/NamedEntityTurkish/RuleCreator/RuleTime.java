package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleTime extends Rule{
	ArrayList<String> hours = new ArrayList<String>();
	ArrayList<String> timeWords = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleTime() {
		MinioReader fileReader = MinioReader.getFileReader("res/hours");
		while (fileReader.inputAvailable()) {
			String hour = fileReader.readLine();
			hours.add(hour);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/timewords");
		while (fileReader.inputAvailable()) {
			String timeWord = fileReader.readLine();
			timeWords.add(timeWord);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsTime(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		boolean zamanWordFound = false;
		boolean writtenNumberFound = false;
		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (int j = 0; j < clearedContent.length(); j++) {
				if ((clearedContent.substring(j, j + 1)).equals(".")
						|| (clearedContent.substring(j, j + 1)).equals(":")) {
					try {
						int k = Integer
								.parseInt(clearedContent.substring(0, j));
						if (k <= 24 && k >= 0) {
							try {
								int l = Integer.parseInt(clearedContent
										.substring(j + 1, clearedContent
												.length()));

								if (l <= 59 && l >= 0) {
									if ((wordsList.get(i + 1).getType()) != null) {

									} else {
										wordsList.get(i).setType(WordType.TIME);
									}
								}

							} catch (Exception e) {
								// TODO: handle exception
							}
						}

					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

			for (String timeWord : timeWords) {
				if (clearedContent.startsWith(timeWord)) {
					zamanWordFound = true;
				}
			}

			if (zamanWordFound) {
				wordsList.get(i).setType(WordType.TIME);
				try {
					int j = Integer.parseInt(wordsList.get(i - 1).getContent()
							.substring(0,
									wordsList.get(i - 1).getContent().length() - 1));
					wordsList.get(i - 1).setType(WordType.TIME);

				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					int k = 1;
					while (true) {
						for (String hour : hours) {
							if ((wordsList.get(i - k).getClearedContent())
									.contains(hour)) {
								wordsList.get(i - k).setType(WordType.TIME);
								writtenNumberFound = true;
								break;
							} else {
								writtenNumberFound = false;
							}

						}

						for (String hour : hours) {
							if ((wordsList.get(i + k).getClearedContent())
									.contains(hour)) {
								wordsList.get(i + k).setType(WordType.TIME);
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

				} catch (Exception e) {
					// TODO: handle exception
				}

				zamanWordFound = false;
			}

		}
		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
