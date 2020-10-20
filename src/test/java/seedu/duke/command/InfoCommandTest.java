package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.parser.InfoParser;
import seedu.duke.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InfoCommandTest {
    AnimeData animeData;
    StorageManager storageManager;
    User user;
    
    protected static final String ZERO_ANIME_INDEX = "-a 0";
    protected static final String LARGE_ANIME_INDEX = "-a 3";

    @BeforeEach
    void setUp() {
        ArrayList<Anime> testList = new ArrayList<>();
        Anime testAnime1 = new Anime();
        Anime testAnime2 = new Anime();
        testList.add(testAnime1);
        testList.add(testAnime2);
        
        animeData = new AnimeData(testList);
    }

    @Test
    void execute_zeroInteger_throwsAniException() throws AniException {
        InfoParser testParser = new InfoParser();
        InfoCommand testInfo = testParser.parse(ZERO_ANIME_INDEX);
        assertThrows(AniException.class, () -> {
            testInfo.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_indexLargerThanDataSize_throwsAniException() throws AniException {
        InfoParser testParser = new InfoParser();
        InfoCommand testInfo = testParser.parse(LARGE_ANIME_INDEX);
        assertThrows(AniException.class, () -> {
            testInfo.execute(animeData, storageManager, user);
        });
    }
}
