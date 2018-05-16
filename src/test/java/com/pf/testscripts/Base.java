package com.pf.testscripts;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.pf.pagefactory.DriverHome;
import com.pf.pagefactory.Searchfortwobedroomsinmarina;
import com.pf.utilities.Xls_Reader;

/**
 * 
 * 
 * This is the base class for all the test suites,It executes before executing the TestSuite Classes
 * 
 * 
 */

public class Base {
	
	public String excep;
	public DriverHome driverhome;
	public Searchfortwobedroomsinmarina twobeds;
	

	public static String passMessage = null;
	public static String finalMessage = null;
	public static String skipMessage = null;
	public Xls_Reader xls;

	@BeforeTest(alwaysRun = true)
	@Parameters({ "browser" })
	public void setUp(String browser) throws Exception {
		try {
			Reporter.log("=====Browser Session Started=====", true);
			driverhome = new DriverHome(browser, "test");
			
		} catch (WebDriverException e) {
			excep=e.toString();
	    	Assert.fail(excep);
			System.out.println(e);
			
		}
	}

	
	@AfterTest

	public void close() throws Exception {
		try {
			Thread.sleep(5000);
		    //driverhome.emailreport();
			//driverhome.quitDriver();
			Reporter.log("=====Browser Session End=========", true);
		} catch (WebDriverException e) {
			excep=e.toString();
	    	Assert.fail(excep);
			System.out.println(e); 

		}
	}
	
}