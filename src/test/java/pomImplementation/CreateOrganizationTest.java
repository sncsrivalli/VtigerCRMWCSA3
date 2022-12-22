package pomImplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelFileUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewOrganizationPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationsPage;

public class CreateOrganizationTest {

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
		
		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		OrganizationsPage organization = new OrganizationsPage(driver);
		CreateNewOrganizationPage createOrganization = new CreateNewOrganizationPage(driver);
		NewOrganizationInfoPage newOrganization = new NewOrganizationInfoPage(driver);
		
		if(login.getPageHeader().contains("vtiger"))
			System.out.println("Login Page Displayed");
		else
			System.out.println("Login Page is not Displayed");
		
		String username = property.getDataFromProperties("username");
		String password = property.getDataFromProperties("password");
		login.loginToApplication(username, password);
		
		if(home.getPageHeader().contains("Home"))
			System.out.println("Home Page is displayed");
		else
			System.out.println("Home Page is not displayed");
		
		home.clickOrganizations();
		
		if (organization.getPageHeader().contains("Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		organization.clickPlusButton();
		
		if(createOrganization.getPageHeader().contains("Creating New Organization"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Organization");
		
		String organizationName = map.get("Organization Name") + javaUtil.generateRandomNumber(100);
		createOrganization.setOrganizationName(organizationName);
		
		createOrganization.selectIndustry(web, map.get("Industry"));
		createOrganization.clickSaveButton();
		
		if(newOrganization.getPageHeader().contains(organizationName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
	
		newOrganization.clickOrganizationsLink();
		
		if(organization.getNewOrganization().equals(organizationName)) {
			System.out.println("Pass");
			excel.updateTestStatusInExcel("TestData", "Create Organization", "Pass", IConstantPath.EXCEL_FILE_PATH);
		}
		else {
			System.out.println("Fail");
			excel.updateTestStatusInExcel("TestData", "Create Organization", "Fail", IConstantPath.EXCEL_FILE_PATH);
		}			
		
		home.signOut(web);
		
		web.closeBrowser();
		excel.closeExcel();
		
		
	}

}
