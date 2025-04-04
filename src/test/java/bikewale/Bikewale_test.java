package bikewale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baselibrary.Baselibrary;
import  com.bikewale.*;

import propertyutility.PropertyUtility;

public class Bikewale_test extends Baselibrary

{
	private static final Logger logger = LogManager.getLogger(Bikewale_test.class);
	private static final String BIKEWALE_URL_KEY = "Bikewale";
	private static final String SMOKE_GROUP = "smoke";
	private Bikewale_page Bikewale_page;
	private SoftAssert softAssert;

	@Parameters({ "url" })
	@BeforeTest
	public void launchBikewale(String url) {
		try {
			String expectedUrl = PropertyUtility.getreadproperty(BIKEWALE_URL_KEY);
			if (url.equalsIgnoreCase(expectedUrl)) {
				driver.get(expectedUrl);
				logger.info("Navigated to Bikewale URL: " + expectedUrl);

			} else {
				logger.error("Wrong URL used for testing. Expected: " + expectedUrl + ", Actual: " + url);

				driver.quit();
			}
			Bikewale_page = new Bikewale_page();
			softAssert = new SoftAssert();
		} catch (Exception e) {
			logger.error("Failed to launch Bikewale: " + e.getMessage());

			throw new RuntimeException("Failed to launch Bikewale", e);
		}
	}

	@Test(groups = SMOKE_GROUP)
	public void loginBikewale() {
		try {

			logger.info("Starting Bikewale login test");

			Bikewale_page.loginToBikewale();
			logger.info("Bikewale login test completed");

		} catch (Exception e) {
			logger.error("Bikewale login test failed: " + e.getMessage());

			softAssert.fail("Bikewale login test failed: " + e.getMessage());
		}
		softAssert.assertAll();
	}
}
