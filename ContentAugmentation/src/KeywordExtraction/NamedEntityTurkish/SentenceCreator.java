package KeywordExtraction.NamedEntityTurkish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import tr.edu.hacettepe.cs.minio.MinioReader;
import tr.edu.hacettepe.cs.minio.MinioWriter;


public class SentenceCreator {
	ArrayList<String> sentence;
	ArrayList<String> temp;
	char[] smallLetters = {'a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş', 't', 'u', 'ü', 'v', 'y', 'z'};
 	public SentenceCreator()
	{
		
	}
	
	public void createSentenceBySentenceText(String infile,String outfile) throws IOException
	{
		String line= "";
		StringTokenizer token;
		String fullText = "";
		String word = "";
		String sentenceToWrite = "";
		sentence = new ArrayList<String> ();
		MinioReader in = MinioReader.getFileReader(infile);
		MinioWriter out = MinioWriter.getFileWriter(outfile);
		boolean found = false;
		boolean containsSentence = false;
		while ( in.inputAvailable() )
		{
			line = in.readLine();
			fullText += line + " ";
		}
		
		token = new StringTokenizer(fullText);
		
		while (token.hasMoreTokens())
		{
			word = token.nextToken();
		
			sentence.add(word);
			
			if( word.substring( (word.length()-1) ).equals(".") || word.substring( (word.length()-1) ).equals(":") || word.substring( (word.length()-1) ).equals("?") || word.substring( (word.length()-1) ).equals("!"))
			{
				if(token.hasMoreTokens())
					word = token.nextToken();
				
				for (int i = 0; i < smallLetters.length; i++) {
					if( word.substring(0,1).equals(smallLetters[i]) )
					{
						found = true;
					}
				}
				
				if(!found)
				{
					int sentenceSize = sentence.size();
					for (int i = 0; i < sentenceSize; i++) {
						sentenceToWrite += sentence.remove(0) + " ";
						containsSentence = true;
					}
				}
				if(!sentenceToWrite.equals(""))
				{
					out.println(sentenceToWrite);
					//System.out.println(sentenceToWrite);
				}
				sentence.add(word);
				sentenceToWrite = "";
			}
			
		}	

		if (!containsSentence) {
			out.println(fullText);
			
		}
		in.close();
		out.close();
	
	}
}
