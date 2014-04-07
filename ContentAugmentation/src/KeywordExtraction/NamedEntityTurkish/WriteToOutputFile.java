package KeywordExtraction.NamedEntityTurkish;

import java.io.IOException;
import java.util.ArrayList;

public class WriteToOutputFile {
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	public WriteToOutputFile()
	{
		
	}
	public void writeToFile(ArrayList modifiedWordsList, String outputType) throws IOException
	{
		this.modifiedWordsList = modifiedWordsList;
		
		if (outputType.equals("TXT"))
		{
			TXTOutputWriter TXTOutputWriter = new TXTOutputWriter();
			TXTOutputWriter.writeToFile(modifiedWordsList);
		}	
	}
	
	public void deleteContent(String outputType) throws IOException
	{
		
		if (outputType.equals("TXT"))
		{
			TXTOutputWriter txtOutputWriter = new TXTOutputWriter();
			txtOutputWriter.deleteContent();
		}		
	}
}
