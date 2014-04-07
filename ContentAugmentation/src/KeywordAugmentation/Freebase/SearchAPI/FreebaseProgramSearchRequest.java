package KeywordAugmentation.Freebase.SearchAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class FreebaseProgramSearchRequest extends FreebaseSearchRequest {

	public FreebaseProgramSearchRequest() throws FileNotFoundException,
			IOException {
		this.generateInstance();
	}

	private JSONArray getProgramSearchResults(String programName)
			throws IOException, ParseException, JSONException {
		JSONArray result = new JSONArray();
		String query = programName;
		result = executeQuery(query);
		if (!result.isEmpty())
			System.out.println(result);

		return result;
	}

	public String getProgramMid(String programName, String type)
			throws IOException, ParseException, JSONException {
		String mid = null;
		String wantedType = null;
		if(type.equalsIgnoreCase("Dizi") || type.equalsIgnoreCase("Eğlence") || type.equalsIgnoreCase("Yaşam") || type.equalsIgnoreCase("Haber"))
			wantedType = "/tv/tv_program";
		else if(type.equalsIgnoreCase("Sinema"))
			wantedType = "/film/film";
		JSONArray result = getProgramSearchResults(programName);
		int resultSize = result.size();
		for (int i = 0; i < resultSize; i++) {
			JSONObject result_obj = (JSONObject) result.get(i);
			String name = (String) result_obj.get("name");
			if(name.equalsIgnoreCase(programName)){
				JSONObject outputObject = (JSONObject) result_obj.get("output");
				JSONObject typeObject = (JSONObject) outputObject.get("type");
				JSONArray typeArray = (JSONArray) typeObject.get("/type/object/type");
				for (int j = 0; j < typeArray.size(); j++) {
					JSONObject temp =  (JSONObject) typeArray.get(j);
					String extractdType = (String) temp.get("id");
					if(extractdType.equalsIgnoreCase(wantedType))
						mid = (String) result_obj.get("mid");
				}
			}
				
		}
		return mid;
	}
}
