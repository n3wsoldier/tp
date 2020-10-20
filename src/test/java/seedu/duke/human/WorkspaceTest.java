package seedu.duke.human;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkspaceTest {

    @Test
    public void testWorkspaceCreation() throws ParseException, AniException {
        Workspace testWorkspace = new Workspace("Translation Company A");

        assertEquals("Translation Company A", testWorkspace.getName());
    }
}
