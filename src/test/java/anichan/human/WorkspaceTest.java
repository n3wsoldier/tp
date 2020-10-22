package anichan.human;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkspaceTest {

    @Test
    public void testWorkspaceCreation() {
        Workspace testWorkspace = new Workspace("Translation Company A");

        assertEquals("Translation Company A", testWorkspace.toString());
    }

}
