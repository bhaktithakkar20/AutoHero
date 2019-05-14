package autoheroTestPages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import autoheroTests.InitSuite;
import resources.config.Constants;
import resources.utilities.FileOperations;
import resources.utilities.Ordering;
import resources.utilities.LoggerUtils;

public class ModuleSearchPage {

	// locators used
	String locatorFirstRegistration = "//*[@id=\"app\"]//following-sibling::div[@data-qa-selector='filter-year']//span[text()='Erstzulassung ab']";
	String locatorFirstRegistrationOptions = "//*[@id=\"app\"]//following-sibling::select[@name='yearRange.min']";
	String locatorFilterCompleted = "//*[@id=\"app\"]//following-sibling::div[@data-qa-selector='results-amount']";
	String locatorSortRecords = "//*[@id=\"app\"]//following-sibling::div[@class='sort___MeITR']//following-sibling::select[@name='sort']";
	String locatorRecordsPerPage = "//*[@id=\"app\"]//following-sibling::select[@name='pageSize']";
	String locatorList = "//*[@id=\"app\"]//ancestor::div[@data-qa-selector='results-found']//div[@class='item___T1IPF']";
	String locatorDisplayList = "//*[@id=\"app\"]//following-sibling::div[@data-qa-selector='top-options']//label[@data-qa-selector-type='LIST']";
	String locatorDate = "//*[@id='app']//ancestor::div[@data-qa-selector='results-found']//div[@class='item___T1IPF']//li[1]";
	String locatorAmount = "//*[@id='app']//ancestor::div[@data-qa-selector='results-found']//div[@class='item___T1IPF']//div[@data-qa-selector='price']";
	String locatorNext = "//*[@id=\"app\"]//span[@aria-label='Next']";

	String filteredRecordsDumpFile = Constants.logFolderPath + Constants.tstamp + "/"
			+ Constants.moduleSearchRecordsFileName;
	List<Integer> yearsOfFilteredRecords = null;
	List<Double> pricesOfFilteredRecords = null;

	public static Logger instanceLogger = null;
	public static FileHandler logFileHandler = null;
	public static String currentPackage = "";
	public static String currentClass = "";

	/**
	 * This method contains logic to initialize and enable logging for this class.
	 * 
	 * @param None.
	 * @return Nothing.
	 */
	public void setupLogParameters() {
		try {
			String temp = "";
			String logFileName = "";

			// set current package
			temp = this.getClass().getPackage().toString();
			if (temp.contains(".")) {
				currentPackage = temp.substring(temp.lastIndexOf(".") + 1, temp.length());
			} else {
				currentPackage = temp.substring(temp.lastIndexOf(" ") + 1, temp.length());
			}

			// set current class
			temp = this.getClass().toString();
			currentClass = temp.substring(temp.lastIndexOf(" ") + 1, temp.length());

			// set logfilepath
			logFileName = Constants.logFolderPath + Constants.tstamp + "/"
					+ this.getClass().getCanonicalName().toString() + ".log";

			// set logger for this class.
			LoggerUtils.setupLogger(logFileName, currentPackage, currentClass);

		} catch (Exception e) {
			System.out.println("Oops! Encountered Exception in setupLogParameters! " + e.getMessage());
		}
	}

	/**
	 * This method contains logic to verify that all the records displayed after
	 * filtering by registration year have registration years greater than or equal
	 * to the year provided while filtering.
	 * 
	 * @param Year: Registration year.
	 * @return Boolean : True Or False indicating whether records were filtered
	 *         properly or not.
	 */
	public Boolean verifyFilteredByYear(Integer year) {
		try {
			if ((Ordering.isListHavingSmallerValue(year, this.yearsOfFilteredRecords)) == true) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in verifyFilteredByYear! " + e.getMessage());
		}
		return false;

	}

	/**
	 * This method contains logic to verify that all the records displayed after
	 * sorting by price in descending order and sorted appropriately.
	 * 
	 * @param None
	 * @return Boolean : True Or False indicating whether records were sorted
	 *         properly or not.
	 */
	public Boolean verifySortedByPrice() {
		try {
			boolean isSorted = Ordering.isListSortedDescending(this.pricesOfFilteredRecords);
			if (isSorted) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in verifySortedByPrice!" + e.getMessage());
		}
		return false;
	}

