package hardcodedTestScripts;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactWithExistingOrganization {

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
		
		driver.findElement(By.xpath("//a[.='Contacts']")).click();
		
		String contacts = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		
		if (contacts.contains("Contacts"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();
		String createContact = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createContact.contains("Creating New Contact"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		WebElement salutationDropdown = driver.findElement(By.name("salutationtype"));
		Select salutation = new Select(salutationDropdown);
		salutation.selectByValue("Mrs.");
		
		String lastName = "XYZ"+randomNum;
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.xpath("//img[contains(@onclick,'Accounts&action')]")).click();
		
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String wID : windows) {
			driver.switchTo().window(wID);
		}
		
		String organization = "Qspiders";
		List<WebElement> organizationList = driver.findElements(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/descendant::tr/td[1]/a"));
		for(WebElement org: organizationList) {
			String name = org.getText();
			if(name.equals(organization)) {
				org.click();
				break;
			}
		}
		
		driver.switchTo().window(parentWindow);
		driver.findElement(By.name("imagename")).sendKeys("D:\\QSP-JS-101 Batch\\QSPGL-JSP-101\\Notes\\Listeners 2.png");
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newContactInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newContactInfo.contains(lastName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newContact = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[4]/a")).getText();
		if(newContact.equals(lastName))
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
