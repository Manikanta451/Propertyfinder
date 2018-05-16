package com.pf.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.pf.utilities.PropertiesFileReader;

/**
 * 
 * This Class is to initialize the all pages with using Pagefactory
 * 
 */

public class DriverHome extends CommonBase {
	
	public static final String prod_url = PropertiesFileReader.readvalueOfKey("app.url");
			

	public DriverHome(WebDriver driver) {
		super(driver);

	}

	public DriverHome(String browser, String text) {
		super(prod_url, browser);
	}

	public DriverHome(String url) {
		super(url, "test");

	}
	
	public Searchfortwobedroomsinmarina gettwobeds(){
		return PageFactory.initElements(driver, Searchfortwobedroomsinmarina.class);
	}
	
	
	
}

