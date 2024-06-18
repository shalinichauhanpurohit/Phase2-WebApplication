package PizzaHutTestNGimplementation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
	
	protected static WebDriver driver;
	ExtentReports extent;	
	
	public void setupReport () 
	{
		ExtentSparkReporter sparkRepoter = new ExtentSparkReporter("extent.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkRepoter);
	}   
	
	//Create a method and name it OpenBrowser()
	public void openBrowser( )
	{
		FirefoxOptions option = new FirefoxOptions();
    	option.addPreference("geo.prompt.testing", true);
    	option.addPreference("geo.prompt.testing.allow", true);
    	
	    //Add steps to open the Chrome browser
    	option.addArguments("--incognito");
	    driver = new FirefoxDriver(option);   
        driver.manage().window().maximize();
	}
	
	// Method to open the application URL in the browser
    public void openApplicationURL(String url) 
    {
    	driver.get(url);
    }
    
    // Method to delete cookies
    public void deleteCookies() {
        driver.manage().deleteAllCookies();
    }
	
    // Method to set page load timeout
    public void setPageLoadTimeout(int seconds) 
    {
        driver.manage().timeouts();
    }
 }
