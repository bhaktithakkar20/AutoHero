package resources.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	/**
	 * This method contains logic to read the contents of a json file in a Map.
	 * 
	 * @param jsonFilePath : Path of json file whose contents are to be read.
	 * @return jsonMap: Map object which contains the contents of the file.
	 */
	public static Map<String, Object> jsonToMap(String jsonFilePath) {
		Map<String, Object> jsonMap = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			// read JSON from a file
			jsonMap = mapper.readValue(new File(jsonFilePath), new TypeReference<Map<String, Object>>() {
			});

		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return jsonMap;
	}

}