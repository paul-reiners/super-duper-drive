package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Tests for User Signup, Login, and Unauthorized Access Restrictions.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/**
	 * Write a test that verifies that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void testPageAccess() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(5000);

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		Thread.sleep(5000);

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(5000);
	}

	/**
	 * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies
	 * that the home page is no longer accessible.
	 */
	@Test
	public void testSignUpLoginLogout() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys("John");

		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys("Lennon");

		WebElement inputUserName = driver.findElement(By.id("inputUsername"));
		inputUserName.sendKeys("lennon");

		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys("julia");

		Thread.sleep(5000);

		WebElement submitButton = driver.findElement(By.id("submit-button"));
		submitButton.click();

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		inputUserName = driver.findElement(By.id("inputUsername"));
		inputUserName.sendKeys("lennon");

		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys("julia");
		Thread.sleep(5000);

		submitButton = driver.findElement(By.id("submit-button"));
		submitButton.click();
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(5000);

		WebElement btnLogout = driver.findElement(By.id("btnLogout"));
		btnLogout.click();
		Thread.sleep(5000);

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(5000);
	}
}
