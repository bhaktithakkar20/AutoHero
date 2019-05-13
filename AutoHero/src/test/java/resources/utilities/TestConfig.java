
package resources.utilities;

import java.util.Map;

import resources.config.Constants;

public class TestConfig {

	public String baseUrl;
	public String browser;
	private String configFilePath = "";
	public String emailBody;
	public String emailFrom;
	public String emailFromPass;
	public String emailSubject;
	public String emailTo;
	public String logDirectory;
	public String reportDirectory;

	public TestConfig(String testBed) {
		try {

			this.baseUrl = "";
			this.browser = "";
			this.logDirectory = "";
			this.reportDirectory = "";

			if (testBed.equals("")) {
				this.configFilePath = Constants.configPath + "/" + Constants.defaultTestBed + Constants.configFileName;
			} else {
				this.configFilePath = Constants.configPath + testBed + Constants.configFileName;
				System.out.println(this.configFilePath);
			}
		} catch (Exception e) {
			System.out.println("[TestConfig]: Ooops! Exception encountered in constructor :" + e.getMessage());
		}
	}

	/**
	 * This method reads the config file and initializes test parameters
	 * 
	 * @param None.
	 * @return None.
	 */
	public void readTestConfig() {
		try {
			Map<String, Object> jsonMap = JsonUtils.jsonToMap(this.configFilePath);

			this.baseUrl = jsonMap.get("baseUrl").toString();
			this.browser = jsonMap.get("browser").toString();
			this.logDirectory = jsonMap.get("logDirectory").toString();
			this.reportDirectory = jsonMap.get("reportDirectory").toString();
			this.emailFrom = jsonMap.get("emailFrom").toString();
			this.emailTo = jsonMap.get("emailTo").toString();
			this.emailSubject = jsonMap.get("emailSubject").toString();
			this.emailBody = jsonMap.get("emailBody").toString();
			this.emailFromPass = jsonMap.get("emailFromPass").toString();

		} catch (Exception e) {
			System.out.println(
					"[readTestConfig]: Ooops! Exception encountered while reading test config :" + e.getMessage());
		}
	}

}