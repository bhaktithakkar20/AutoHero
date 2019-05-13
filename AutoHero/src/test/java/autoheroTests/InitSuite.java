package autoheroTests;

import org.testng.annotations.BeforeSuite;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.Parameters;

import resources.config.Constants;
import resources.utilities.EmailReport;
import resources.utilities.TestConfig;

import org.testng.annotations.AfterSuite;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InitSuite {

	public static WebDriver driver = null;
	public static TestConfig objConfig = null;

	public static DesiredCapabilities capabilities = null;
	String driverPath = Constants.driverDirectory;

	/**
	 * This method contains logic to initialize test environment , initialize
	 * web-driver, browser parameters.
	 * 
	 * @param testEnv : defines environment on which the tests are to be
	 *                executed.This value is fetched from textng.xml.
	 *                <parameter name="testEnv" value="envDefault" /> .
	 * @return Nothing.
	 */
	@BeforeSuite
	@Parameters({ "testEnv" })
	public void initialize(String suiteEnv) throws IOException {

		try {
			objConfig = new TestConfig(suiteEnv);

			objConfig.readTestConfig();
			if (objConfig.browser.equals("chrome") | objConfig.browser.equals("")) {
				driverPath = driverPath + Constants.driverChrome;
				System.setProperty("webdriver.chrome.driver", driverPath);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-popup-blocking");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			} else if (objConfig.browser.equals("firefox")) {
				driverPath = driverPath + Constants.driverFireFox;
				System.setProperty("webdriver.firefox.marionette", driverPath);
				// FirefoxOptions options = new FirefoxOptions();
			}
		} catch (Exception E) {
			System.out.println(
					"[InitSuite initialize]: Ooops! Exception encountered while intitializing test :" + E.getMessage());
		}
	}

	/**
	 * This method ensures closing of browser session
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	public static void launchBrowser() {
		try {
			if (objConfig.browser.equals("chrome") | objConfig.browser.equals("")) {
				driver = new ChromeDriver(capabilities);
			} else if (objConfig.browser.equals("firefox")) {
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Constants.timeoutExplicitWait, TimeUnit.SECONDS);
			driver.get(objConfig.baseUrl);

			WebDriverWait wait = new WebDriverWait(driver, Constants.timeoutExplicitWait);
			wait.until(ExpectedConditions.titleContains(Constants.siteTitle));

		} catch (Exception E) {
			System.out.println("[InitSuite launchBrowser]: Ooops! Exception encountered while launching browser :"
					+ E.getMessage());
		}
	}

	/**
	 * This method ensures closing of browser session
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	@AfterSuite
	public void TeardownTest() {
		try {
			EmailReport.sendEmailReport(objConfig.emailFrom, objConfig.emailFromPass, objConfig.emailTo,
					objConfig.emailSubject + Constants.tstamp, objConfig.emailBody);
			if ((driver != null) && !(driver.toString().contains("(null)"))) {
				InitSuite.driver.quit();
			}

		} catch (Exception e) {
			System.out.println("[InitSuite TeardownTest]: Ooops! Exception encountered while teardown of suite :"
					+ e.getMessage());
		}

	}

}
