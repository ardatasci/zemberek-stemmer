package KeywordExtraction.NamedEntityTurkish.RuleCreater;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;

public class RuleDate {
	ArrayList<String> months = new ArrayList<String>();
	ArrayList<String> dateWords = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleDate() {
		MinioReader fileReader = MinioReader.getFileReader("res/months");
		while (fileReader.inputAvailable()) {
			String month = fileReader.readLine();
			months.add(month);
		}

		fileReader.close();

		fileReader = MinioReader.getFileReader("res/datewords");
		while (fileReader.inputAvailable()) {
			String dateWord = fileReader.readLine();
			dateWords.add(dateWord);
		}

		fileReader.close();
	}

	public ArrayList<Word> containsDate(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak
		boolean monthFound = false;
		boolean tarihWordFound = false;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String month : months) {
				if (clearedContent.contains(month)) {
					monthFound = true;
				}
			}

			if (monthFound) {
				wordsList.get(i).setType("date");

				try {
					int j = Integer
							.parseInt(wordsList.get(i - 1).getClearedContent()
									.substring(0,
											wordsList.get(i - 1).getClearedContent()
													.length()));
					if (24 >= j && j > 1) {
						wordsList.get(i - 1).setType("date");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					int j = Integer
							.parseInt(wordsList.get(i + 1).getClearedContent()
									.substring(0,
											wordsList.get(i + 1).getClearedContent()
													.length()));
					if (j > -2000) {
						wordsList.get(i + 1).setType("date");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				monthFound = false;
			}

			for (int j = 0; j < clearedContent.length(); j++) {
				if ((clearedContent.substring(j, j + 1)).equals(".")
						|| (clearedContent.substring(j, j + 1)).equals("/")
						|| (clearedContent.substring(j, j + 1)).equals("-")) {
					try {
						int k = Integer
								.parseInt(clearedContent.substring(0, j));
						try {
							int l = Integer.parseInt(clearedContent.substring(
									j + 1, clearedContent.length() - 1));
							wordsList.get(i).setType("date");
						} catch (Exception e) {
							// TODO: handle exception
							for (int m = j + 1; m < clearedContent.length(); m++) {
								if ((clearedContent.substring(m, m + 1))
										.equals(".")
										|| (clearedContent.substring(m, m + 1))
												.equals("/")
										|| (clearedContent.substring(m, m + 1))
												.equals("-")) {
									try {
										int n = Integer.parseInt(clearedContent
												.substring(j + 1, m));
										wordsList.get(i).setType("date");
									} catch (Exception e1) {
										// TODO: handle exception
									}

								}
							}
						}

					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

			for (String dateWord : dateWords) {
				if (clearedContent.contains(dateWord)) {
					tarihWordFound = true;
				}
			}

			if (tarihWordFound) {
				try {
					int n = Integer.parseInt(wordsList.get(i - 1)
							.getClearedContent());
					wordsList.get(i - 1).setType("date");
					wordsList.get(i).setType("date");
				} catch (Exception e1) {
					// TODO: handle exception
				}
				// wordsList.get(i).setType("date");
				try {
					// burda bunu yapmaktan vaz gectim. neden: odevi tarihinde
					// veremedim
					// wordsList.get(i-1).setType("date");

				} catch (Exception e) {
					// TODO: handle exception
				}
				tarihWordFound = false;
			}

		}
		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
