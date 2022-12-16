package genericLibImpementation;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericLibraries.ExcelFileUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;

public class CreateContactWithExistingOrganization {

	public static void main(String[] args) {
		
		WebDriverUtility web = new WebDriverUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		ExcelFileUtility excel = new ExcelFileUtility();
		JavaUtility javaUtil = new JavaUtility();
		
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);
		
		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));
		
		WebDriver driver = web.openApplication(browser, url, time);
		
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
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Contact");
		
		WebElement salutationDropdown = driver.findElement(By.name("salutationtype"));
		web.dropdown(map.get("First Name Salutation"), salutationDropdown);

		String lastName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		driver.findElement(By.xpath("//img[contains(@onclick,'Accounts&action')]")).click();
	
		String parentWindow = web.getParentWindow();
		web.handeChildBrowser();
				
		String organization = map.get("Organization Name");
		List<WebElement> organizationList = driver.findElements(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/descendant::tr/td[1]/a"));
		for(WebElement org: organizationList) {
			String name = org.getText();
			if(name.equals(organization)) {
				org.click();
				break;
			}
		}
		
		web.switchToWindow(parentWindow);
		
		driver.findElement(By.name("imagename")).sendKeys(map.get("Contact Image"));
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		
		String newContactInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(newContactInfo.contains(lastName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String newContact = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[4]/a")).getText();
		if(newContact.equals(lastName)) {
			System.out.println("Pass");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH);
		}
		else {
			System.out.println("Fail");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH);
		}
			
		WebElement administratorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		web.mouseHoverToElement(administratorIcon);
		
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		web.closeBrowser();
		excel.closeExcel();

	}

}
