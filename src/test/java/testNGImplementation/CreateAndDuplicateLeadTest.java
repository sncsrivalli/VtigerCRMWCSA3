package testNGImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;

public class CreateAndDuplicateLeadTest extends BaseClass{

	@Test
	public void createDuplicateLeadTest() {
		SoftAssert softAssert = new SoftAssert();
		home.clickLeads();
		softAssert.assertTrue(leads.getPageHeader().contains("Leads"));
		
		leads.clickPlusButton();
		softAssert.assertTrue(createLead.getPageHeader().contains("Creating New Lead"));
		
		Map<String,String> map = excel.getDataBasedOnKey("TestData", "Create Lead");

		createLead.selectSalutation(web, map.get("First Name Salutation"));
	
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createLead.setLastName(leadName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSave();
		
		softAssert.assertTrue(newLeadInfo.getPageHeader().contains(leadName));
		
		newLeadInfo.clickDuplicateButton();
		
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		duplicating.setLastName(duplicateLeadName);
		duplicating.clickSave();
		
		newLeadInfo.clickLeadsLink();
		softAssert.assertTrue(leads.getNewLead().equals(duplicateLeadName));
		if(leads.getNewLead().equals(duplicateLeadName)) 
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Pass", IConstantPath.EXCEL_FILE_PATH);
		else 
			excel.updateTestStatusInExcel("TestData", "Create Lead", "Fail", IConstantPath.EXCEL_FILE_PATH);
		softAssert.assertAll();
	}
}
