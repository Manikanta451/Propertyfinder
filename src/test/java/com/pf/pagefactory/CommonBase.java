package com.pf.pagefactory;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.pf.utilities.PropertiesFileReader;
import com.pf.pagefactory.CommonBase;


/**
 * 
 * 
 * This Class is the base class for the entire script the driver is initialized here
 * 
 * 
 */

public class CommonBase {
	
	public String timeStamp;
	public String browserName;
	public String randomnumber;
	public String sikulipath = System.getProperty("user.dir");
	public String imagepath  = System.getProperty("user.dir");
	public static String reportpath1 = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "test-output"+ System.getProperty("file.separator")+ "TestReport.xlsx";
	public static String extentreportpath = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "ExtentReports"+ System.getProperty("file.separator")+ "ExtentReportResults.html";
	public static String reportpath3 = System.getProperty("user.dir")+ System.getProperty("file.separator")+ "src/main/java/com/pf/test/data"+ System.getProperty("file.separator")+ "TestCases.xlsx";
	public String screenshot = System.getProperty("user.dir") + "_Screenshot";
	public String str;
	public String snum;
	public String excep;
	public String order;
	public String today;
	public String randomemail;
	public static Properties CONFIG = null;
	public static WebDriver driver;
	public static WebElement webelement = null;
	public String Testcase;
	public WritableSheet writablesh;
	public WritableWorkbook workbookcopy;
	List<WebElement> noOfColumns;
	List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	// Expected Date, Month and Year
	int expMonth;
	int expYear;
	String expDate = null;
	// Calendar Month and Year
    String calMonth = null;
    String calYear = null;
    boolean dateNotFound;

	
	// CommonBase File
	
	public WebDriverWait dwait;
	String[] dialog;
	protected String url = null;
	protected String browser;
	public final int elementTimeOut = Integer.parseInt(PropertiesFileReader.readvalueOfKey("element.time.out"));
	public int windowTimeOut = Integer.parseInt(PropertiesFileReader.readvalueOfKey("window.time.out"));
	public int pageLoadTimeOut = Integer.parseInt(PropertiesFileReader.readvalueOfKey("page.Load.TimeOut"))	;
	public static final Logger LOG = Logger.getLogger(CommonBase.class);

	@SuppressWarnings("static-access")//to suppress warnings relative to incorrect static access
	
