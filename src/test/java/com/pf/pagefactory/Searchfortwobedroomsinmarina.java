package com.pf.pagefactory;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.pf.datainitialization.DataInt;

public class Searchfortwobedroomsinmarina extends CommonBase{
	
	public Searchfortwobedroomsinmarina(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.XPATH, using = "/html/body/main/div[1]/div[1]/div[1]/div/div/form/div/div[1]/div[2]/div[1]/div/div[1]/input")
	public static WebElement SearchCity;
										
	/*@FindBy(how = How.CLASS_NAME, using = "dropdown_item ")
	public static WebElement Properttype;*/
	
	//WebElement Properttype = driver.findElement(By.cssSelector(".searchproperty_type"));
	boolean Properttype =driver.findElements(By.xpath("//div[@class='searchproperty_column searchproperty_type']/div[@class='dropdown dropdown-height1']")).get(0).getText().equals("Apartment");
	
	public void searchTwoBebrooms(DataInt dataInt) throws Exception {
		
			waitForSeconds(4);
			SearchCity.click();
			waitForSeconds(1);
			SearchCity.sendKeys(dataInt.getCity());
			selectOptionWithText(dataInt.getCity());
			waitForSeconds(1);
			/*Select dd1 = new Select(Properttype);	
			dd1.selectByVisibleText("Apartment");
*/		
	  }	
	
	
}
