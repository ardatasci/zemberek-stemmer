import java.util.ArrayList;
import java.util.List;


public class TermPairs {
	
	String term1;
	String term2;
	List<String> docIDs;
	
	
	TermPairs() {
		this.term1 = "";
		this.term2 = "";
		this.docIDs = new ArrayList<String>();
	}
	
	TermPairs(String term1, String term2, String docID) {
		this.term1 = term1;
		this.term2 = term2;
		this.docIDs.add(docID);
	}
	public void addDocToTermPair(String docID) {
		this.docIDs.add(docID);
		
	}
	
	public String getFirstTerm() {
		return this.term1;
	}
	public String getSecondTerm() {
		return this.term2;
	}
	public List<String> getDocIDs() {
		return this.docIDs;
	}

}
