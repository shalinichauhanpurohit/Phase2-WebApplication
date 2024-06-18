package PizzaHutTestNGimplementation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class pizzaHutTest extends BaseTest{
	
	String baseUrl;
	ExtentReports extent;
    ExtentTest test;
	
   
	@BeforeSuite
	@Parameters("url")
	
	public void setUp(String url) {
		
		//ExtentReport
        setupReport();
		
		// Open browser and maximize window
        openBrowser();
                
        // Open application URL
        baseUrl = url;
        openApplicationURL(baseUrl);
        
        // Delete cookies
        deleteCookies();
        
        // Set page load timeout
        setPageLoadTimeout(5000);
        
        test = extent.createTest("pizzaHut Test");
	}
	
	@BeforeClass
	@Test(priority=1)
	public void setLocation()
	{
		try {
			
		//Set the user delivery location as Lulu Mall, Bangalore
		WebElement locationSetup = driver.findElement(By.xpath("//input[@type='text']"));
		locationSetup.sendKeys("Lulu Mall banglore");
		locationSetup.click();	
		test.pass("Location set");
		}
		catch(Exception e){
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
		WebElement sideMenu = driver.findElement(By.xpath("//class[@href='/order/sides/']"));
		sideMenu.click();
		test.pass("The user is on the Sides page");
		
		//Adding Item to the basket which is below Rs.200	
		WebElement item = driver.findElement(By.xpath("//item[@price<200]"));
		item.click();
		
		WebElement addToBasket = driver.findElement(By.linkText("Add"));
		addToBasket.click();
		test.pass("Item below 200 added to the basket.");
		
		//Checkout the Item added to the Basket
		//WebElement checkOut = driver.findElement(By.linkText("Checkout"));
		//checkOut.click();
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
	//	openApplicationURL(baseUrl);
		//deleteCookies();
			
		//Navigate to page DRINKS
		WebElement addDrink = driver.findElement(By.linkText("Drinks"));
		addDrink.click();
		test.pass("The user is on the Drinks page.");
		
		//Add 2 drinks 
		WebElement item1 = driver.findElement(By.xpath("//button[@dataset.synth=button--pepsi-600ml--one-tap]"));
		item1.click();
		WebElement item2 = driver.findElement(By.xpath("//button[@dataset.synth=button--7-up-600ml--one-tap]"));
		item2.click();
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
		WebElement checkOut = driver.findElement(By.xpath("//a[@href=https://www.pizzahut.co.in/order/checkout/]"));
		checkOut.click();
		test.pass("The user is on the checkout page.");
		
		//Validate the online payment option is selected
		WebElement onlinePay = driver.findElement(By.id("payment-method--razorpay"));
		assert onlinePay.isSelected();
		test.pass("Online Payment selected by default");
		
		//Change to the cash payment
		WebElement cashPay = driver.findElement(By.id("payment-method--cash"));
		cashPay.click();
		test.pass("Payment changed to Cash.");
		
		//Validate the "I agree" option is selected
		WebElement agreePay = driver.findElement(By.id("marketingOptIn"));
		assert agreePay.isSelected();
		test.pass("I agree selected by the default");
		
		//Enter name, mobile, and email address
		WebElement name = driver.findElement(By.id("checkout__name"));
		name.sendKeys("Shalini");
		WebElement email = driver.findElement(By.id("checkout__email"));
		email.sendKeys("shalinichauhanpurohit@yahoo.com");
		WebElement address = driver.findElement(By.id("checkout__phone"));
		address.sendKeys("9687482564");
		test.pass("Details are added");
		
		//Click on the Apply Gift Card link. A pop up should appear. Click on the Voucher
		WebElement giftCard = driver.findElement(By.linkText("Apply Gift Card"));
		giftCard.click();
		WebElement coupon = driver.findElement(By.linkText("Coupon"));
		coupon.click();
		
		//Give the Voucher code as 12345 and submit
		WebElement couponCode = driver.findElement(By.xpath("//input[@name=voucherId]"));
		couponCode.sendKeys("12345");
		WebElement applyButton = driver.findElement(By.xpath("//button[@type=submit]"));
		applyButton.click();
		
		//Validate if an error is coming that the number is incorrect
		WebElement errorMsg = driver.findElement(By.linkText("Sorry, we don’t currently support that coupon code."));
		assert errorMsg.isDisplayed();
		
		//Close the voucher pop up
		WebElement closePopup = driver.findElement(By.linkText("Cancel"));
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
	


