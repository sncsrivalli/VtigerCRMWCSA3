package hardcodedTestScripts;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganizationTest {

	public static void main(String[] args) {
		
		Random random = new Random();
		int randomNum = random.nextInt(100);
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		String title = driver.findElement(By.xpath("//a[@href='http://www.vtiger.com']")).getText();
		
		if(title.contains("vtiger"))
			System.out.println("Login Page Displayed");
		else
			System.out.println("Login Page is not Displayed");
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		
		String homePageText = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(homePageText.contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		String organizations = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		
		if (organizations.contains("Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();
		
		String createOrg = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrg.contains("Creating New Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		String organizationName = "Qspiders" + randomNum;
		driver.findElement(By.name("accountname")).sendKeys(organizationName);
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		Select industry = new Select(industryDropdown);
		industry.selectByValue("Banking");
		
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newOrgInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newOrgInfo.contains(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newOrg = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]")).getText();
		if(newOrg.equals(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions a = new Actions(driver);
		a.moveToElement(administratorIcon).perform();
		
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		driver.quit();
		
		
	}

}
