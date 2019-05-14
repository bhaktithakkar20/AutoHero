package resources.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	public static String configFileName = "/config.json";
	public static int timeout = 20;
	public static String defaultTestBed = "envDefault";
	public static String driverChromeMac = "chromedriver";
	public static String driverChromeWin = "chromedriver.exe";
	public static String driverFireFox = "geckodriver";
	public static String driverDirectory = System.getProperty("user.dir") + "/src/test/java/resources/drivers/";
	public static String configPath = System.getProperty("user.dir") + "/src/test/java/resources/config/";
	public static String logFolderPath = System.getProperty("user.dir") + "/logs/";
	public static Integer timeoutExplicitWait = 30;
	public static Integer additionalWait3Sec = 3000; // in milliseconds
	public static Integer additionalWait8Sec = 8000; // in milliseconds
	public static String absolutePath = System.getProperty("testng.absolute");
	public static String tstamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
	public static String siteTitle = "Premium Gebrauchtwagen Angebote | AutoHero.com";
	public static String emailReportAttachmentPath = System.getProperty("user.dir")
			+ "/test-output/emailable-report.html";
	public static String moduleSearchRecordsFileName = "ModuleSearchPageRecords.txt";

}
