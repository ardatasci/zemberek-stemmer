package KeywordExtraction.NamedEntityTurkish.RuleCreator;

import java.util.ArrayList;

import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

public abstract class Rule {
	protected AnnotatedWordListCreator annotatedWordListCreator;
	protected Zemberek zemberek;
	protected CozumlemeSeviyesi strateji;
	
	public Rule(){
		annotatedWordListCreator = AnnotatedWordListCreator.getInstance();
		zemberek = new Zemberek(new TurkiyeTurkcesi());
		strateji=CozumlemeSeviyesi.TUM_KOK_VE_EKLER;
	}
	
	abstract void findEntitiesInDictionary(ArrayList<Word> wordsList, ArrayList<String> entities, WordType entityType, int sentenceNumber);
}
