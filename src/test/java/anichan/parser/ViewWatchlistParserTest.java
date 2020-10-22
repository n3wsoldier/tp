package anichan.parser;

import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ViewWatchlistParserTest {
    protected static final String EMPTY_DESCRIPTION = "";
    protected static final String EMPTY_FIELD = "-v";
    protected static final String INVALID_OPTION = "-n 1";
    protected static final String NON_INTEGER_FIELD_TEST = "-v Default";

    @Test
    void parse_emptyDescription_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(EMPTY_DESCRIPTION);
        });
    }

    @Test
    void parse_emptyField_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(EMPTY_FIELD);
        });
    }

    @Test
    void parse_invalidOption_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_OPTION);
        });
    }

    @Test
    void parse_nonIntegerField_throwsAniException() {
        AddToWatchlistParser testParse = new AddToWatchlistParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(NON_INTEGER_FIELD_TEST);
        });
    }
}