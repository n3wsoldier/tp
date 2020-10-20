package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchParserTest {
    protected static final String INVALID_PARAMETERS_TEST1 = "-n ";
    protected static final String INVALID_PARAMETERS_TEST2 = "-a Cowboy Bebop";

    @Test
    void parse_invalidParameter_throwsAniException() {
        SearchParser testParse = new SearchParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST1);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST2);
        });
    }
}
