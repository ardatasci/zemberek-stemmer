package KeywordExtraction.NamedEntityTurkish;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleAbbreviation;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleCityName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleContinentName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleCountryName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleDate;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleLocationName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleMonetary;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleOrganizationName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RulePercentage;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RulePersonName;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RulePossibleNames;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleQuantity;
import KeywordExtraction.NamedEntityTurkish.RuleCreater.RuleTime;


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
	
	public ArrayList<Word> findEntities (int[] wantedEntities, ArrayList<Word> wordsList)
	{
		this.wantedEntities = wantedEntities;
		this.wordsList = wordsList;
		
		// XXX -- asil is burada yapilacak
		// burada cumleler tek tek geliyo yani sadece bir cumlenin icindeki entity ler bulunacak
		// tek tek her entity icin denendikten sonra modifiedWordList olusturulacak
		// uzun lafin kisasi bu gelen cumleye burada her bir entity rule uygulanarak cumle nin entity leri bulunacak
		
		
		
			modifiedWordsList = rulePossibleNames.containsName(wordsList);
		
		if (wantedEntities[0] == 1) {
			modifiedWordsList = ruleAbbreviation.containsAbbreviation(wordsList);
		}
		if (wantedEntities[1] == 1) {
			modifiedWordsList = ruleDate.containsDate(modifiedWordsList);
		}
		if (wantedEntities[2] == 1) {
			modifiedWordsList = ruleTime.containsTime(modifiedWordsList);
		}
		if (wantedEntities[4] == 1) {
			modifiedWordsList = rulePercentage.containsPercentage(modifiedWordsList);
		}
		if (wantedEntities[5] == 1) {
			modifiedWordsList = ruleQuantity.containsQuantity(modifiedWordsList);
		}
		if (wantedEntities[3] == 1) {
			modifiedWordsList = ruleMonetary.containsMonetary(modifiedWordsList);
		}
		
		//modifiedWordsList = ruleCategorizeNames.containsCategorizedNames(wordsList);
		
		if (wantedEntities[6] == 1) {
			modifiedWordsList = ruleLocationName.containsLocationName(modifiedWordsList);
		}
		if (wantedEntities[7] == 1) {
			modifiedWordsList = ruleCityName.containsCityName(modifiedWordsList);
		}
		if (wantedEntities[8] == 1) {
			modifiedWordsList = ruleCountryName.containsCountryName(modifiedWordsList);
		}
		if (wantedEntities[9] == 1) {
			modifiedWordsList = ruleContinentName.containsContinentName(modifiedWordsList);
		}
		if (wantedEntities[10] == 1) {
			modifiedWordsList = ruleOrganizationName.containsOrganizationName(modifiedWordsList);
		}
		for (Word word : modifiedWordsList) {
			System.out.println(word.getContent() + "--++" + word.getType());
		}
		if (wantedEntities[11] == 1) {
			modifiedWordsList = rulePersonName.containsPersonName(modifiedWordsList);
		}
		for (Word word : modifiedWordsList) {
			System.out.println(word.getContent() + "--++" + word.getType());
		}

		return modifiedWordsList;
		
	}
	
}
