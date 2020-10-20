package seedu.duke.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageManagerTest {
    private static final String VALID_WORKSPACE = "ValidWorkspace";
    private static final String EMPTY_FILE_WORKSPACE = "EmptyFileWorkspace";
    private static final String EMPTY_WORKSPACE = "EmptyWorkspace";
    private static final String SOME_INVALID_WORKSPACE = "SomeInvalidWorkspace";
    private static final String ALL_INVALID_WORKSPACE = "AllInvalidWorkspace";
    private static final String SCRIPT_FILE_NAME = "script.txt";
    private static final String INVALID_TEST_DIRECTORY = "a" + File.separator + "b" + File.separator + "c"
                                                         + File.separator;
    private static final String VALID_TEST_DIRECTORY = "src" + File.separator + "test" + File.separator
                                                        + "data" + File.separator + "StorageManagerTest"
                                                        + File.separator;
    private static final String VALID_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithValidFile"
                                                        + File.separator;
    private static final String EMPTY_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithEmptyFileAndDirectory"
                                                         + File.separator;
    private static final String INVALID_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithInvalidFile"
                                                         + File.separator;

    private StorageManager validFileSM;
    private StorageManager invalidFileSM;
    private StorageManager emptySM;
    private StorageManager invalidDirectorySM;
    private User userToSave;
    private User userToLoad;
    private ArrayList<Watchlist> watchlistListForLoad;
    private ArrayList<Watchlist> watchlistListForSave;

    @BeforeEach
    public void setUp() throws AniException {
        validFileSM = new StorageManager(VALID_FILE_DIRECTORY);
        invalidFileSM = new StorageManager(INVALID_FILE_DIRECTORY);
        emptySM = new StorageManager(EMPTY_FILE_DIRECTORY);
        invalidDirectorySM = new StorageManager(INVALID_TEST_DIRECTORY);

        userToLoad = null;
        userToSave = new User("Testing", "Male");

        watchlistListForLoad = new ArrayList<>();
        watchlistListForSave = new ArrayList<>();

        Watchlist firstWatchlist = new Watchlist("a");
        firstWatchlist.addAnimeToList(1);
        firstWatchlist.addAnimeToList(2);
        firstWatchlist.addAnimeToList(3);

        Watchlist secondWatchlist = new Watchlist("b");
        secondWatchlist.addAnimeToList(2);
        secondWatchlist.addAnimeToList(3);
        secondWatchlist.addAnimeToList(4);

        watchlistListForSave.add(firstWatchlist);
        watchlistListForSave.add(secondWatchlist);
    }

    @Test
    void testRetrieveWorkspaceList() {
        // Valid Workspace Directory
        String[] validWorkspaceList = invalidFileSM.retrieveWorkspaceList();
        assertEquals(2, validWorkspaceList.length);

        // Invalid Workspace Directory
        String[] invalidWorkspaceList = invalidDirectorySM.retrieveWorkspaceList();
        assertNotNull(invalidWorkspaceList);
    }

    // ========================== User Saving and Loading ==========================

    @Test
    void testSaveUser() throws AniException {
        validFileSM.saveUser(userToSave);
    }

    @Test
    void testLoadUser() throws AniException {
        // Invalid Directory
        assertThrows(AniException.class, () -> {
            userToLoad = invalidDirectorySM.loadUser();
            fail();
        });

        // Valid Directory (Use result from testSaveUser())
        userToLoad = validFileSM.loadUser();
        assertEquals(userToLoad.getName(), userToSave.getName());
        assertEquals(userToLoad.getGender(), userToSave.getGender());
    }

    @Test
    void loadUser_emptyUserFile_throwsAniException() {
        assertThrows(AniException.class, () -> {
            userToLoad = emptySM.loadUser();
            fail();
        });
    }

    @Test
    void loadUser_invalidUserFile_throwsAniException() {
        assertThrows(AniException.class, () -> {
            userToLoad = invalidFileSM.loadUser();
            fail();
        });
    }

    // ========================== Watchlist Saving and Loading ==========================

    @Test
    void testSaveWatchlistList() throws AniException {
        validFileSM.saveWatchlistList(VALID_WORKSPACE, watchlistListForSave);
    }

    @Test
    void testLoadWatchlistList() throws AniException {
        // Valid Watchlist File
        String validResult = validFileSM.loadWatchlistList(VALID_WORKSPACE, watchlistListForLoad);
        String expectedValidResult = "Loaded successfully.";
        assertEquals(expectedValidResult, validResult);

        // Invalid Directory
        assertThrows(AniException.class, () -> {
            invalidDirectorySM.loadWatchlistList(VALID_WORKSPACE, watchlistListForLoad);
            fail();
        });

        // Empty Watchlist File
        String emptyResult = emptySM.loadWatchlistList(EMPTY_FILE_WORKSPACE, watchlistListForLoad);
        String expectedEmptyFileResult = "Empty watchlist file.";
        assertEquals(expectedEmptyFileResult, emptyResult);

        // No Watchlist File
        assertThrows(AniException.class, () -> {
            emptySM.loadWatchlistList(EMPTY_WORKSPACE, watchlistListForLoad);
            fail();
        });
    }

    @Test
    void loadWatchlistList_someInvalidWatchlist_failToLoadSome() throws AniException {
        String someInvalidResult = invalidFileSM.loadWatchlistList(SOME_INVALID_WORKSPACE, watchlistListForLoad);
        String expectedSomeInvalidResult = "Not all loaded successfully (some invalid).";
        assertEquals(1, watchlistListForLoad.size());
        assertEquals(someInvalidResult, expectedSomeInvalidResult);
    }

    @Test
    void loadWatchlistList_allInvalidWatchlist_failToLoadAll() throws AniException {
        String allInvalidResult = invalidFileSM.loadWatchlistList(ALL_INVALID_WORKSPACE, watchlistListForLoad);
        String expectedAllInvalidResult = "No watchlist loaded successfully (all invalid).";
        assertEquals(0, watchlistListForLoad.size());
        assertEquals(allInvalidResult, expectedAllInvalidResult);
    }

    // ========================== Script Reading ==========================
    @Test
    void testReadScriptFile() throws AniException {
        // Valid Script File
        String fileString = validFileSM.readScript(VALID_WORKSPACE, SCRIPT_FILE_NAME);
        assertNotNull(fileString);

        // Invalid Directory
        assertThrows(AniException.class, () -> {
            invalidDirectorySM.readScript(VALID_WORKSPACE, SCRIPT_FILE_NAME);
            fail();
        });

        // Empty Script File
        assertThrows(AniException.class, () -> {
            emptySM.readScript(EMPTY_FILE_WORKSPACE, SCRIPT_FILE_NAME);
            fail();
        });
    }
}
