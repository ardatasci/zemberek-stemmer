package KeywordExtraction.NamedEntityTurkish.RuleCreater;

import java.util.ArrayList;

import tr.edu.hacettepe.cs.minio.MinioReader;
import KeywordExtraction.NamedEntityTurkish.Word;

public class RuleCityName {
	ArrayList<String> cities = new ArrayList<String>();

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();

	String clearedContent = "";

	public RuleCityName() {
		MinioReader fileReader = MinioReader.getFileReader("res/cities");
		while (fileReader.inputAvailable()) {
			String city = fileReader.readLine();
			cities.add(city);
		}
		
		fileReader.close();
	}

	public ArrayList<Word> containsCityName(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
		// XXX -- ici doldurulacak

		for (int i = 0; i < wordsList.size(); i++) {
			wordsList.get(i).cleareContent();

			clearedContent = wordsList.get(i).getClearedContent();
			boolean exist = cities.contains(clearedContent);
			if (exist && (( wordsList.get(i).getType() != null && !wordsList.get(i).getType().equals("locationName")
					&& !wordsList.get(i).getType().equals("organizationName")) || wordsList.get(i).getType() == null)) {
				wordsList.get(i).setType("cityName");
				wordsList.get(i).setSubType("city");
			}
		}

		modifiedWordsList = wordsList;

		return modifiedWordsList;

	}
}
