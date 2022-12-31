package testNGImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;

public class CreateContactWithExistingOrganizationTest extends BaseClass{

	@Test
	public void createContactTest() {
		SoftAssert softAssert = new SoftAssert();
		home.clickContacts();
		softAssert.assertTrue(contacts.getPageHeader().contains("Contacts"));
		
		contacts.clickPlusButton();
		softAssert.assertTrue(createContact.getPageHeader().contains("Creating New Contact"));
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Contact");
			
		createContact.selectSalutation(web, map.get("First Name Salutation"));
		String lastName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createContact.setLastName(lastName);
		createContact.selectExistingOrganization(web, map.get("Organization Name"));
		createContact.uploadContactImage(map.get("Contact Image"));		
		createContact.clickSave();
		
		softAssert.assertTrue(newContact.getPageHeader().contains(lastName));
		
		newContact.clickContactsLink();
		softAssert.assertTrue(contacts.getNewContact().equals(lastName));
		if(contacts.getNewContact().equals(lastName)) 
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH);
		else 
			excel.updateTestStatusInExcel("TestData", "Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH);
		
		softAssert.assertAll();
	}

}
