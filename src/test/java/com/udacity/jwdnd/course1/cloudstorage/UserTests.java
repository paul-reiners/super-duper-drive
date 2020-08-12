package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

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
	public void testPageAccess() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies
	 * that the home page is no longer accessible.
	 */
	@Test
	public void testSignUpLoginLogout() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName("John");
		signupPage.setLastName("Lennon");
		signupPage.setUserName("lennon");
		signupPage.setPassword("julia");
		Thread.sleep(5000);
		signupPage.signUp();

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName("lennon");
		loginPage.setPassword("julia");
		Thread.sleep(5000);
		loginPage.login();

		HomePage homePage = new HomePage(driver);
		Thread.sleep(5000);
		homePage.logout();

		driver.get("http://localhost:" + this.port + "/home");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());
	}
}
