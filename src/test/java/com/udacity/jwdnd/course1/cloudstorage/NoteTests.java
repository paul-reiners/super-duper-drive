package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.Ordered;

import java.util.concurrent.TimeUnit;

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
	 * Test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testDelete() {
		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		HomePage homePage = signUpAndLogin();
		createNote(noteTitle, noteDescription, homePage);
		homePage.navToNotesTab();
		homePage = new HomePage(driver);
		homePage.deleteNote();
		Assertions.assertTrue(homePage.noNotes(driver));
	}

	/**
	 * Test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void testCreateAndDisplay() {
		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		HomePage homePage = signUpAndLogin();
		createNote(noteTitle, noteDescription, homePage);
		homePage.navToNotesTab();
		homePage = new HomePage(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		homePage.deleteNote();
		homePage.logout();
	}

	/**
	 * Test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testModify() {
		String noteTitle = "My Note";
		String noteDescription = "This is my note.";
		HomePage homePage = signUpAndLogin();
		createNote(noteTitle, noteDescription, homePage);
		homePage.navToNotesTab();
		homePage = new HomePage(driver);
		homePage.editNote();
		String modifiedNoteTitle = "My Modified Note";
		homePage.modifyNoteTitle(modifiedNoteTitle);
		String modifiedNoteDescription = "This is my modified note.";
		homePage.modifyNoteDescription(modifiedNoteDescription);
		homePage.saveNoteChanges();
		homePage.navToNotesTab();
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
	}

	private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
		homePage.navToNotesTab();
		homePage.addNewNote();
		homePage.setNoteTitle(noteTitle);
		homePage.setNoteDescription(noteDescription);
		homePage.saveNoteChanges();
	}
}
