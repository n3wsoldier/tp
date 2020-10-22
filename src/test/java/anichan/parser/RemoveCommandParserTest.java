package anichan.parser;

import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RemoveCommandParserTest {
    protected static final String INVALID_PARAMETERS_TEST1 = "";
    protected static final String INVALID_PARAMETERS_TEST2 = "-d";
    protected static final String INVALID_PARAMETERS_TEST3 = "-n 1";
    protected static final String INVALID_FIELD_TEST1 = "-d Gundam";

    @Test
    void parse_emptyDescription_throwsAniException() {
        RemoveCommandParser testParse = new RemoveCommandParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST1);
        });
    }

    @Test
    void parse_emptyField_throwsAniException() {
        RemoveCommandParser testParse = new RemoveCommandParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST2);
        });
    }

    @Test
    void parse_invalidOption_throwsAniException() {
        RemoveCommandParser testParse = new RemoveCommandParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_PARAMETERS_TEST3);
        });
    }

    @Test
    void parse_nonIntegerField_throwsAniException() {
        RemoveCommandParser testParse = new RemoveCommandParser();
        assertThrows(AniException.class, () -> {
            testParse.parse(INVALID_FIELD_TEST1);
        });
    }
}
