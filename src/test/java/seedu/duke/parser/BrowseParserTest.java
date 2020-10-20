package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.command.BrowseCommand;
import seedu.duke.exception.AniException;
import seedu.duke.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrowseParserTest {
    AnimeData animeData;
    StorageManager storageManager;

    protected static final String INVALID_PARAMETERS_TEST1 = "-n name";
    protected static final String INVALID_PARAMETERS_TEST2 = "-sort name";
    protected static final String INVALID_FIELD_TEST1 = "-s   ";
    protected static final String INVALID_FIELD_TEST2 = "-s beepboopbeep";
    protected static final String INVALID_FIELD_TEST3 = "-s -o -p";
    protected static final String DIFF_ORDER_TEST = "-p 1 -s rating -o asc";
    protected static final String DIFF_ORDER_TEST2 = "-s rating -o asc -p 1";

    @BeforeEach
    void setUp() {
        ArrayList<Anime> testList = new ArrayList<>();
        animeData = new AnimeData(testList);
        storageManager = new StorageManager("test");
    }

    @Test
    void parse_invalidParameter_ThrowsAniException() {
        BrowseParser testParse = new BrowseParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST1);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST2);
        });
    }

    @Test
    void parse_invalidField_ThrowsAniException() {
        BrowseParser testParse = new BrowseParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_FIELD_TEST1);
        });

        BrowseParser testParse2 = new BrowseParser();
        assertThrows(AniException.class, () -> {
            testParse2.parse(INVALID_FIELD_TEST2);
        });

        BrowseParser testParse3 = new BrowseParser();
        assertThrows(AniException.class, () -> {
            testParse3.parse(INVALID_FIELD_TEST3);
        });
    }

    @Test
    void parse_differentParameterOrder_identicalBrowseSettings() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(DIFF_ORDER_TEST);
        BrowseCommand testBrowse2 = testParse.parse(DIFF_ORDER_TEST2);

        assertEquals(testBrowse.getPage(), testBrowse2.getPage());
        assertEquals(testBrowse.getSortType(), testBrowse2.getSortType());
        assertEquals(testBrowse.getOrder(), testBrowse2.getOrder());
    }

}
