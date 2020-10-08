package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterTest {

    @Test
    public void testCharacterCreation() throws DukeException {
        Character kirito = new Character("Kirito");
        assertEquals("Kirito", kirito.toString());
    }

    @Test
    void getTotalVoiceActors_emptyList_returnZero() throws DukeException {
        Character kirito = new Character("Kirito");
        assertEquals(0, kirito.getTotalVoiceActors());
    }

    @Test
    void getTotalVoiceActors_addTwo_returnTwo() throws DukeException {
        Character kirito = new Character("Kirito");

        // Make voice actors
        VoiceActor yoshitsuguMatsuoka = new VoiceActor("Yoshitsugu Matsuoka");
        VoiceActor brycePapenbrook = new VoiceActor("Bryce Papenbrook");

        // Assign characters voiced by voice actor
        kirito.addVoiceActor(yoshitsuguMatsuoka);
        kirito.addVoiceActor(brycePapenbrook);

        assertEquals(2, kirito.getTotalVoiceActors());
    }
}