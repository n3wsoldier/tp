package anichan.parser;

import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    public void parseUserInput_emptyInput_showInvalidCommand() {
        Parser parser = new Parser();

        String input = "";

        assertThrows(AniException.class, () -> {
            parser.parseUserInput(input);
        });
    }

    @Test
    public void parseUserInput_oneWord_returnsOneString() throws AniException {
        Parser parser = new Parser();

        String input = "test";
        String[] stringParts = parser.parseUserInput(input);

        assertEquals(stringParts.length, 1);
    }

    @Test
    public void parseUserInput_moreThanTwoWords_returnsTwoStrings() throws AniException {
        Parser parser = new Parser();

        String input = "test more than two";
        String[] stringParts = parser.parseUserInput(input);

        assertEquals(stringParts.length, 2);
    }
}
