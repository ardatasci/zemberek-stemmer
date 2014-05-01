package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RulePersonName extends Rule{
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
		
		//findEntitiesInDictionary(wordsList, personNames, WordType.PERSON);

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();
			content = wordsList.get(i).getContent();
			for (String personName : personNames) {

				//if (clearedContent.contains(personName)
				if (clearedContent.equals(personName)
						&& !wordsList.get(i).getType().equals(WordType.LOCATION)
						&& !wordsList.get(i).getType().equals(WordType.ORGANIZATION)
						&& !wordsList.get(i).getType().equals(WordType.DATE)) {
//					if(personName.equalsIgnoreCase("Yunus") || personName.equalsIgnoreCase("Emre")){
//						System.out.println("Yunus Emre");
//					}
//					if(wordsList.get(i + 1).equals("Emre")){
//						System.out.println("bldum");
//					}
					wordsList.get(i).setType(WordType.PERSON);
					wordsList.get(i).setSubType(WordType.NAME);

					try {
						if (wordsList.get(i + 1).getType().equals(WordType.POSSIBLE)
								|| wordsList.get(i + 1).getType().equals(WordType.PERSON)) {
//							if(wordsList.get(i + 1).equals("Emre")){
//								System.out.println("bldum");
//							}
							wordsList.get(i + 1).setType(WordType.PERSON);
							wordsList.get(i + 1).setSubType(WordType.NAME);
							preFoundPersonList.add(wordsList.get(i + 1));
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (clearedContent.equals(personName)) {
					wordsList.get(i).setSubType(WordType.NAME);
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
					while ((wordsList.get(i + k).getType()).equals(WordType.POSSIBLE)
							|| (wordsList.get(i + k).getType()).equals(WordType.PERSON)) {
						wordsList.get(i).setType(WordType.PERSON);
						int l = 1;
						try {
							while (k == 1
									&& (wordsList.get(i - l).getType())
											.equals(WordType.ORGANIZATION)) {
								if ((wordsList.get(i - l).getContent()
										.substring(0,
												wordsList.get(i - l).getContent()
														.length() - 1))
										.equals(wordsList.get(i - l).getClearedContent())) {
									wordsList.get(i - l).setType(WordType.PERSON);
									wordsList.get(i - l).setSubType(
											WordType.ORGANIZATION);

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
												.equals(WordType.CITY)) {
									if ((wordsList.get(i - l).getContent()
											.substring(
													0,
													wordsList.get(i - l).getContent()
															.length() - 1))
											.equals(wordsList.get(i - l).getClearedContent())) {
										wordsList.get(i - l).setType(
												WordType.PERSON);
										wordsList.get(i - l).setSubType(WordType.CITY);
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
							wordsList.get(i + k).setType(WordType.PERSON);
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

						wordsList.get(i + k).setType(WordType.PERSON);
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				preNounFound = false;
			}

			for (String postNoun : postNouns) {
				if (clearedContent.equals(postNoun)) {
					postNounFound = true;
				}
			}

			if (postNounFound) {
				try {
					int k = 1;

					while ((wordsList.get(i - k).getType()).equals(WordType.POSSIBLE)
							|| (wordsList.get(i - k).getType()).equals(WordType.PERSON)
							|| (wordsList.get(i - k).getType())
									.equals(WordType.ORGANIZATION)
							|| (wordsList.get(i - k).getType())
									.equals(WordType.LOCATION)) {
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							wordsList.get(i).setType(WordType.PERSON);

							wordsList.get(i - k).setType(WordType.PERSON);
							preFoundPersonList.add(wordsList.get(i - k));
							break;
						}

						if ((wordsList.get(i - k).getType())
								.equals(WordType.ORGANIZATION)
								&& wordsList.get(i - k).getContent()
										.substring(
												0,
												wordsList.get(i - k).getContent()
														.length() - 1)
										.equals(
												wordsList.get(i - k).getClearedContent())) {
							wordsList.get(i).setType(WordType.PERSON);

							wordsList.get(i - k).setType(WordType.PERSON);
							preFoundPersonList.add(wordsList.get(i - k));

						}
						// wordsList.get(i-k).setType(WordType.PERSON);
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
					wordsList.get(i).setType(WordType.PERSON);
					wordsList.get(i).setSubType(WordType.NAME);
				}

			}
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
