package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EstimateParserTest {
    private EstimateParser estimateParser;

    @BeforeEach
    void setUp() {
        estimateParser = new EstimateParser();
    }

    @Test
    void parse_validParameters_success() throws AniException {
        estimateParser.parse("script.txt");
        estimateParser.parse("random_script.txt -wph 777");
    }

    @Test
    void parse_invalidParameters_throwsAniException() {
        assertThrows(AniException.class, () -> estimateParser.parse(""));
        assertThrows(AniException.class, () -> estimateParser.parse("script"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -one -two"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt script_two.txt"));

        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -o"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph one"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 0"));
        assertThrows(AniException.class, () -> estimateParser.parse("script.txt -wph 777 0"));
    }

    @Test
    void parse_nullDescription_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> estimateParser.parse(null));
    }
}
