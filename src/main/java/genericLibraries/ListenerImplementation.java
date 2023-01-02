package genericLibraries;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementation implements ITestListener{
	
	private ExtentReports report;
	private ExtentTest test;
	public static ExtentTest stest;

	@Override
	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getMethod().getMethodName());
		stest = test;
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.pass(result.getMethod().getMethodName()+" Pass");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getMethod().getMethodName()+" Fail");
		new WebDriverUtility().getScreenshot(BaseClass.sjavaUtil, BaseClass.sdriver, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.skip(result.getMethod().getMethodName()+" skipped");
		test.fail(result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentSparkReporter spark = new ExtentSparkReporter("./ExtentReports/extentReport.html");
		spark.config().setDocumentTitle("Title - VTiger CRM");
		spark.config().setReportName("Vtiger Reports");
		spark.config().setTheme(Theme.DARK);
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("Author", "Srivalli");
		report.setSystemInfo("OS", "Windows");
		report.setSystemInfo("Browser", "Chrome");
		
	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}

}
