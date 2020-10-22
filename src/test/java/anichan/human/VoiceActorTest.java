package anichan.human;

import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoiceActorTest {

    @Test
    public void testVoiceActorCreation() throws AniException {
        VoiceActor yoshitsuguMatsuoka = new VoiceActor("Yoshitsugu Matsuoka");
        assertEquals("Yoshitsugu Matsuoka", yoshitsuguMatsuoka.toString());
    }

    @Test
    void getTotalCharacters_emptyList_returnZero() throws AniException {
        VoiceActor yoshitsuguMatsuoka = new VoiceActor("Yoshitsugu Matsuoka");
        assertEquals(0, yoshitsuguMatsuoka.getTotalCharacters());
    }

    @Test
    void getTotalCharacters_addTwo_returnTwo() throws AniException {
        VoiceActor yoshitsuguMatsuoka = new VoiceActor("Yoshitsugu Matsuoka");

        // Make characters
        Character kirito = new Character("Kirito");
        Character somaYukihira = new Character("Soma Yukihira");

        // Assign characters voiced by voice actor
        yoshitsuguMatsuoka.addCharacter(kirito); // Japanese va
        yoshitsuguMatsuoka.addCharacter(somaYukihira);

        assertEquals(2, yoshitsuguMatsuoka.getTotalCharacters());
    }
}
