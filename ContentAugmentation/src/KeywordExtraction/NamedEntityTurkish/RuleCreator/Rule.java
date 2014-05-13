package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public abstract class Rule {
	protected AnnotatedWordListCreator annotatedWordListCreator;
	
	public Rule(){
		annotatedWordListCreator = AnnotatedWordListCreator.getInstance();
	}
	
	abstract void findEntitiesInDictionary(ArrayList<Word> wordsList, ArrayList<String> entities, WordType entityType, int sentenceNumber);
}
