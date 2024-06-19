package PizzaHutTestNGimplementation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;
import com.aventstack.extentreports.ExtentTest;

public class pizzaHutTest extends BaseTest{
	
	ExtentTest test;
	
	   
	@BeforeSuite
	@Parameters("url")
	
	public void setUp(String url) {
		
		//ExtentReport
        setupReport();
		
		// Open browser and maximize window
        openBrowser();
                        
        // Open application URL
        openApplicationURL(url);
        
        // Delete cookies
        deleteCookies();
        
        // Set page load timeout
        setPageLoadTimeout(5000);
        
	}
	
	@BeforeClass
	@Test(priority=1)
	public void setLocation()
	{
		try {
		
		test = extent.createTest("pizzaHut Test");
		
		setPageLoadTimeout(5000);

		//close the location blackbox
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
		   	WebElement blackBox1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-synth='close-offer-collection']")));
		   	blackBox1.click();
		   
		
		//Set the user delivery location as Lulu Mall, Bangalore
			WebElement locationSetup = driver.findElement(By.xpath("//input[@data-synth='input--google-places']"));
			locationSetup.sendKeys("Lulu Mall Banglore");
			WebElement flexBox = driver.findElement(By.xpath("//div[@class='pt-5 border-t overflow-scrolling-touch']"));
			flexBox.isDisplayed();
			WebElement locationSet = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='text-left pl-15 py-10 border-t border-l-0 border-r-0 border-b-0 border-grey-lightest border-solid suggestion-item'][1]")));
		    locationSet.click();
		    	
			test.pass("Location set");
		
		}	
		catch(Exception e)
		{
			test.fail("Location is not set"+e.getMessage())	;
		}
	}
	
	@Test(priority=2)
	public void validateDealPage()
	{
		try {
			
		//The user is now on the Deals page. Validate that the URL has text as ‘deals’
			String currentUrl = driver.getCurrentUrl();
			assert currentUrl.contains("deals");
		 
			test.pass("URL contain the word deals.");
		}
		catch(AssertionError e) {
			test.fail("URL does not contain the work deals"+e.getMessage());
		}
		
	}
	
	@Test(priority=3)
	public void addSidesItem()
	{
		try {
			
		//Navigate to Sides Menu
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
			WebElement sideMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-synth='link--sides--side']")));
			sideMenu.click();
			
			test.pass("The user is on the Sides page");
		
		//Adding Item to the basket which is below Rs.200	
			WebElement item = driver.findElement(By.xpath("//span[@class='w-auto ml-3']"));
			item.click();		
			test.pass("Item below 200 added to the basket.");
		
		}
		catch(Exception e)
		{
			test.fail("Items not added to the basket"+e.getMessage());
		}
	}
	
	@Test(priority=4)
	public void addDrinkItem()
	{
		try {
			
		//Navigate to page DRINKS
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // 10 seconds timeout
			WebElement addDrink = driver.findElement(By.linkText("Drinks"));
			addDrink.click();
			test.pass("The user is on the Drinks page.");
		
		//Add 2 drinks 
			WebElement item1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-synth='link--pepsi-600ml']")));
			item1.click();			
			WebElement item1Add = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-synth='button--add-to-basket']")));
			item1Add.click();	
			WebElement item2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-synth='link--pepsi-black-250ml']")));
			item2.click();
			WebElement item2Add = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-synth='button--add-to-basket']")));
			item2Add.click();
			
			test.pass("Two drinks added to the basket");
	
		}
		catch(Exception e)
		{
			test.fail("Items not added to the basket"+e.getMessage());
		}
	}
		
	@Test(priority=5)	
	public void checkoutProcess() {
		
		try {
		//Checkout the order
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // 10 seconds timeout
			WebElement checkOut = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Checkout']")));
			checkOut.click();
			test.pass("The user is on the checkout page.");
		
		//Validate the online payment option is selected
			WebElement onlinePay = driver.findElement(By.id("payment-method--razorpay"));
			assert onlinePay.isSelected();
			test.pass("Online Payment selected by default");
		
		//Validate the "I agree" option is selected
			WebElement agreePay = driver.findElement(By.id("marketingOptIn"));
			assert agreePay.isSelected();
			test.pass("I agree selected by the default");
		
		//Enter name, mobile, and email address
			WebElement name = driver.findElement(By.id("checkout__name"));
			name.sendKeys("Shalini");
			WebElement email = driver.findElement(By.id("checkout__email"));
			email.sendKeys("shalinichauhanpurohit@yahoo.com");
			WebElement phoneNo = driver.findElement(By.id("checkout__phone"));
			phoneNo.sendKeys("9687482564");
			WebElement address = driver.findElement(By.id("checkout__deliveryAddress.interior"));
			address.sendKeys("Gandhinagar");
			test.pass("Details are added");
		
			
		//Change to the cash payment
            WebElement cashPay = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@data-synth='payment-method--cash-label']")));
			cashPay.click();
			test.pass("Payment changed to Cash.");
			
		//Click on the Apply Gift Card link. A pop up should appear. Click on the Voucher
			WebElement giftCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Apply Gift Card']")));
			giftCard.click();
			WebElement coupon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Coupon']")));
			coupon.click();
		
		//Give the Voucher code as 12345 and submit
			WebElement couponCode = driver.findElement(By.xpath("//input[@name='voucherId']"));
			couponCode.sendKeys("12345");
			WebElement applyButton = driver.findElement(By.xpath("//button[@type='submit']"));
			applyButton.click();
		
		//Validate if an error is coming that the number is incorrect
			WebElement errorMsg = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sorry, we don’t currently support that coupon code.']")));
			assert errorMsg.isDisplayed();
		
		//Close the voucher pop up
			WebElement closePopup = driver.findElement(By.xpath("//button[@class='icon-remove-3 absolute top-0 right-0 mr-20 mt-20']"));
			closePopup.click();
			
		}
		catch(Exception e)
		{
			test.fail("Process Failed"+ e.getMessage());
		}
	}
	
	 @AfterClass
	    public void processClose() {
	        driver.quit();
	        test.pass("Driver session quit");
	        extent.flush();
}
}
	


