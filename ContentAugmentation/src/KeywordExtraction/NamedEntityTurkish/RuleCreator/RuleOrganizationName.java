package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public class RuleOrganizationName extends Rule{
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

	public ArrayList<Word> containsOrganizationName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;

		boolean organizationPostFixesFound = false;

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();

			for (String organizationPostfix : organizationPostfixes) {
				if (clearedContent.startsWith(organizationPostfix)) {
					organizationPostFixesFound = true;
				}
			}

			if (organizationPostFixesFound) {
				wordsList.get(i).setType(WordType.ORGANIZATION);
				try {
					int k = 1;

					while ((wordsList.get(i - k).getType()).equals(WordType.POSSIBLE)
							|| (wordsList.get(i - k).getType()).equals(WordType.CITY)
							|| (wordsList.get(i - k).getType())
									.equals(WordType.ORGANIZATION)) {
						if (wordsList.get(i - k).getContent().substring(
								wordsList.get(i - k).getContent().length() - 2,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(",")) {
							break;
						}
						if (wordsList.get(i - k).getContent().substring(0,
								wordsList.get(i - k).getContent().length() - 1)
								.equals(wordsList.get(i - k).getClearedContent())) {
							wordsList.get(i - k).setType(WordType.ORGANIZATION);

						}
						// wordsList.get(i-k).setType("organizationName");
						k++;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				organizationPostFixesFound = false;
			}

		}
		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
