package KeywordExtraction.NamedEntityTurkish;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UtilFunctions {
	
	public UtilFunctions(){
		
	}
	
	public void writeWordsToFile(String fileName, ArrayList<Word> wordList) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		
		for (Word word : wordList) {
			writer.println(word.clearedContent + "--type: " + word.type );
		}
		writer.close();
	}
}
