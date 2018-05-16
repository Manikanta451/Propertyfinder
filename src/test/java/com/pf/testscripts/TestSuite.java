package com.pf.testscripts;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.pf.testscripts.ExcelSheetObjects;
import com.pf.testscripts.SetupEnvironment;
import com.pf.datainitialization.DataInt;
import com.pf.utilities.Util;
import com.pf.utilities.Xls_Reader;

/**
 * 
 * 
 * This is the Main Class for the Execution of the ELOP Super Admin test cases. 
 *
 */

public class TestSuite extends Base {
	
	public static final Logger LOG = Logger.getLogger(TestSuite.class);
	String testDataPath = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\pf\\test\\data\\TestData.xlsx";
	public Xls_Reader xls = new Xls_Reader(testDataPath);
	public String excep;
    

	@Test(description = "Searchtwobedrooms", dataProvider="getInfo",priority = 1)
	public void Searchtwobedrooms(DataInt dataInt) throws Exception {
		try {
			twobeds=driverhome.gettwobeds();
			twobeds.searchTwoBebrooms(dataInt);
			//SetupEnvironment.createXLSReport(ExcelSheetObjects.KEYWORD_PASS, ExcelSheetObjects.LoginWithValidCredentials, "TestCases");
		} catch (Exception e) {
			//SetupEnvironment.createXLSReport(ExcelSheetObjects.KEYWORD_FAIL,ExcelSheetObjects.LoginWithValidCredentials, "TestCases");
			excep=e.toString();
			Assert.fail(excep);
			e.printStackTrace();
		}
					
	}
	
	@DataProvider
	public Iterator<Object[]> getInfo() {
			return Util.getInfo("Info", xls).iterator();
		}
	
	
	

	
}
