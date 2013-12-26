package ResourceCollection.DigiturkEPG;
import java.io.FileReader;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import KeywordExtraction.AnnotatedKeyword;
import KeywordExtraction.DBPediaSpotlight;

public class EPGReader {
	
	
	Vector<ProgramInfo> processedPrograms;
	
	public EPGReader()
	{
		
		this.processedPrograms = new Vector<>();
	}
	
	
	
	public Vector<ProgramInfo> getProcessedPrograms() {
		return processedPrograms;
	}


	public void readProgramInfos(String channelName) throws Exception
	{
		String channelFileName = channelName + ".json";
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(channelFileName));
		System.out.println(channelName);
		JSONArray jsonArray = (JSONArray) obj;
		int length = jsonArray.size();
		for(int i=0; i<length;i++)
		{
			JSONObject first_obj = (JSONObject) ( jsonArray.get(i)); 
			JSONObject second_obj = (JSONObject) first_obj.get("broadcast");
			//System.out.println(first_obj);
			//System.out.println((String)second_obj.get("sDesc"));
			ProgramInfo programInfo = new ProgramInfo();
			programInfo.setProgramID((String)second_obj.get("program_id"));
			programInfo.setTimeOfDay((long)second_obj.get("pStart"));
			programInfo.setTitle((String)second_obj.get("pName"));
			programInfo.setGenre((String)second_obj.get("pGenreStr"));
			programInfo.setDescription((String)second_obj.get("sDesc"));
			programInfo.setChannelName(channelName);
			programInfo.extractKeywordsFromDescription();
			programInfo.collectProgramActors();
			if(!this.isIncludeProgram(programInfo))
				this.processedPrograms.add(programInfo);
			
		}
		//Vector<AnnotatedKeyword> allKeywords = annotatedKeywords;
		//for (AnnotatedKeyword annotatedKeyword : allKeywords) {
		//	annotatedKeyword.printAnnotatedKeyword();
		//}
		
	}
	
	private boolean isIncludeProgram(ProgramInfo programInfo)
	{
		for (ProgramInfo program : processedPrograms) {
			if(program.getTitle().equalsIgnoreCase(programInfo.getTitle()))
				return true;
		}
		return false;
	}
	

}
