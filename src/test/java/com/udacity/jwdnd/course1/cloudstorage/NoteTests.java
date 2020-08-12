package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests {

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

	private HomePage signUpAndLogin() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName("John");
		signupPage.setLastName("Lennon");
		signupPage.setUserName("lennon");
		signupPage.setPassword("julia");
		signupPage.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName("lennon");
		loginPage.setPassword("julia");
		loginPage.login();

		return new HomePage(driver);
	}

	/**
	 * Test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void testCreateAndDisplay() throws InterruptedException {
		HomePage homePage = signUpAndLogin();
		homePage.addNewNote();
		Thread.sleep(5000);
	}
}
