package pomImplementation;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericLibraries.ExcelFileUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import pomPages.ContactsPage;
import pomPages.CreateNewContactPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewContactInfoPage;

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
		
		LoginPage login = new LoginPage(driver);
		HomePage home = new HomePage(driver);
		ContactsPage contacts = new ContactsPage(driver);
		CreateNewContactPage createContact = new CreateNewContactPage(driver);
		NewContactInfoPage newContact = new NewContactInfoPage(driver);
		
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
		
		home.clickContacts();
		
		if (contacts.getPageHeader().contains("Contacts"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		contacts.clickPlusButton();
		
		if(createContact.getPageHeader().contains("Creating New Contact"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Contact");
			
		createContact.selectSalutation(web, map.get("First Name Salutation"));
		String lastName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createContact.setLastName(lastName);
		createContact.selectExistingOrganization(web, map.get("Organization Name"));
		createContact.uploadContactImage(map.get("Contact Image"));		
		createContact.clickSave();
		
		if(newContact.getPageHeader().contains(lastName))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		newContact.clickContactsLink();
		
		if(contacts.getNewContact().equals(lastName)) {
			System.out.println("Pass");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH);
		}
		else {
			System.out.println("Fail");
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH);
		}
			
		home.signOut(web);
		
		web.closeBrowser();
		excel.closeExcel();

	}

}
