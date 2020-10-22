package anichan.command;

import anichan.human.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.parser.BrowseParser;
import anichan.storage.StorageManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowseCommandTest {
    AnimeData animeData;
    User user;
    StorageManager storageManager;

    protected static final String LARGE_PAGE_NUM = "-p 9999";
    protected static final String NEGATIVE_PAGE_NUM = "-p -1";
    protected static final String ZERO_PAGE_NUM = "-p 0";

    @BeforeEach
    void setUp() {
        ArrayList<Anime> testList = new ArrayList<Anime>();
        Anime testAnime1 = new Anime();
        Anime testAnime2 = new Anime();
        testList.add(testAnime1);
        testList.add(testAnime2);
        animeData = new AnimeData(testList);
        storageManager = new StorageManager("test");
    }

    @Test
    void execute_invalidPageNum_ThrowsAniException() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(LARGE_PAGE_NUM);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(NEGATIVE_PAGE_NUM);
        });

        BrowseCommand testBrowse2 = testParse.parse(ZERO_PAGE_NUM);
        assertEquals(testBrowse2.getPage(), 1);
    }
}
