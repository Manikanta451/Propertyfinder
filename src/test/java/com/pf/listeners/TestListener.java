package com.pf.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.pf.pagefactory.CommonBase;

public class TestListener implements ITestListener {
	
	public String browserName;
	public WebDriver driver;
	public String failedscreens = System.getProperty("user.dir") + "_Failed_Screens";
	public String excep;

	public void onTestFailure(ITestResult result) {

		try {
			System.out.println("***** Error " + result.getName()+ " test has failed *****");
			String methodName = result.getName().toString().trim();
			takeScreenShot(methodName);
			
		    } catch (Exception e) {
		    	excep=e.toString();
		    	Assert.fail(excep);
			e.printStackTrace();
		}
	}

	public void takeScreenShot(String methodName) throws Exception {
		// get the driver

		CommonBase commonbase = new CommonBase("", "");
		driver = commonbase.initDriver("", "");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				
		// The below method will save the screen shot in  drive with test method name
		
		try {
			FileUtils.copyFile(scrFile, new File(failedscreens + File.separator + methodName + "_" + browsername() + ".png"));	
			System.out.println("***Placed screen shot in " + failedscreens + " ***");		
			} catch (IOException e) {
				excep=e.toString();
		    	Assert.fail(excep);
		    	e.printStackTrace();
		  }
	}

	public String browsername() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		browserName = cap.getBrowserName().toLowerCase();
		return browserName;
	}

	public void onFinish(ITestContext context) {
		
		//System.out.println("The name of the testcase OnFinish :"      +context.getName());
	}

	public void onTestStart(ITestResult result) {
		
		//System.out.println("The name of the testcase Onstart is :"        +result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		
		//System.out.println("The name of the testcase Success is :"        +result.getName());
	}

	public void onTestSkipped(ITestResult result) {
		
		// System.out.println("The name of the testcase Skipped is :"       +result.getName());							    		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		//System.out.println("The Successpercentage of the testcase is :"     +result.getName());
	}

	public void onStart(ITestContext context) {
		
		//System.out.println("The name of the testcase Onstart is :"     +context.getName());
	}
}
