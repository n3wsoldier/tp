package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WatchlistParserTest {
    private WatchlistParser watchlistParser;

    @BeforeEach
    void setUp() {
        watchlistParser = new WatchlistParser();
    }

    @Test
    void parse_validParameters_success() throws AniException {
        watchlistParser.parse("-n correct");
        watchlistParser.parse("-l");
        watchlistParser.parse("-s 1");
        watchlistParser.parse("-d 1");
    }

    @Test
    void parse_invalidParameters_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse(""));
        assertThrows(AniException.class, () -> watchlistParser.parse("-invalid"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-n one -one two"));
        assertThrows(AniException.class, () -> watchlistParser.parse("hello -n testing"));

        assertThrows(AniException.class, () -> watchlistParser.parse("-n"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-s"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-d"));

        assertThrows(AniException.class, () -> watchlistParser.parse("-l a"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-s one"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-d one"));
    }

    @Test
    void parse_nullDescription_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> watchlistParser.parse(null));
    }
}
