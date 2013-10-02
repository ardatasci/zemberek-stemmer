import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ProgramProfiler {
	
	public void printProfile(String channelName, String programId, String content) {
		try {		
			File file = new File("/tmp/" + channelName + "_" + programId + ".json");	
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			System.out.println("Written");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
