package autoheroTests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import autoheroTestPages.ModuleSearchPage;

import resources.customReporter.CustomTestSummary;
import resources.customReporter.CustomTestExecutionReport;

@Listeners(value = { CustomTestSummary.class, CustomTestExecutionReport.class })
public class ModuleSearchTests {

	ModuleSearchPage objSearchPage;

	/**
	 * This method is expected to perform initialization of resources needed for
	 * execution of tests associated with Search Modules
	 * 
	 * @param None.
	 * @return None.
	 */
	@BeforeClass
	public void initModuleSearchTest() {
		objSearchPage = new ModuleSearchPage();
		objSearchPage.setupLogParameters();
		InitSuite.launchBrowser();
	}

	/**
	 * This is the first test associated with Search Module which filters the cars
	 * by registration date and sorts the filtered records in descending order by
	 * price.
	 * 
	 * @param registrationYear : The registration year by which cars are to be
	 *                         filtered. This value is provided in testng.xml.
	 * @return None.
	 */
	@Parameters({ "registrationYear" })
	@Test(priority = 1)
	public void filterByRegDateSortByPriceDesc(String year) {
		objSearchPage.filterYearAndSortPriceDesc(Integer.parseInt(year));
	}

	/**
	 * This is the second test associated with Search Module which depends on Test
	 * 1. It verifies that filtered records have registration year >= year provided
	 * while filtering.
	 * 
	 * @param registrationYear : The registration year by which cars are to be
	 *                         filtered. This value is provided in testng.xml.
	 * @return None.
	 */
	@Parameters({ "registrationYear" })
	@Test(priority = 2, dependsOnMethods = { "filterByRegDateSortByPriceDesc" })
	public void verifyCarsAreFilteredByRegDate(String year) {

		assertFalse(objSearchPage.verifyFilteredByYear(Integer.parseInt(year)),
				"Records filtered contain records with years less than:" + year);
	}

	/**
	 * This is the third test associated with Search Module. It verifies whether the
	 * records are sorted properly when sort by price in descending is applied.
	 * 
	 * @param None.
	 * @return None.
	 */
	@Test(priority = 3)
	public void verifyCarsFilteredAreSortedDesc() {

		assertTrue(objSearchPage.verifySortedByPrice(), "Records filtered are not sorted by price in descending order");

	}

	/**
	 * This method is expected to perform cleaning of resources used while testing
	 * search module.
	 * 
	 * @param None.
	 * @return None.
	 */
	@AfterClass
	public void closeAllFileHandler() {
		try {
			objSearchPage.closeFileHandlers();
		} catch (Exception e) {
			System.out.println(
					"[closeAllFileHandler] [ModuleSearchTests]: Ooops! Exception encountered while closing file handlers :"
							+ e.getMessage());
		}

	}
}
