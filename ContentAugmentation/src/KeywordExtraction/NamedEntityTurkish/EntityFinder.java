package KeywordExtraction.NamedEntityTurkish;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleAbbreviation;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleCityName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleContinentName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleCountryName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleDate;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleLocationName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleMonetary;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleOrganizationName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RulePercentage;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RulePersonName;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RulePossibleNames;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleQuantity;
import KeywordExtraction.NamedEntityTurkish.RuleCreator.RuleTime;


public class EntityFinder {
	
	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	
	String outputType = "";
	int[] wantedEntities;
	// rule lar burada tanimlanacak
	
	RulePossibleNames rulePossibleNames = new RulePossibleNames();
	RuleAbbreviation ruleAbbreviation = new RuleAbbreviation();
	RuleDate ruleDate = new RuleDate();
	RuleTime ruleTime = new RuleTime();
	RuleMonetary ruleMonetary = new RuleMonetary();
	RulePercentage rulePercentage = new RulePercentage();
	RuleQuantity ruleQuantity = new RuleQuantity();
	//RuleCategorizeNames ruleCategorizeNames = new RuleCategorizeNames();
	
	RuleLocationName ruleLocationName = new RuleLocationName();
	RuleCityName ruleCityName = new RuleCityName();
	RuleCountryName ruleCountryName = new RuleCountryName();
	RuleContinentName ruleContinentName = new RuleContinentName();
	RuleOrganizationName ruleOrganizationName = new RuleOrganizationName();
	RulePersonName rulePersonName = new RulePersonName();
	
	public EntityFinder()
	{
		
	}
	
	public ArrayList<Word> findEntities (int[] wantedEntities, ArrayList<Word> wordsList, int sentenceNumber)
	{
		this.wantedEntities = wantedEntities;
		this.wordsList = wordsList;

		
		// XXX -- asil is burada yapilacak
		// burada cumleler tek tek geliyo yani sadece bir cumlenin icindeki entity ler bulunacak
		// tek tek her entity icin denendikten sonra modifiedWordList olusturulacak
		// uzun lafin kisasi bu gelen cumleye burada her bir entity rule uygulanarak cumle nin entity leri bulunacak
		
		
		
		modifiedWordsList = rulePossibleNames.containsName(wordsList);
		//modifiedWordsList = rulePossibleNames.determinePossibleNames(wordsList);

			

		if (wantedEntities[0] == 1) {
			modifiedWordsList = ruleAbbreviation.containsAbbreviation(wordsList, sentenceNumber);
		}
		if (wantedEntities[1] == 1) {
			//modifiedWordsList = ruleDate.containsDate(modifiedWordsList);
		}
		if (wantedEntities[2] == 1) {
			//modifiedWordsList = ruleTime.containsTime(modifiedWordsList);
		}
		if (wantedEntities[4] == 1) {
			//modifiedWordsList = rulePercentage.containsPercentage(modifiedWordsList);
		}
		if (wantedEntities[5] == 1) {
			//modifiedWordsList = ruleQuantity.containsQuantity(modifiedWordsList);
		}
		if (wantedEntities[3] == 1) {
			//modifiedWordsList = ruleMonetary.containsMonetary(modifiedWordsList);
		}
		
		//modifiedWordsList = ruleCategorizeNames.containsCategorizedNames(wordsList);
		
		if (wantedEntities[6] == 1) {
			modifiedWordsList = ruleLocationName.containsLocationName(modifiedWordsList, sentenceNumber);
		}
		if (wantedEntities[7] == 1) {
			modifiedWordsList = ruleCityName.containsCityName(modifiedWordsList, sentenceNumber);
		}
		if (wantedEntities[8] == 1) {
			modifiedWordsList = ruleCountryName.containsCountryName(modifiedWordsList, sentenceNumber);
		}
		if (wantedEntities[9] == 1) {
			//modifiedWordsList = ruleContinentName.containsContinentName(modifiedWordsList);
		}
		if (wantedEntities[10] == 1) {
			modifiedWordsList = ruleOrganizationName.containsOrganizationName(modifiedWordsList, sentenceNumber);
		}
		if (wantedEntities[11] == 1) {
			modifiedWordsList = rulePersonName.containsPersonName(modifiedWordsList, sentenceNumber);
		}
//		for (Word word : modifiedWordsList) {
//			System.out.println(word.getContent() + "--++" + word.getType());
//		}
//		if (wantedEntities[11] == 1) {
//			modifiedWordsList = rulePersonName.containsPersonName(modifiedWordsList, sentenceNumber);
//		}
//		for (Word word : modifiedWordsList) {
//			System.out.println(word.getContent() + "--++" + word.getType());
//		}

		return modifiedWordsList;
		
	}
	
}
