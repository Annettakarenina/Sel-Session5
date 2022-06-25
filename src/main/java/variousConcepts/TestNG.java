package variousConcepts;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNG {
	
	WebDriver driver;
	String browser;
	String url;
	
	By USER_NAME_FIELD =By.xpath("//input[@id='username']") ;
	By PASSWORD_FIELD=By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD=By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD=By.xpath("//*[@id=\'page-wrapper\']/div[2]/div/h2");
	By CUSTOMERS_MENU_FIELD=By.xpath("//span[text()='Customers']");
	By FULL_NAME_FIELD= By.xpath("//input[@id='account']");
	By ADD_CUSTOMER_FIELD=By.xpath("//a[text()='Add Customer']");
	By COMPANY_DROPDOWN_FIELD=By.xpath("//select[@id='cid']");
	By EMAIL_FIELD= By.xpath("//input[@id='email']");
	By COUNTRY_DROPDOWN_FIELD=By.xpath("//select[@id='country']");

	
	//TestData
	String userName="demo@techfios.com";
	String password="abc123";
	
	@BeforeClass
	public void readConfig() {
		//InputStream//BufferedReader//Scanner//FileReader
		
		try {//2 classes to read Properties file
			
			InputStream input=new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop=new Properties();
			prop.load(input);
			browser=prop.getProperty("browser");
			url=prop.getProperty("url");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void init() throws InterruptedException {
		
		
		if(browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}



		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	@Test(priority=1)
	public void loginTest() {
		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		//Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), DASHBOARD_HEADER_FIELD, "Dashboard is not visible");
	}
	@Test(priority=2)
	public void addCustomer() throws InterruptedException {
		loginTest();
		driver.findElement(CUSTOMERS_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_FIELD).click();
		Thread.sleep(5000);//replace with webDriver& make generic()
		boolean fullNameField=driver.findElement(FULL_NAME_FIELD).isDisplayed();
		Assert.assertTrue(fullNameField, "Add customer page is not available");
		
		driver.findElement(FULL_NAME_FIELD).sendKeys("Selenium"+generateRandomNumber(999));
		
		selectFromDropdown(COMPANY_DROPDOWN_FIELD, "Techfios");
		
		driver.findElement(EMAIL_FIELD).sendKeys("abc"+generateRandomNumber(999)+"@techfios.com");
		
		selectFromDropdown(COUNTRY_DROPDOWN_FIELD, "Afghanistan");
		
	}
	
	public int generateRandomNumber(int boundaryNumber) {
		
		Random rnd=new Random();
		int randomNumber=rnd.nextInt(boundaryNumber);
		return randomNumber;
		
		
	}

	public void selectFromDropdown(By byLocator, String visibleText) {
		
		Select sel1=new Select(driver.findElement(byLocator));
		sel1.selectByVisibleText(visibleText);
		
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
