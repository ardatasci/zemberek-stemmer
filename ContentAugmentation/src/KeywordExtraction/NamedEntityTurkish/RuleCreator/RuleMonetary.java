package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleMonetary extends Rule{
	ArrayList<String> monetaryPostfixes = new ArrayList<String>();
	ArrayList<String> monetaryPrefixes = new ArrayList<String>();
	ArrayList<String> numbers = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleMonetary() {
		MinioReader fileReader = MinioReader
				.getFileReader("res/monetarypostfix");
		while (fileReader.inputAvailable()) {
			String monetaryPostfix = fileReader.readLine();
			monetaryPostfixes.add(monetaryPostfix);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/monetaryprefix");
		while (fileReader.inputAvailable()) {
			String monetaryPrefix = fileReader.readLine();
			monetaryPrefixes.add(monetaryPrefix);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/numbers");
		while (fileReader.inputAvailable()) {
			String number = fileReader.readLine();
			numbers.add(number);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsMonetary(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		boolean monetaryPrefixFound = false;
		boolean monetaryPostfixFound = false;
		boolean writtenNumberFound = false;
		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String monetaryPostfix : monetaryPostfixes) {
				if (clearedContent.startsWith(monetaryPostfix)) {
					monetaryPostfixFound = true;
				}
			}

			if (monetaryPostfixFound) {
				wordsList.get(i).setType(WordType.MONETARY);
				try {
					int j = Integer
							.parseInt(wordsList.get(i - 1).getClearedContent()
									.substring(0,
											wordsList.get(i - 1).getClearedContent()
													.length()));
					if (j >= 0) {
						wordsList.get(i - 1).setType(WordType.MONETARY);
					}
				} catch (Exception e) {
					// TODO: handle exception
					try {
						int k = 1;
						while (true) {
							for (String number : numbers) {
								if ((wordsList.get(i - k).getClearedContent())
										.contains(number)) {
									wordsList.get(i).setType(WordType.MONETARY);
									wordsList.get(i - k).setType(WordType.MONETARY);
									writtenNumberFound = true;
									break;
								} else {
									try {
										int l = Integer.parseInt(wordsList
												.get(i - k).getClearedContent()
												.substring(0, wordsList.get(i
														- k).getClearedContent()
														.length()));

										wordsList.get(i - k)
												.setType(WordType.MONETARY);
										writtenNumberFound = true;
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

					} catch (Exception e1) {
						// TODO: handle exception
					}
				}

				monetaryPostfixFound = false;
			}

			for (String monetaryPrefix : monetaryPrefixes) {
				if (clearedContent.contains(monetaryPrefix)) {
					monetaryPrefixFound = true;
				}
			}

			if (monetaryPrefixFound) {
				wordsList.get(i).setType(WordType.MONETARY);

				try {
					int j = Integer
							.parseInt(wordsList.get(i + 1).getClearedContent()
									.substring(0,
											wordsList.get(i + 1).getClearedContent()
													.length() - 1));
					if (j >= 0) {
						wordsList.get(i + 1).setType(WordType.MONETARY);
					}
				} catch (Exception e) {
					// TODO: handle exception

				}

				monetaryPostfixFound = false;
			}
		}
		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
