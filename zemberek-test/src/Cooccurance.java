import java.util.ArrayList;
import java.util.List;


public class Cooccurance {
	
	// x and y length of the matrix
	int length;
	List <TermPairs> term_pairs;

	
	void Coocurance() {
		length = 0;
		term_pairs = new ArrayList<TermPairs>();
	}
	
	public TermPairs getTermPair(String term1, String term2) {	
		for(int i = 0; i < this.term_pairs.size(); i++) {
			if(term_pairs.get(i).getFirstTerm() == term1 && term_pairs.get(i).getSecondTerm() == term2) {
				return term_pairs.get(i);
			}
		}
		return null;
	}

	public void newTermPair(String term1, String term2, String docID) {
		TermPairs  t_p = new TermPairs(term1, term2, docID);
		this.term_pairs.add(t_p);
	}
 
	
	
	public void addProgramProfile(ProgramProfile program_profile) {
		List<String> terms = program_profile.bag_of_words;
		String pID = program_profile.pID;
		int size = terms.size();
		
		
		
		if(size > 1 ) {
			for(int j = 0; j < size; j++) {
				for(int i = j+1; i < size; i++) {
					TermPairs tp = this.getTermPair(terms.get(j), terms.get(i));
					if(tp != null)
					{
						tp.addDocToTermPair(pID);
					} else {
						this.newTermPair(terms.get(j), terms.get(i), pID);
					}
					
				}
				
			}
		}
		
		
		
		
		
	}


}
