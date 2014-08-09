package KeywordExtraction.NamedEntityTurkish;

import java.util.ArrayList;

public class AnnotatedWordListCreator {

	private static AnnotatedWordListCreator instance = null;

	private ArrayList<Word> AnnotatedWordList = new ArrayList<Word>();
	private int totalSentenceSize = 0;
	private long totalWordCount = 0;

	private AnnotatedWordListCreator() {
		// Exists only to defeat instantiation.
	}

	public static AnnotatedWordListCreator getInstance() {
		if (instance == null) {
			instance = new AnnotatedWordListCreator();
		}
		return instance;
	}

	public void addAnnotatedWord(Word annotatedWord) {
		AnnotatedWordList.add(annotatedWord);
	}

	public ArrayList<Word> getAnnotatedWordList() {
		return AnnotatedWordList;
	}

	public int getTotalSentenceSize() {
		return totalSentenceSize;
	}

	public void setTotalSentenceSize(int totalSentenceSize) {
		this.totalSentenceSize = totalSentenceSize;
	}

	public long getTotalWordCount() {
		return totalWordCount;
	}

	public void setTotalWordCount(long totalWordCount) {
		this.totalWordCount = totalWordCount;
	}
	

}
