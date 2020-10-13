package seedu.duke.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.human.User;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StorageTest {
    private static final String INVALID_USER_PROFILE_FILE = "a/b/c/invalid_userprofile.txt";
    private static final String INVALID_WATCHLIST_FILE = "a/b/c/invalid_watchlist.txt";
    private static final String TEST_DIRECTORY = ".." + File.separator + ".." + File.separator +
                                                 "src" + File.separator + "test" + File.separator +
                                                 "data" + File.separator + "StorageTest" + File.separator;
    private static final String TEST_USER_PROFILE_FILE = TEST_DIRECTORY + "userprofile.txt";
    private static final String TEST_WATCHLIST_FILE = TEST_DIRECTORY + "watchlist.txt";

    private Ui ui;
    private Storage validStorage;
    private Storage invalidStorage;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        validStorage = new Storage(TEST_USER_PROFILE_FILE, TEST_WATCHLIST_FILE);
        invalidStorage = new Storage(INVALID_USER_PROFILE_FILE, INVALID_WATCHLIST_FILE);
    }

    @Test
    void loadUserProfile_validStorage_notNullObject() {
        User user = validStorage.loadUser(ui);
        String expected = "\nName: Tester\nBirthdate: 01/01/2020\nGender: Other";
        assertEquals(user.toString(), expected);
    }

    @Test
    void loadUserProfile_invalidStorage_nullObject() {
        User user = invalidStorage.loadUser(ui);
        assertNull(user);
    }

    @Test
    void loadWatchlist_validStorage_notNullObject() {
        ArrayList<Watchlist> watchlists = validStorage.loadWatchlist(ui);
        String expected = "Test List" + System.lineSeparator();
        expected += "1. Testing" + System.lineSeparator() + "2. The" + System.lineSeparator();
        expected += "3. Watchlist" + System.lineSeparator();
        assertEquals(watchlists.get(0).toString(), expected);
    }

    @Test
    void loadWatchlist_invalidStorage_notNullObject() {
        ArrayList<Watchlist> watchlists = invalidStorage.loadWatchlist(ui);
        assertNotNull(watchlists);
    }
}