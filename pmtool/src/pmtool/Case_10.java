package pmtool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Case_10 {

	public static void main(String[] args) {
		// Step 0: Invoke chromedriver.exe file from your drive needed from Chrome
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\stergios\\Downloads\\Java_and_Selenium_course_files\\browser_drivers\\chromedriver.exe");
						
		// Step 1: Create driver object for Chrome browser
		WebDriver driver = new ChromeDriver();
					
		// Hit URL in browser
		driver.get("https://node-fs-app.herokuapp.com/");
		// Add a small delay before giving inputs
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		    System.out.println(e);
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		LocalDateTime current_date_time = LocalDateTime.now();  
		String formateddatetime = dtf.format(current_date_time);
		
		// Register a new account
		driver.findElement(By.id("signup")).click();
				
		driver.findElement(By.id("fullName")).sendKeys("John Doe"+ formateddatetime);
		// Sign up form accepts upper case in email but requires lower case in login
		driver.findElement(By.id("email")).sendKeys("John_D" + formateddatetime + "@in.gr");
		driver.findElement(By.id("address")).sendKeys("Konstantinou Karamanli " + formateddatetime);
		driver.findElement(By.id("password")).sendKeys("123456");
		
		driver.findElement(By.cssSelector("button[name='action']")).click();
		
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		
		String expected_text = "Successfull registration, login to start using PPMTool";
		String sunny_day_text = driver.findElement(By.xpath("//*[@class='row']/div/div/div/p")).getText();
		//System.out.println(sunny_day_text);
		
		if (sunny_day_text.equals(expected_text)) {
			System.out.println("Registration was successful");
		} else {
			System.out.println("Registration failed");
		}
		
		
		// Login to account
		driver.findElement(By.id("login")).click();				

		// Form requires email to be lower case
		driver.findElement(By.id("email")).sendKeys("john_d" + formateddatetime + "@in.gr");
		driver.findElement(By.id("password")).sendKeys("123456");
		
		try {
		    Thread.sleep(200);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		driver.findElement(By.cssSelector("button[name='action']")).click();
		
		try {
		    Thread.sleep(500);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}	
		
		//Create and view new project 
		driver.findElement(By.xpath("//*[text()='Create']")).click();
		driver.findElement(By.id("name")).sendKeys("John Doe "+ formateddatetime);
		driver.findElement(By.id("description")).sendKeys("This is a project created by John Doe "+ formateddatetime);
		driver.findElement(By.cssSelector("button[name='action']")).click();
		try {
		    Thread.sleep(400);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}		

		expected_text = "John Doe "+ formateddatetime;
		String extracted_text = driver.findElement(By.xpath("//*[@class='card-title']")).getText();
		
		if (extracted_text.equals(expected_text)) {
			System.out.println("Creation of project was successful");
		} else {
			System.out.println("Project creation failed");
		}
		
		//Edit existing project and view it
		driver.findElement(By.id("btn_update_project")).click();
		try {
		    Thread.sleep(400);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		
		driver.findElement(By.id("description")).sendKeys(" (Updated)");
		driver.findElement(By.cssSelector("button[name='action']")).click();
		
		try {
		    Thread.sleep(400);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		String current_text = driver.findElement(By.xpath("//*[@class='row']/div/div/div/p")).getText();
		if (current_text.contains("(Updated)")) {
			System.out.println("Project editing was successful");
		} else {
			System.out.println("Project editing failed");
		}
		
		
		//Delete the existing project
		driver.findElement(By.id("delete_project")).click();
		//Handle pop up message
        Alert alert = driver.switchTo().alert();
        alert.accept();
        
		try {
		    Thread.sleep(300);
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
        
        current_text = driver.findElement(By.xpath("//*[@class='row']/div/div/div/p")).getText();
        
        if (current_text.contains("(Updated)")) {
        	System.out.println("Project deletion failed"); 
		} else {
			System.out.println("Project deletion was successful");
		}
        
		// Teardown
		driver.close(); 
		driver.quit();
        
	}
	
}