				protected CommonBase(WebDriver driver) {
					if (this.driver == null) {
						this.driver = driver;
					}
					dwait = new WebDriverWait(driver, 20);
				}
			
			
				public CommonBase(String url, String browser) {
			
					createWebDriver(url, browser);
			
				}
				
			
				public void createWebDriver(String url, String browser) {
			
					try {
						this.url = url;
						driver = initDriver(url, browser);
						driver.manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
								
					} catch (Exception exec) {
			
					}
				}
				
			
				public WebDriver initDriver(String url, String browser) throws Exception {
					
					if (browser.equalsIgnoreCase("ie")|| browser.equalsIgnoreCase("internet explorer")) {
						// Create the DesiredCapability object of InternetExplorer
						DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
						// Setting this capability will make your tests unstable and hard to debug.
						capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
						capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						//This will move the mouse pointer to the location where the operation is being performed on screen.
						capabilities.setCapability("requireWindowFocus", true);
						capabilities.setCapability("enablePersistentHover", false);
						
						System.setProperty(
								"webdriver.ie.driver",
								System.getProperty("user.dir")
										+ System.getProperty("file.separator")
										+ "BrowserDrivers"
										+ System.getProperty("file.separator")
										+ "IEDriverServer.exe");
						driver = new InternetExplorerDriver(capabilities);
						//driver.manage().window().setSize(new Dimension(1024,768));
						driver.get(url);
			
					} else if (browser.equalsIgnoreCase("firefox")||browser.equalsIgnoreCase("mozilla")||browser.equalsIgnoreCase("mozilla firefox")) {
						
						/*DesiredCapabilities capabilities=DesiredCapabilities.firefox();
						capabilities.setCapability("marionette", false);
						System.setProperty(
								"webdriver.gecko.driver",
								System.getProperty("user.dir")
										+ System.getProperty("file.separator")
										+ "BrowserDrivers"
										+ System.getProperty("file.separator")
										+ "geckodriver.exe");*/
						
						driver = new FirefoxDriver(FirefoxDriverProfile());
						driver.get(url);
			
					} else if (browser.equalsIgnoreCase("chrome")||browser.equalsIgnoreCase("google chrome")) {
						// Set logging preference In Google Chrome browser capability to log browser errors.
						LoggingPreferences pref = new LoggingPreferences();
						pref.enable(LogType.BROWSER, Level.ALL);
						Map<String, Object> prefs = new HashMap<String, Object>();	
						prefs.put("profile.default_content_setting_values.plugins", 1);
						prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
						prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
						    // Enable Flash for this site
						prefs.put("PluginsAllowedForUrls", "http://elop.thinkebiznow.com");						   
						prefs.put("profile.default_content_settings.popups", 0);
						prefs.put("profile.default_content_setting_values.notifications", 2);
						prefs.put("credentials_enable_service", false);
						prefs.put("password_manager_enabled", false);
						ChromeOptions options = new ChromeOptions();	 	               
						options.setExperimentalOption("prefs", prefs);
						options.addArguments("disable-infobars");
						options.addArguments("--disable-extensions");
						options.addArguments("--test-type");
						DesiredCapabilities cap = DesiredCapabilities.chrome () ;      
						cap.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
						cap.setCapability(ChromeOptions.CAPABILITY, options);
						cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
						cap.setCapability(CapabilityType.LOGGING_PREFS, pref);
						System.setProperty(
								"webdriver.chrome.driver",
								System.getProperty("user.dir")
										+ System.getProperty("file.separator")
										+ "BrowserDrivers"
										+ System.getProperty("file.separator")
										+ "chromedriver.exe");
						driver = new ChromeDriver(cap);
						driver.get(url);
			
					}else if (browser.equalsIgnoreCase("safari")||browser.equalsIgnoreCase("apple safari")) {
						System.setProperty("webdriver.safari.driver",
								System.getProperty("user.dir")
								       + System.getProperty("file.separator")
								       + "BrowserDrivers"
								       + System.getProperty("file.separator")
								       +"SafariDriver.safariextz ");
						driver = new SafariDriver();
						driver.get(url);
						                
						
					}else if (browser.equalsIgnoreCase("microsoft edge")||browser.equalsIgnoreCase("edge")) {
						System.setProperty("webdriver.edge.driver",
								System.getProperty("user.dir")
								       + System.getProperty("file.separator")
								       + "BrowserDrivers"
								       + System.getProperty("file.separator")
								       +"MicrosoftWebDriver.exe ");
						
						//System.setProperty("webdriver.edge.driver",new File (serverpath).getAbsolutePath());
						driver = new EdgeDriver();
						driver.get(url);
						          	
					}/*else {
						throw new IllegalArgumentException("The Browser Type is Undefined");
					}*/
					driver.manage().deleteAllCookies();
			     	driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
			     	driver.manage().window().maximize();
					return driver;
				}
			
				
				public static FirefoxProfile FirefoxDriverProfile() throws Exception {
					
					FirefoxProfile profile = new FirefoxProfile();
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference("browser.download.manager.showWhenStarting",false);
					profile.setPreference("browser.download.dir",System.getProperty("user.dir") + "_Downloads");
					profile.setPreference("browser.helperApps.neverAsk.openFile","application/octet-stream;application/pdf,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");	
					profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream;application/pdf,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");				
					profile.setPreference("pdfjs.disabled", true);
					profile.setPreference("plugin.scan.Acrobat", "99.0");
					profile.setPreference("plugin.scan.plid.all", false);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
					profile.setPreference("browser.download.manager.focusWhenStarting",false);
					profile.setPreference("browser.download.manager.useWindow", false);
					profile.setPreference("browser.download.manager.showAlertOnComplete",false);
					profile.setPreference("browser.download.manager.closeWhenDone", false);
					profile.setPreference("webdriver.load.strategy", "unstable");
					/*profile.setPreference("javascript.enabled", true);
					profile.setPreference("dom.max_chrome_script_run_time", 0);
					profile.setPreference("dom.max_script_run_time", 0);
					profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
					profile.setPreference("startup.homepage_welcome_url.additional",  "about:blank");
					profile.setPreference("startup.homepage_welcome_url", "about:blank");*/
					profile.setAcceptUntrustedCertificates(true);
					profile.setAssumeUntrustedCertificateIssuer(false);
					return profile;
			
				}
			
				
				public void quitDriver() {
					driver.close();
				}
				
				
				public int switchToNewWindow(String sTitle) {
			
					String parentWindow = driver.getWindowHandle();		//To get the window handle of the current window
					Set<String> allWindows = driver.getWindowHandles(); //To get the window handle of all the current windows.
					for (String childWindows : allWindows) {
						if (!childWindows.equals(parentWindow)) {
							driver.switchTo().window(childWindows);
							break;
						}
					}
					return 0;
				}
			
				
				public void switchWindowUsingTitle(String title) {
			
					String handle = driver.getWindowHandle();
			
					for (int i = 1; i <= windowTimeOut; i++) {
						for (String windowHandler : driver.getWindowHandles()) {
							driver.switchTo().window(windowHandler);
							if (driver.getTitle().contains(title)) {
							return;
							}
						}
						if (i == windowTimeOut) {
							driver.switchTo().window(handle);
							throw new NoSuchElementException(
									"No window available with given title : " + title);
						}
						try {
							Thread.sleep(1000L);
						} catch (InterruptedException ie) {
			
							throw new RuntimeException("Exception occured in sleep method");
						}
					}
				}
				
			
				public void scrollPage() throws InterruptedException {
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
					waitForSeconds(3);
					((JavascriptExecutor) driver).executeScript("window.scrollBy(500,0)");
			
				}
			
				
				public void switchbackToMainWindow() {
			
					Set<String> windowIterator = driver.getWindowHandles();
					Object[] handles = windowIterator.toArray();
					driver.switchTo().window(handles[0].toString());
				}
			
				
				@SuppressWarnings("static-access")//to suppress warnings relative to incorrect static access
				public WebElement findElement(By by) throws Exception {
					WebElement element = null;
					for (int i = 1; i <= elementTimeOut; i++) {
						try {
							element = this.driver.findElement(by);
						} catch (WebDriverException e) {
							if (i == elementTimeOut) {
								throw e;
							}
			
							try {
								Thread.sleep(1000L);
							} catch (InterruptedException ie) {
			
								throw new RuntimeException(
										"Exception occured in sleep method");
							}
						}
					}
					if (element != null) {
						return element;
					} else {
						throw new WebDriverException("Cannot find element with "
								+ by.getClass());
					}
			
				}
				
				
				public static void waitForSeconds(int Sec){
					
					long start = System.currentTimeMillis();//returns the current time in milliseconds
					//System.out.println("stsrt Time "+start);
					long stop = start+Sec*1000;
					//System.out.println("Stop Time "+stop);
					while(System.currentTimeMillis()<stop){
						
					}		
				}
				
				
				public boolean isElementExist(WebElement elemetName) {
			
					boolean present = true;
					try {
						elemetName.isDisplayed();
						present = true;
			
					} catch (NoSuchElementException e) {
						present = false;
					}
			
					return present;
				}
			
				
				public static boolean hasTextContainsString(String actual, String expected) throws Exception {
						
								boolean result = false;
									if (actual.contains(expected)) {
											result = true;
											}
										return result;
							}
				
			
				public String getTitle() {
					return driver.getTitle();
				}
			
				
				public void clickButtoninputValueContaintext(String text) throws Exception {
					findElement(By.xpath("//input[@value='" + text + "']")).click();
				}
			
				
				public WebElement waitforElementToAppear(WebElement element) {
					WebDriverWait wait = new WebDriverWait(driver, 60);
					WebElement ele = wait.until(ExpectedConditions.visibilityOf(element));
					return ele;
				}
				
			
				public void mouseOver(WebElement parentElement, WebElement childElement)throws Exception {
						
					LOG.info("Inside the Mouse Over Method");
					Actions action = new Actions(driver);
					waitForSeconds(2);
					action.moveToElement(parentElement).perform();
					waitForSeconds(1);
					action.moveToElement(childElement).perform();
			
				}
			
				
				public boolean checkAlert() {
					try {
						str = driver.switchTo().alert().getText();
						driver.switchTo().alert().accept();
						return true;
			
					} catch (NoAlertPresentException Ex) {
			
					}
					return false;
			
				}
				
			
				public void browsername() {
			
					Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
					browserName = cap.getBrowserName().toLowerCase();
				/*	String os = cap.getPlatform().toString();
					System.out.println("Operating System "+os);
					String version = cap.getVersion().toString();
					System.out.println("Version is "+version);*/
			
				}
			
				
				public String gettimestamp() {
					timeStamp = new SimpleDateFormat("MM/dd/YYYY").format(new Date());
					LOG.info("TimeStamp" + timeStamp);
					
					return timeStamp;
				}
			
				
				public void robot() throws Exception {
					
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					robot.keyPress(KeyEvent.VK_DELETE);
					robot.keyRelease(KeyEvent.VK_DELETE);
				}
			
				
				public Boolean waitforElementTotype(WebElement con, String text) {
					WebDriverWait wait = new WebDriverWait(driver, 40);
					Boolean tr = wait.until(ExpectedConditions.textToBePresentInElement(
							con, text));
					return tr;
				}
			
				
				public Boolean switchtonewtab() {
					try {
						ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
						driver.switchTo().window(tabs2.get(1));
						return true;
					} catch (Exception e) {
						
					}
					return false;
				}
				
			
				public Boolean switchbacktotab() {
					try {
						ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
						driver.close();
						driver.switchTo().window(tabs2.get(0));
						return true;
						
					} catch (Exception e) {
						
					}
					return false;
			
				}
				
			
				public boolean getscreenshot() {
					try {
						File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						File browseFile = new File((screenshot + File.separator + getnum() + ".png"));
						FileUtils.moveFile(scrFile, browseFile);
						return true;
					} catch (Exception e) {
						
					}
					return false;
			
				}
			
				
				public String getnum() {
					order = driver.findElement(By.id("orderNumber")).getText();
					return order;
				}
				
					
				public boolean getFailedScreenshot() {
						try {
							File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							File browseFile = new File((screenshot + File.separator + gettimestamp() + ".png"));
							FileUtils.moveFile(scrFile, browseFile);
							return true;
						} catch (Exception e) {
							
						}
						return false;
			
					}
					
					
				public String getfuturedate() {
						   Date tomorrow = new Date(System.currentTimeMillis()+ (1000 * 60 * 60 * 24 * 7));
						   System.out.println(tomorrow);
						SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
						   String s = formatter.format(tomorrow);
						   System.out.println(s);
						return s;
			
						}
				
				
				public static void emailreport() throws Exception{		 
					 sendPDFReportByGMail("seleniumautomatonreports@gmail.com", "1111111!", "mani6747@gmail.com", "Propertyfinder Automation Report", "");
				 }	 
				  
				
				private static void sendPDFReportByGMail(String from, String pass, String to, String subject, String body) throws Exception {
					 System.out.println("Waiting for generating Testreports....");
					 System.setProperty("javax.net.ssl.trustStore", "C://Program Files//Java//jre1.8.0_101//lib//security//cacerts");
					 System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
					 Properties props = System.getProperties();		 
					 String host = "smtp.gmail.com";
				     props.put("mail.smtp.starttls.enable", "true");
				     props.put("mail.smtp.host", host);
				     props.put("mail.smtp.user", from);
				     props.put("mail.smtp.password", pass);
				     props.put("mail.smtp.auth", "true");	  
				     props.put("mail.smtp.socketFactory.port", "465");  
				     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
				     props.put("mail.smtp.auth", "true");  
				     props.put("mail.smtp.port", "465");  
					 Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
						 
							protected PasswordAuthentication getPasswordAuthentication() {
			
							return new PasswordAuthentication("from","pass");
			
							}
						});			 
					 MimeMessage message = new MimeMessage(session);		 
					 try {
						 //Set from address
						 message.setFrom(new InternetAddress(from));
						 message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
						 //Set subject
						 message.setSubject(subject);
						 message.setText(body);
						 BodyPart objMessageBodyPart = new MimeBodyPart();
						 objMessageBodyPart.setText("Hi,"
						 		+ "\n"
						 		+ "\n"
						 		+ "      Please Find The Attached Automation Report Files,please download and then open it!"
						 		+ "\n"
						 		+ "\n");
						 Multipart multipart = new MimeMultipart();
						 multipart.addBodyPart(objMessageBodyPart);
						 objMessageBodyPart = new MimeBodyPart();
						 addAttachment(multipart, reportpath1);
						 addAttachment(multipart, extentreportpath);
						 addAttachment(multipart, reportpath3);
						 message.setContent(multipart);
						 Transport transport = session.getTransport("smtp");
						 transport.connect(host, from, pass);
						 transport.sendMessage(message, message.getAllRecipients());
						 transport.close();		 
					}
					catch (AddressException ae) {		 
						ae.printStackTrace();		 
					} 
					catch (MessagingException me) {		 
						me.printStackTrace();		 
					}
					System.out.println("Testreports has been Sent Successfully....");
			}
				 
				 
				private static void addAttachment(Multipart multipart, String filename)throws Exception{
					    DataSource source = new FileDataSource(filename);
					    BodyPart messageBodyPart = new MimeBodyPart();        
					    messageBodyPart.setDataHandler(new DataHandler(source));
					    messageBodyPart.setFileName(filename);
					    multipart.addBodyPart(messageBodyPart);
					}
			
			
				public static void fileupload(String string) {
						
					    	StringSelection stringSelection = new StringSelection(string);////StringSelection is a class that can be used for copy and paste operations.
					    	Toolkit.getDefaultToolkit().getSystemClipboard() .setContents(stringSelection, null);//When you do a cut or copy of text in the operating system, the text is  stored in the clipboard 
					    	
					            
					    		try {
					    			Robot robot = new Robot();
					    			robot.delay(4000);
					    			robot.keyPress(KeyEvent.VK_CONTROL);//This is for pressing  ctrl button  of Keyboard
					    			robot.keyPress(KeyEvent.VK_V); //This is for pressing  v button of keyboard
					    			robot.keyRelease(KeyEvent.VK_V); //This is for releasing the ctrl button of KeyBoard
					    			robot.keyRelease(KeyEvent.VK_CONTROL); //This is for releasing V button of keyBoard
					    			robot.delay(2000);
					    			robot.keyPress(KeyEvent.VK_TAB);
					    			robot.keyRelease(KeyEvent.VK_TAB);
					    			robot.keyPress(KeyEvent.VK_ENTER); 
					    			robot.keyRelease(KeyEvent.VK_ENTER); 
						 
					    			} catch (AWTException e) {
					    				e.printStackTrace();
					    			}
								}
					
					
				public static void HighLightElement(WebElement element){
						
							JavascriptExecutor js=(JavascriptExecutor)driver; 
							js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid orange;');", element);
						 
								try {
									Thread.sleep(1000);
									} 
									catch (InterruptedException e) {
									}
									js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element); 
									}	
				
				
				public void slider(WebElement elementTodrag, int xOffset, int yOffset) {
						try {
							if (elementTodrag.isDisplayed()) {
								Actions move = new Actions(driver);
								Action action = (Action) move.dragAndDropBy(elementTodrag, xOffset, yOffset).build();
								Thread.sleep(2000);
								action.perform();
							} else {
								System.out.println("Element was not displayed to drag");
							}
						} catch (StaleElementReferenceException e) {
							System.out.println("Element with " + elementTodrag + "is not attached to the page document "	+ e.getStackTrace());
						} catch (NoSuchElementException e) {
							System.out.println("Element " + elementTodrag + " was not found in DOM " + e.getStackTrace());
						} catch (Exception e) {
							System.out.println("Unable to resize" + elementTodrag + " - "	+ e.getStackTrace());
						}
					}
			
				 
				public void  systemdateselect(){
							
							try {
								DateFormat dateformat = new SimpleDateFormat("d"); //date format
					            Date date = new Date();					
					            today = dateformat.format(date);   
					            WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div")); //find the calendar
					            List<WebElement> columns=dateWidget.findElements(By.tagName("td"));  
					            //comparing the text of cell with today's date and clicking it.
					            for (WebElement cell : columns)
					            {
					               if (cell.getText().equals(today))
					               {
					                  cell.click();
					                  break;
					               }
					            }
							} catch (Exception e) {
								
							}
							
						}
						
						
				public File captureElementSpecificScreenShot(WebElement element) throws IOException{
							
							try{
							 File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							 File browseFile = new File((screenshot + File.separator + gettimestamp() + ".png"));
							 FileUtils.moveFile(screen, browseFile);
							 BufferedImage img = ImageIO.read(screen);
							 int width = element.getSize().getWidth();
							 int height = element.getSize().getHeight();
							 Rectangle rect = new Rectangle(width, height);
							 Point p = element.getLocation();
							 BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width,rect.height);
							 ImageIO.write(dest, "png", screen);
							 return screen;
							 }catch (Exception e) {	
								}
							return null;
								
							   }
						
						
				public static void scrollTo(WebDriver driver, WebElement element) {
					   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);          
					    }
						
						
				public static enum ModeEmail {
							
							ALPHA, NUMERIC
						}
			
				
				public static String generateRandomStringemail(int length, ModeEmail alpha)throws Exception {
								
							StringBuffer buffer = new StringBuffer();
							String characters = "";
			
							switch (alpha) {
			
							case ALPHA:
								characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
								break;
			
							case NUMERIC:
								characters = "1234567890";
								break;
							}
			
							int charactersLength = characters.length();
			
							for (int i = 0; i < length; i++) {
								double index = Math.random() * charactersLength;
								buffer.append(characters.charAt((int) index));
							}
							return buffer.toString();
						}
			
				
				public String gettingEmailrandomly() throws Exception {
							
							for (int i = 0; i < 1; i++) {
								String alpha = CommonBase.generateRandomStringemail(6, CommonBase.ModeEmail.ALPHA);
								String numeric = CommonBase.generateRandomStringemail(2, CommonBase.ModeEmail.NUMERIC);
								//String alpha2 = CommonBase.generateRandomStringemail(1, CommonBase.ModeEmail.ALPHA);
								//String numeric1 = CommonBase.generateRandomStringemail(2,CommonBase.ModeEmail.NUMERIC);		
								//String alpha3 = CommonBase.generateRandomStringemail(1, CommonBase.ModeEmail.ALPHA);
								//String numeric2 = CommonBase.generateRandomStringemail(3,CommonBase.ModeEmail.NUMERIC);
								//String alpha4 = CommonBase.generateRandomStringemail(1, CommonBase.ModeEmail.ALPHA);
								//randomemail = alpha.concat(numeric).concat(alpha2).concat(numeric1).concat(alpha3).concat(numeric2).concat(alpha4).concat("@gmail.com");
								randomemail=alpha.concat(numeric).concat("@gmail.com");
										
							}
							return randomemail;
						}			
				
			
				public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
							try {
								if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
									Actions action = new Actions(driver);
									action.dragAndDrop(sourceElement, destinationElement).build().perform();
								} else {
									System.out.println("Element was not displayed to drag");
								}
							} catch (StaleElementReferenceException e) {
								System.out.println("Element with " + sourceElement + "or" + destinationElement + "is not attached to the page document "
										+ e.getStackTrace());
							} catch (NoSuchElementException e) {
								System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM "+ e.getStackTrace());
							} catch (Exception e) {
								System.out.println("Error occurred while performing drag and drop operation "+ e.getStackTrace());
							}
						}
				
				
				public void GetJSErrosLog() {
						  // Capture all JSerrors and print In console.
						  LogEntries jserrors = driver.manage().logs().get(LogType.BROWSER);
						  for (LogEntry error : jserrors) {
						   System.out.println(error.getMessage());
						  }
						 }
					
							
				public static void futuredateselection(){
								
								try {
									DateFormat dateformat = new SimpleDateFormat("d"); //date format
						            Date date = new Date();					
						            String today = dateformat.format(date); 
						            //System.out.println("Today is :"+today);
						            int dateselectfuture=Integer.parseInt(today);
						            int future=dateselectfuture + 4;
						            String futuredate=String.valueOf(future);	
						            //System.out.println("Featrue date is :"+futuredate);

						            WebElement dateWidget = driver.findElement(By.id("ui-datepicker-div")); //find the calendar
						            List<WebElement> columns=dateWidget.findElements(By.tagName("td"));  
						            System.out.println(columns);
						            //comparing the text of cell with today's date and clicking it.
						            for (WebElement cell : columns)
						            {
						            	
						               if (cell.getText().equals(futuredate)){
						              
						                  cell.click();
						                 
						                  break;
						                
						               } 
						            }
						            
								} catch (Exception e) {
								}
								
							}
			 
				
				public String sevendigitrandomnumber()throws Exception{
						       
						         try {
						        	Random rand = new Random();
						     		int digit = rand.nextInt(3000000) + 3000000;
						     		String number=String.valueOf(digit);
						     		randomnumber=number;
								     } catch (Exception e) {
								    	 excep=e.toString();
								    	 Assert.fail(excep);
								   }
								return randomnumber;
								
						     
					  }
				
				public void expiryDate() throws InterruptedException{
					
					try {
						  dateNotFound = true;						 
						  //Set your expected date, month and year.  
						  expDate = "8";
						  expMonth= 4;
						  expYear = 2018;
						  
						  //This loop will be executed continuously till dateNotFound Is true.
						  while(dateNotFound){
						   //Retrieve current selected month name from date picker popup.
						   calMonth = driver.findElement(By.className("ui-datepicker-month")).getText();
						   System.out.println(calMonth);
						   //Retrieve current selected year name from date picker popup.
						   calYear = driver.findElement(By.className("ui-datepicker-year")).getText();
						   System.out.println(calYear);
						   //If current selected month and year are same as expected month and year then go Inside this condition.
						   if(monthList.indexOf(calMonth)+1 == expMonth && (expYear == Integer.parseInt(calYear))){						   
						    //Call selectDate function with date to select and set dateNotFound flag to false.
						    selectDate(expDate);
						    dateNotFound = false;
						    }
						   //If current selected month and year are less than expected month and year then go Inside this condition.
						   else if(monthList.indexOf(calMonth)+1 < expMonth && (expYear == Integer.parseInt(calYear)) || expYear > Integer.parseInt(calYear))
						   {
						    //Click on next button of date picker.
						    driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[2]/span")).click();
						   }
						   //If current selected month and year are greater than expected month and year then go Inside this condition.
						   else if(monthList.indexOf(calMonth)+1 > expMonth && (expYear == Integer.parseInt(calYear)) || expYear < Integer.parseInt(calYear))
						   {
						    //Click on previous button of date picker.
						    driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
						   }
						  }
						  
					} catch (Exception e) {
						    e.printStackTrace();
						}
					 } 
					 
				public void selectDate(String date){
					 WebElement datePicker = driver.findElement(By.id("ui-datepicker-div")); 
					  noOfColumns=datePicker.findElements(By.tagName("td"));

					  //Loop will rotate till expected date not found.
					  for (WebElement cell: noOfColumns){
					   //Select the date from date picker when condition match.
					   if (cell.getText().equals(date)){
					    cell.findElement(By.linkText(date)).click();
					    break;
					     }
					   }
					 } 		

				public void selectOptionWithText(String textToSelect) {
					try {
						System.out.println("Select value from auto");
						WebElement autoOptions = driver.findElement(By.className("autocomplete_dropdown"));
						dwait.until(ExpectedConditions.visibilityOf(autoOptions));

						List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("span"));
						for(WebElement option : optionsToSelect){
					        if(option.getText().equals(textToSelect)) {
					        	System.out.println("Trying to select: "+textToSelect);
					            option.click();
					            break;
					        }
					    }
						
					} catch (NoSuchElementException e) {
						System.out.println(e.getStackTrace());
					}
					catch (Exception e) {
						System.out.println(e.getStackTrace());
					}
				}
						 
			}