	/**
	 * This method contains logic to filterByRegDate all the records having reg date
	 * >= given year
	 * 
	 * @param Year : Registration year used for filtering.
	 * @return None
	 */
	public void filterByRegDate(Integer year) {
		try {
			// Click on the element available to filter by first registration year and Wait
			// for 30 seconds to obtain the expanded view
			InitSuite.driver.findElement(By.xpath(locatorFirstRegistration)).click();
			WebDriverWait wait = new WebDriverWait(InitSuite.driver, Constants.timeoutExplicitWait);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorFirstRegistrationOptions)));

			// Click on dropdown to select registration year and select the registration
			// year from dropdown as provided in test input
			InitSuite.driver.findElement(By.xpath(locatorFirstRegistrationOptions)).click();
			Select selectElementYear = new Select(
					InitSuite.driver.findElement(By.xpath(locatorFirstRegistrationOptions)));
			selectElementYear.selectByVisibleText(year.toString());

			/*
			 * Wait for 30 seconds for filter to get applied and page to load completely.
			 * Verify that loading is completed by the label which mentions number of
			 * records filtered once loading is completed. While loading is in progress ,
			 * this label shows "loading..." and once completed it shows no of records
			 * available.
			 */
			WebDriverWait waitForFiltering = new WebDriverWait(InitSuite.driver, Constants.timeoutExplicitWait);
			waitForFiltering.until(
					ExpectedConditions.textMatches(By.xpath(locatorFilterCompleted), Pattern.compile(".*Treffer.*")));
			Thread.sleep(Constants.additionalWait3Sec);

			instanceLogger.info(
					"[filterYearAndSortPriceDesc]:Filter By registration year " + year + " completed successfully!");

		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in filterByRegDate!" + e.getMessage());
		}
	}

	/**
	 * This method contains logic to change the number of records displayed per page
	 * to maximum i.e.72 to reduce the number of traversals needed.
	 * 
	 * @param None
	 * @return None
	 */
	public void increaseNumberOfRecordsPerPage() {
		try {
			/*
			 * To reduce the number of iterations to get all the filtered records , we are
			 * increasing the number of records displayed per page to 72. This will reduce
			 * number of autoheroTestPages we need to iterate.
			 */
			Select selectElementRecordsPerPage = new Select(
					InitSuite.driver.findElement(By.xpath(locatorRecordsPerPage)));
			selectElementRecordsPerPage.selectByIndex(2);
			Thread.sleep(Constants.additionalWait3Sec);

			// This step is added to ensure that the updated value of number of records per
			// page is applied successfully.
			InitSuite.driver.findElement(By.xpath(locatorDisplayList)).click();
			Thread.sleep(Constants.additionalWait8Sec);

			instanceLogger.info("[filterYearAndSortPriceDesc]:Number of records per page changed successfully to = "
					+ selectElementRecordsPerPage.getFirstSelectedOption().getText());

		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in increaseNumberOfRecordsPerPage!" + e.getMessage());
		}
	}

	/**
	 * This method contains logic sort the filtered records by price in descending
	 * order
	 * 
	 * @param None
	 * @return None
	 */
	public void sortBypriceDesc() {
		try {
			// This step sorts the filtered records by price in descending order.
			Select selectElementSortBy = new Select(InitSuite.driver.findElement(By.xpath(locatorSortRecords)));
			selectElementSortBy.selectByIndex(2);

			/*
			 * Verify that after sorting page loading is completed using the label which
			 * mentions number of records filtered once loading is completed. While loading
			 * is in progress , this label shows "loading..." and once completed it shows no
			 * of records available.
			 */
			WebDriverWait waitForSorting = new WebDriverWait(InitSuite.driver, Constants.timeoutExplicitWait);
			waitForSorting.until(
					ExpectedConditions.textMatches(By.xpath(locatorFilterCompleted), Pattern.compile(".*Treffer.*")));
			Thread.sleep(Constants.additionalWait8Sec);

			instanceLogger
					.info("[filterYearAndSortPriceDesc]:Sorting by Price in Descending order completed successfully!");
		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in sortBypriceDesc!" + e.getMessage());
		}
	}

	/**
	 * This method contains logic to navigate to next page.
	 * 
	 * @param None
	 * @return None
	 */
	public void navigateToNextPage() {
		try {
			// Click on "Next" button on page navigator to move to next page.
			WebDriverWait waitForPageNavigator = new WebDriverWait(InitSuite.driver, Constants.timeoutExplicitWait);
			waitForPageNavigator.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorNext)));
			InitSuite.driver.findElement(By.xpath(locatorNext)).click();

			// Wait for 8 seconds to ensure redirecting to next page has completed.
			Thread.sleep(Constants.additionalWait8Sec);
		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in navigateToNextPage!" + e.getMessage());
		}
	}

	/**
	 * This method contains logic to scroll the browser down to bottom of page.
	 * 
	 * @param None
	 * @return None
	 */
	public void browserScrollToBottom() {
		try {
			/*
			 * Once records from current page are read, for moving to next page we need to
			 * scroll down to the end of page to get access to page navigator
			 */
			((JavascriptExecutor) InitSuite.driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			// Wait for 3 seconds for page to scroll down completely.
			Thread.sleep(Constants.additionalWait3Sec);
		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in browserScrollToBottom!" + e.getMessage());
		}

	}

	/**
	 * This method contains logic to filter by registration date -> sort by price in
	 * descending order. 
	 * Steps followed are: 
	 * 1.)Expand the filter by registration element.
	 * 2.)Select the registration year from drop-down from expanded view.
	 * 3.)Change the number of records displayed per page to 72 to reduce number of
	 * iterations while fetching data to be verified. 
	 * 4.)Sort by price in descending order.
	 * 5.)Collect all the data required for verification i.e. registration
	 * year and price for all filtered records.
	 * 
	 * @param year : Registration year used for filtering.
	 * @return Nothing.
	 */
	public void filterYearAndSortPriceDesc(Integer year) {

		FileOperations foObject = new FileOperations(filteredRecordsDumpFile);

		int numOfFilteredRecords = 0;
		String labelTotalFilteredRecords = "", labelForNumberOfRecordsAvailable = "Treffer";

		this.yearsOfFilteredRecords = new ArrayList<Integer>();
		this.pricesOfFilteredRecords = new ArrayList<Double>();
		int totalPages = 0, pageCounter = 1, numberOfRecordsPerPage = 72;

		try {
			// filter cars having registration date >= 2015
			filterByRegDate(year);
			increaseNumberOfRecordsPerPage();
			sortBypriceDesc();

			// Get total number of records available after filters are applied.
			labelTotalFilteredRecords = InitSuite.driver.findElement(By.xpath(locatorFilterCompleted)).getText();
			numOfFilteredRecords = Integer
					.parseInt(labelTotalFilteredRecords.replaceAll(labelForNumberOfRecordsAvailable, "").trim());
			instanceLogger.info(
					"[filterYearAndSortPriceDesc]:Total number of records after filtering are:" + numOfFilteredRecords);

			// Calculate number of total pages need to be traversed to fetch details of all
			// filtered records.
			totalPages = Ordering.getNumberOfPagesToBeTraversed(numOfFilteredRecords, numberOfRecordsPerPage);
			instanceLogger
					.info("[filterYearAndSortPriceDesc]: Total number pages having filtered data after filtering are : "
							+ totalPages);

			do {

				// Get Registration dates and prices for all filtered records present on current
				// page.
				List<WebElement> dateElements = InitSuite.driver.findElements(By.xpath(locatorDate));
				List<WebElement> amountElements = InitSuite.driver.findElements(By.xpath(locatorAmount));

				// Loop to extract values for date and price from list elements available on
				// page.
				for (int i = 0; i < dateElements.size(); i++) {
					String dateVal = dateElements.get(i).getText().substring(5).trim();
					String amountVal = amountElements.get(i).getText().replaceAll("€", "").trim();

					this.yearsOfFilteredRecords.add(Integer.parseInt(dateVal));
					this.pricesOfFilteredRecords.add(Double.parseDouble(amountVal));

					foObject.writeToFile(dateVal + " " + amountVal + "\n");
				}

				// scroll browser to bottom to get access to page navigator.
				browserScrollToBottom();

				if (pageCounter != totalPages) {
					navigateToNextPage();
				}
				pageCounter++;
			} while (pageCounter <= totalPages);

			foObject.closeFileHandle();

		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in filterYearAndSortPriceDesc! " + e.getMessage());
		}
	}

	/**
	 * This method contains logic to set the instance logger for this class
	 * 
	 * @param Logger : logger object
	 * @return None
	 */
	public static void setInstanceLogger(Logger instanceLogger) {
		ModuleSearchPage.instanceLogger = instanceLogger;
	}

	/**
	 * This method contains logic to set the filehandler used for logging for this
	 * class
	 * 
	 * @param FileHandler : filehandler object
	 * @return None
	 */
	public static void setLogFileHandler(FileHandler logFileHandler) {
		ModuleSearchPage.logFileHandler = logFileHandler;
	}

	/**
	 * This method contains logic to close all open file handlers used for logging.
	 * 
	 * @param None
	 * @return None
	 */
	public void closeFileHandlers() {
		try {
			for (Handler h : instanceLogger.getHandlers()) {
				h.close();
			}
			logFileHandler.close();
		} catch (Exception e) {
			instanceLogger.severe("Oops! Encountered Exception in closeFileHandlers! " + e.getMessage());
		}

	}

}
