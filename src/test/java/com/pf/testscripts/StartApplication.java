package com.pf.testscripts;

import java.util.ArrayList;
import java.util.List;
import org.automationtesting.excelreport.Xl;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.TestNG;
import com.pf.pagefactory.CommonBase;

/**
 * 
 * 
 * This is the Main Class for the Execution of the ELOP Super Admin Project and excution start from here. 
 *
 */

public class StartApplication extends CommonBase{

	public static String excep;
	
	public StartApplication(WebDriver driver) {
		super(driver);
	}

	public static void main (String[] args) throws Exception {
		
	          try {
	        	  TestNG runner=new TestNG();
	        	  List<String> suitefiles=new ArrayList<String>();
	        	  suitefiles.add("E://Seleniumeclipsewoekspace//PropertyFInder//TestNG.xml");
	        	  runner.setTestSuites(suitefiles);
	        	  runner.run();
	        	  /*Xl.generateReport("TestReport.xlsx");
	        	  emailreport();*/
		    
	           } catch (Exception e) {                                                                                              
	        	   excep=e.toString();
	        	   Assert.fail(excep);
	    	       e.printStackTrace();
	         }
		
	  }
	
}