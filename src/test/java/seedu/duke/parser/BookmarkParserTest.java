package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.command.BookmarkAnimeCommand;
import seedu.duke.exception.AniException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookmarkParserTest {
    protected static final String INVALID_FIRST_PARAMETERS_ADD_TEST1 = "1 -a 123";
    protected static final String INVALID_FIRST_PARAMETERS_ADD_TEST2 = "a -a 123";
    protected static final String INVALID_FIRST_PARAMETERS_DELETE_TEST1 = "1 -d 123";
    protected static final String INVALID_FIRST_PARAMETERS_DELETE_TEST2 = "a -d 123";
    protected static final String INVALID_FIELD_ADD_TEST1 = "-a   ";
    protected static final String INVALID_FIELD_ADD_TEST2 = "-a beepboopbeep";
    protected static final String INVALID_FIELD_ADD_TEST3 = "-a 123 123";
    protected static final String INVALID_FIELD_DELETE_TEST1 = "-d   ";
    protected static final String INVALID_FIELD_DELETE_TEST2 = "-d beepboopbeep";
    protected static final String INVALID_FIELD_DELETE_TEST3 = "-d 123 123";
    protected static final String INVALID_FIELD_EDIT_TEST1 = "1 -e   ";
    protected static final String INVALID_FIELD_EDIT_TEST2 = "1 -e beepboopbeep";
    protected static final String INVALID_FIELD_EDIT_TEST3 = "1 -e 123 123";
    protected static final String NEGATIVE_BOOKMARKID_DELETE_TEST = "-d -1";
    protected static final String NEGATIVE_ANIMEID_ADD_TEST = "-a -3";
    protected static final String NEGATIVE_BOOKMARKID_EDIT_TEST = "-1 -e 1";

    protected static final String PROPER_INPUT1 = "-a 410";
    protected static final String PROPER_INPUT2 = "-a 430";

    @Test
    void execute_invalidFirstParameter_ThrowsAniException() throws AniException {
        BookmarkParser testAdd1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testAdd1.parse(INVALID_FIRST_PARAMETERS_ADD_TEST1);
        });

        BookmarkParser testAdd2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testAdd2.parse(INVALID_FIRST_PARAMETERS_ADD_TEST2);
        });

        BookmarkParser testDelete1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testDelete1.parse(INVALID_FIRST_PARAMETERS_DELETE_TEST1);
        });

        BookmarkParser testDelete2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testDelete2.parse(INVALID_FIRST_PARAMETERS_DELETE_TEST2);
        });
    }

    @Test
    void execute_invalidFieldForAdd_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_ADD_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_ADD_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_ADD_TEST3);
        });
    }

    @Test
    void execute_invalidFieldForDelete_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_DELETE_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_DELETE_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_DELETE_TEST3);
        });
    }

    @Test
    void execute_invalidFieldForEdit_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_EDIT_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_EDIT_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_EDIT_TEST3);
        });
    }

    @Test
    void execute_negativeBookmarkIdForDelete_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_BOOKMARKID_DELETE_TEST);
        });
    }

    @Test
    void execute_negativeBookmarkIdForEdit_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_BOOKMARKID_EDIT_TEST);
        });
    }

    @Test
    void execute_negativeAnimeIdForAdd_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_ANIMEID_ADD_TEST);
        });
    }
}
