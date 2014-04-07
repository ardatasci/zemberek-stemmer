package KeywordExtraction.NamedEntityTurkish.RuleCreater;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;

public class RulePersonName {
	ArrayList<String> personNames = new ArrayList<String>();
	ArrayList<String> preNouns = new ArrayList<String>();
	ArrayList<String> postNouns = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	ArrayList<Word> preFoundPersonList = new ArrayList<Word>();

	String clearedContent = "";
	String content = "";

	boolean preNounFound = false;
	boolean postNounFound = false;

	public RulePersonName() {
		MinioReader fileReader = MinioReader.getFileReader("res/personname");
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

	public ArrayList<Word> containsPersonName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();
			content = wordsList.get(i).getContent();

			for (String personName : personNames) {

				if (clearedContent.contains(personName)
						&& !wordsList.get(i).getType().equals("locationName")
						&& !wordsList.get(i).getType().equals("organizationName")
						&& !wordsList.get(i).getType().equals("date")) {
					if(personName.equalsIgnoreCase("Yunus") || personName.equalsIgnoreCase("Emre")){
						System.out.println("Yunus Emre");
					}
					if(wordsList.get(i + 1).equals("Emre")){
						System.out.println("bldum");
					}
					wordsList.get(i).setType("personName");
					wordsList.get(i).setSubType("name");

					try {
						if (wordsList.get(i + 1).getType().equals("possibleName")
								|| wordsList.get(i + 1).getType().equals("person")) {
							if(wordsList.get(i + 1).equals("Emre")){
								System.out.println("bldum");
							}
							wordsList.get(i + 1).setType("personName");
							wordsList.get(i + 1).setSubType("name");
							preFoundPersonList.add(wordsList.get(i + 1));
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (clearedContent.contains(personName)) {
					wordsList.get(i).setSubType("name");
				}
			}

			for (String preNoun : preNouns) {
				if (clearedContent.startsWith(preNoun)) {
					preNounFound = true;
					if (content.contains(",")) {
						preNounFound = false;
					}
				}
			}

			if (preNounFound) {
				try {
					int k = 1;
					while ((wordsList.get(i + k).getType()).equals("possibleName")
							|| (wordsList.get(i + k).getType()).equals("personName")) {
						wordsList.get(i).setType("personName");
						int l = 1;
						try {
							while (k == 1
									&& (wordsList.get(i - l).getType())
											.equals("organizationName")) {
								if ((wordsList.get(i - l).getContent()
										.substring(0,
												wordsList.get(i - l).getContent()
														.length() - 1))
										.equals(wordsList.get(i - l).getClearedContent())) {
									wordsList.get(i - l).setType("personName");
									wordsList.get(i - l).setSubType(
											"organization");

								}
								l++;
							}
							boolean b = false;

							for (String preNoun : preNouns) {
								if (wordsList.get(i - 1).getClearedContent()
										.startsWith(preNoun)) {
									b = true;
								}
							}

							if (!b) {
								l = 2;
								while (k == 1
										&& (wordsList.get(i - l).getType())
												.equals("cityName")) {
									if ((wordsList.get(i - l).getContent()
											.substring(
													0,
													wordsList.get(i - l).getContent()
															.length() - 1))
											.equals(wordsList.get(i - l).getClearedContent())) {
										wordsList.get(i - l).setType(
												"personName");
										wordsList.get(i - l).setSubType("city");
									}
									l++;
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

						if (wordsList.get(i + k).getContent().substring(
								wordsList.get(i + k).getContent().length() - 2,
								wordsList.get(i + k).getContent().length() - 1)
								.equals(",")) {
							wordsList.get(i + k).setType("personName");
							preFoundPersonList.add(wordsList.get(i + k));
							break;
						}
						boolean b = false;

						for (String preNoun : preNouns) {
							if (wordsList.get(i + 1).getClearedContent()
									.startsWith(preNoun)) {
								b = true;
							}
						}

						if (!b) {
							preFoundPersonList.add(wordsList.get(i + k));

						}

						wordsList.get(i + k).setType("personName");
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				preNounFound = false;
			}

			for (String postNoun : postNouns) {
				if (clearedContent.contains(postNoun)) {
					postNounFound = true;
				}
			}

			if (postNounFound) {
				try {
					int k = 1;

					while ((wordsList.get(i - k).getType()).equals("possibleName")
							|| (wordsList.get(i - k).getType()).equals("personName")
							|| (wordsList.get(i - k).getType())
									.equals("organizationName")
							|| (wordsList.get(i - k).getType())
									.equals("locationName")) {
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							wordsList.get(i).setType("personName");

							wordsList.get(i - k).setType("personName");
							preFoundPersonList.add(wordsList.get(i - k));
							break;
						}

						if ((wordsList.get(i - k).getType())
								.equals("organizationName")
								&& wordsList.get(i - k).getContent()
										.substring(
												0,
												wordsList.get(i - k).getContent()
														.length() - 1)
										.equals(
												wordsList.get(i - k).getClearedContent())) {
							wordsList.get(i).setType("personName");

							wordsList.get(i - k).setType("personName");
							preFoundPersonList.add(wordsList.get(i - k));

						}
						// wordsList.get(i-k).setType("personName");
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
					// System.out.println(wordsList.get(i).content);

				}
				postNounFound = false;
			}

		}

		for (int i = 0; i < wordsList.size(); i++) {
			for (int j = 0; j < preFoundPersonList.size(); j++) {
				if (wordsList.get(i).getClearedContent().equals(preFoundPersonList
						.get(j).getClearedContent())) {
					wordsList.get(i).setType("personName");
					wordsList.get(i).setSubType("name");
				}

			}
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
