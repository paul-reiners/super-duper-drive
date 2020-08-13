package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for Note Creation, Viewing, Editing, and Deletion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {
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
