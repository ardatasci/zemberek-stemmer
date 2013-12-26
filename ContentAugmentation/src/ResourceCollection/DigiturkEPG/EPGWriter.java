package ResourceCollection.DigiturkEPG;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

public class EPGWriter {
	
	DigiturkEPGRequest digiturkRequest;
	Vector<ProgramInfo> processedPrograms;
	
	public EPGWriter()
	{
		digiturkRequest = new DigiturkEPGRequest();
		processedPrograms = new Vector<>();
	}
	
	public Vector<ProgramInfo> getProcessedPrograms() {
		return processedPrograms;
	}


	public void saveProgramInfos(String channelName, int startTime) throws IOException, JSONException
	{
		JSONObject program;
		FileWriter file = new FileWriter(channelName + ".json", true);
		file.write("[");
		// New Class for Program
		//String channel = "show_tv";
		for(int i = 0; i < 30; i++) {
			ProgramInfo programInfo = new ProgramInfo();
			program = digiturkRequest.getProgramInfo(channelName, Integer.toString(startTime));
			programInfo.setProgramID(((JSONObject)program.get("broadcast")).getString("program_id"));
			programInfo.setTimeOfDay(((JSONObject)program.get("broadcast")).getInt("pStart"));
			programInfo.setTitle(((JSONObject)program.get("broadcast")).getString("pName"));
			programInfo.setGenre(((JSONObject)program.get("broadcast")).getString("pGenreStr"));
			programInfo.setChannelName(channelName);
			programInfo.setDescription(((JSONObject)program.get("broadcast")).getString("sDesc"));
			this.processedPrograms.add(programInfo);
			System.out.println(((JSONObject)program.get("broadcast")).getString("sDesc"));
			startTime = startTime - 86400;
			file.write(program.toString());
			if(i != 29)
				file.write(",");
			
	 
		}
		file.write("]");
		file.flush();
		file.close();
	}
}
