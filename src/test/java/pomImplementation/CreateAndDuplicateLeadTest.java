package pomImplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelFileUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.CreateNewLeadPage;
import pomPages.DuplicatingPage;
import pomPages.HomePage;
import pomPages.LeadsPage;
import pomPages.LoginPage;
import pomPages.NewLeadInfoPage;

public class CreateAndDuplicateLeadTest {

	public static void main(String[] args) {
		
		WebDriverUtility web = new WebDriverUtility();
		JavaUtility javaUtil = new JavaUtility();
		PropertyFileUtility property = new PropertyFileUtility();
		ExcelFileUtility excel = new ExcelFileUtility();
		
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);
		
		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));
		
		WebDriver driver = web.openApplication(browser, url, time);
		
		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		LeadsPage leads = new LeadsPage(driver);
		CreateNewLeadPage createLead = new CreateNewLeadPage(driver);
		NewLeadInfoPage newLeadInfo = new NewLeadInfoPage(driver); 
		DuplicatingPage duplicating = new DuplicatingPage(driver);
		
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
		
		home.clickLeads();
		
		if (leads.getPageHeader().contains("Leads"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		leads.clickPlusButton();
		
		if(createLead.getPageHeader().contains("Creating New Lead"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Lead");

		createLead.selectSalutation(web, map.get("First Name Salutation"));
	
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createLead.setLastName(leadName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSave();
		
		if(newLeadInfo.getPageHeader().contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		newLeadInfo.clickDuplicateButton();
		
		if(duplicating.getPageHeader().contains(leadName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		duplicating.setLastName(duplicateLeadName);
		duplicating.clickSave();
		
		newLeadInfo.clickLeadsLink();
		
		if(leads.getNewLead().equals(duplicateLeadName)) {
			System.out.println("Pass");
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Pass", IConstantPath.EXCEL_FILE_PATH);
		}
		else {
			System.out.println("Fail");
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Fail", IConstantPath.EXCEL_FILE_PATH);
		}
		

		home.signOut(web);
		web.closeBrowser();
		excel.closeExcel();
	}
}
