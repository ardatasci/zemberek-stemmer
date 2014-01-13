package ResourceCollection.DigiturkEPG;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;

import KeywordAugmentation.Freebase.MQLAPI.FreebaseTVProgramRequest;
import KeywordExtraction.AnnotatedKeyword;

public class EPGStatisticalExtraction {
	Vector<String> annotatedKeywordTypes;
	Vector<AnnotatedKeyword> annotatedKeywords;
	int totalNumberOfProcessedKeyword;
	int totalNumberOfExtractedKeyword;
	Vector<ProgramInfo> collectedPrograms;
	Vector<ProgramInfo> processedPrograms;
	EPGWriter epgWriter;
	EPGReader epgReader;
	FileWriter file;	
	
	public EPGStatisticalExtraction()
	{
		this.annotatedKeywordTypes = new Vector<>();
		this.annotatedKeywords = new Vector<>();
		this.totalNumberOfProcessedKeyword = 0;
		this.totalNumberOfExtractedKeyword = 0;
		this.collectedPrograms = new Vector<>();
		this.processedPrograms = new Vector<>();
		epgWriter = new EPGWriter();
		epgReader = new EPGReader();
		
	}
	
	public Vector<String> getAnnotatedKeywordTypes()
	{
		return this.annotatedKeywordTypes;
	}
	

	public Vector<AnnotatedKeyword> getAnnotatedKeywords() {
		return annotatedKeywords;
	}

	public void setAnnotatedKeywords(Vector<AnnotatedKeyword> annotatedKeywords) {
		this.annotatedKeywords = annotatedKeywords;
	}

	public void collectProgramInfos(String channelName, int startTime) throws IOException, JSONException
	{
		this.epgWriter.saveProgramInfos(channelName, startTime);
		collectedPrograms = epgWriter.getProcessedPrograms();
	}
	
	public void processProgramInfos(String channelName) throws Exception
	{
		this.epgReader.readProgramInfos(channelName);
		//processedPrograms = epgReader.getProcessedPrograms();
		processedPrograms.addAll(epgReader.getProcessedPrograms());
		printProcessedPrograms(channelName + "_Programs");
		//printProcessedProgramKeywords();
	}
	
	public void printProcessedProgramDescriptions()
	{
		int length = this.processedPrograms.size();
		for(int i=0; i< length; i++)
		{
			System.out.println(this.processedPrograms.get(i).getDescription());
		}
	}
	
	public void printProcessedProgramKeywords()
	{
		int length = this.processedPrograms.size();
		int keywordsLength = 0;
		System.out.println("Keywords");
		System.out.println(length);
		for(int i=0; i<length; i++)
		{
			System.out.println(this.processedPrograms.get(i).getTitle());
			keywordsLength = this.processedPrograms.get(i).getAnnotatedKeywords().size();
			for(int j=0; j<keywordsLength;j++)
			{
				this.processedPrograms.get(i).getAnnotatedKeywords().get(j).printAnnotatedKeyword();
			}
		}
	}
	
	public void printProcessedProgramKeywordTypes()
	{
		int length = this.processedPrograms.size();
		int keywordsLength = 0;
		for(int i=0; i<length; i++)
		{
			keywordsLength = this.processedPrograms.get(i).getAnnotatedKeywords().size();
			for(int j=0; j<keywordsLength;j++)
			{
				System.out.println(this.processedPrograms.get(i).getTitle());
				System.out.println(this.processedPrograms.get(i).getAnnotatedKeywords().get(j).getKeywordTypes());
			}
		}
	}
	
	public void printProcessedPrograms(String fileName) throws IOException
	{
		file = new FileWriter("Programs/" + fileName, true);	
		for (ProgramInfo info : processedPrograms) {
			info.printProgramInfoToFile(file);
		}
		file.flush();
		file.close();	
	}
	
	

}
