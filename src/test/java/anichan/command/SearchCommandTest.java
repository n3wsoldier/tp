package anichan.command;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchCommandTest {
    AnimeData animeData;
    User user;
    StorageManager storageManager;

    protected static final int ABOVE_RANGE = 9999;
    protected static final int BELOW_RANGE = -1;

    @BeforeEach
    void setUp() {
        ArrayList<Anime> testList = new ArrayList<>();
        Anime testAnime1 = new Anime();
        Anime testAnime2 = new Anime();
        testList.add(testAnime1);
        testList.add(testAnime2);
        animeData = new AnimeData(testList);
        storageManager = new StorageManager("test");
    }

    @Test
    void execute_invalidSearchType_throwsAniExcetion() {
        SearchCommand testSearch = new SearchCommand();
        testSearch.setSearchTerm("test");
        testSearch.setSearchType(ABOVE_RANGE);
        assertThrows(AniException.class, () -> {
            testSearch.execute(animeData, storageManager, user);
        });

        testSearch.setSearchType(BELOW_RANGE);
        assertThrows(AniException.class, () -> {
            testSearch.execute(animeData, storageManager, user);
        });
    }
}
