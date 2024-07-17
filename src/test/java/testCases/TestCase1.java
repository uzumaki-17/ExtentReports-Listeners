package testCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestCase1 {
//	ExtentSparkReporter is used to set values to the extent reports
	public ExtentSparkReporter spark ;
//	then we pass this value to the actual report
	public ExtentTest test;
	public ExtentReports extent;
	
	@BeforeTest
	public void setReport()
	{
		spark = new ExtentSparkReporter("./reports/spark.html");
		spark.config().setEncoding("utf-8");
		spark.config().setDocumentTitle("sdet Automation reports");
		spark.config().setReportName("Automation Results");
		spark.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(spark);
		
		extent.setSystemInfo("Automation Tester", "Ujjwal Gupta");
		extent.setSystemInfo("Organisation", "Sdet Tech");
		extent.setSystemInfo("Report Version", "Artifact-001");
		
	}
	@AfterTest
		public void endReport()
		{
			extent.flush();
		}
	@Test
	public void loginTest()
	{
		test = extent.createTest("Loin test");
		System.out.println("User LOgin Test");
	}
	@Test
	public void userRegTestFail()
	{
		test = extent.createTest("UserRegTest test");
		Assert.fail("userRegTest Fail");
	}
	@Test
	public void userAuthTestSkip()
	{
		test = extent.createTest("UserAuthTest test");
		throw new SkipException("Skipping Test");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			String methodName = result.getMethod().getMethodName().toString();
			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  FAILED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
			test.fail(m);
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			String methodName = result.getMethod().getMethodName().toString();
			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  PASSED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.pass(m);

		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			String methodName = result.getMethod().getMethodName().toString();
			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  SKIPPED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			test.skip(m);
		}
	}
	
	}
	
