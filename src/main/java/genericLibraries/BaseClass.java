package genericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import pomPages.ContactsPage;
import pomPages.CreateNewContactPage;
import pomPages.CreateNewOrganizationPage;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewContactInfoPage;
import pomPages.NewOrganizationInfoPage;
import pomPages.OrganizationsPage;

public class BaseClass {

	protected WebDriverUtility web;
	protected PropertyFileUtility property;
	protected ExcelFileUtility excel;
	protected JavaUtility javaUtil;
	protected WebDriver driver;
	protected LoginPage login;
	protected HomePage home;
	protected ContactsPage contacts;
	protected CreateNewContactPage createContact;
	protected NewContactInfoPage newContact;
	protected OrganizationsPage organization;
	protected CreateNewOrganizationPage createOrganization;
	protected NewOrganizationInfoPage newOrganization;

	// @BeforeSuite
	// @BeforeTest
	@BeforeClass
	public void classSetup() {
		web = new WebDriverUtility();
		property = new PropertyFileUtility();
		excel = new ExcelFileUtility();
		javaUtil = new JavaUtility();

		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelInitialization(IConstantPath.EXCEL_FILE_PATH);

		String url = property.getDataFromProperties("url");
		String browser = property.getDataFromProperties("browser");
		long time = Long.parseLong(property.getDataFromProperties("timeouts"));

		driver = web.openApplication(browser, url, time);
		login = new LoginPage(driver);
		Assert.assertTrue(login.getPageHeader().contains("vtiger"));
		
	}

	@BeforeMethod
	public void methodSetup() {
		
		home = new HomePage(driver);
		contacts = new ContactsPage(driver);
		createContact = new CreateNewContactPage(driver);
		newContact = new NewContactInfoPage(driver);
		organization = new OrganizationsPage(driver);
		createOrganization = new CreateNewOrganizationPage(driver);
		newOrganization = new NewOrganizationInfoPage(driver);

		String username = property.getDataFromProperties("username");
		String password = property.getDataFromProperties("password");
		login.loginToApplication(username, password);
		Assert.assertTrue(home.getPageHeader().contains("Home"));
	}

	@AfterMethod
	public void methodTeardown() {
		home.signOut(web);
	}

	@AfterClass
	public void classTeardown() {
		web.closeBrowser();
		excel.closeExcel();
	}
	// @AfterTest
	// @AfterSuite

}