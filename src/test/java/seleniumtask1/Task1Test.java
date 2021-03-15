package seleniumtask1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import helper.ScreenshotHelper;

public class Task1Test {
	private static WebDriver driver;
	private static String URL = "http://thedemosite.co.uk/";
	
	//setup reports
	private static ExtentReports extent;
	private static ExtentTest test;

	@BeforeClass
	public static void setup() {
		//setup the chrome driver
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions cOptions = new ChromeOptions();
        cOptions.setHeadless(false);//wanna set it for now 
        //disable cookies 
        cOptions.setCapability("profile.default_content_setting_values.cookies",2);
        cOptions.setCapability("network.cookie.cookieBehavior", 2);
        cOptions.setCapability("profile.block_third_party_cookies", true);
        driver = new ChromeDriver(cOptions);
        driver.manage().window().setSize(new Dimension(1366, 768));
	}
	
	
	@Before
	public void init() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			driver.get(URL);
		} catch (TimeoutException e) {
			System.out.println("Page: " + URL + " did not load within 30 seconds!");
		}
		//try to load the page within 30 seconds
	}
	
	@Test
	public void task1Test() throws Exception {
		driver.get("http://thedemosite.co.uk/addauser.php");//go here find form input data hit save
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.name("saveform")));
		//wait upto 2 seconds for the form to appear
		
		WebElement usernameBox = driver.findElement(By.name("username"));
		WebElement passwordBox = driver.findElement(By.name("password"));
		WebElement submitBtn = driver.findElement(By.name("FormsButton2"));
		
		usernameBox.sendKeys("peter5600");
		passwordBox.sendKeys("pwd12345");//max length of 8
		submitBtn.click();//could use submit but ill use click
		
		driver.get("http://thedemosite.co.uk/login.php");
		
		WebElement loginUNameBox = driver.findElement(By.name("username"));
		WebElement loginPasswordBox = driver.findElement(By.name("password"));
		WebElement loginSubmitBtn = driver.findElement(By.name("FormsButton2"));
		
		loginUNameBox.sendKeys("peter5600");
		loginPasswordBox.sendKeys("pwd12345");
		loginSubmitBtn.click();
		
		List<WebElement> result = driver.findElements(By.tagName("b"));//loads of b tags find something else to check it with
		String ActualResult = result.get(2).getText();
		ScreenshotHelper.screenShot(driver, "src/test/resources/screenshots/shot.png");
		assertEquals(ActualResult, "**Successful Login**");
	}
	//look into xPath
	
	
	@AfterClass
	public static void teardown() {
		driver.quit();
	}
}
