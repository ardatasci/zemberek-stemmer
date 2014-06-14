package KeywordExtraction.NamedEntityTurkish.util;

import java.util.Comparator;

import KeywordExtraction.NamedEntityTurkish.Word;

public class WordComparable implements Comparator<Word>{
 
    @Override
    public int compare(Word w1, Word w2) {
        if(w1.getIndexInSentence() < w2.getIndexInSentence()){
            return -1;
        } else {
            return 1;
        }
    }


}
