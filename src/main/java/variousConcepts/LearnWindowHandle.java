package variousConcepts;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LearnWindowHandle {
	WebDriver driver;

	@Before
	public void init() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www.yahoo.com/");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test
	public void windowHandle() throws InterruptedException {
		
		System.out.println(driver.getTitle());
		String handle = driver.getWindowHandle();
		System.out.println(handle);
		
		//after clicking yahoo's search bar, entering text&clicking search WHandle doesn't change

		driver.findElement(By.xpath("//input[@id='ybar-sbq']")).sendKeys("xpath");
		driver.findElement(By.xpath("//input[@id='ybar-search']")).click();
		Thread.sleep(3000);
		
		System.out.println(driver.getTitle());		
		String handle1 = driver.getWindowHandle();
		System.out.println(handle1);
		
		//w3schools
		driver.findElement(By.xpath("//a[contains(text(), 'XPath Tutorial - W3Schools')]")).click();
		Thread.sleep(3000);
		
		System.out.println(driver.getTitle());		
		String handle3 = driver.getWindowHandle();
		System.out.println(handle3);
		
		Set<String> handles=driver.getWindowHandles();
		System.out.println(handles);
		
		for(String i: handles) {
			System.out.println(i);
			driver.switchTo().window(i);
		}
		
		System.out.println(driver.getTitle());	
		driver.switchTo().window(handle3);
		
	}

	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
