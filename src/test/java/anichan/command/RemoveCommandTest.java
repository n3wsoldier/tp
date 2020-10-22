package anichan.command;

import anichan.human.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.parser.RemoveCommandParser;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RemoveCommandTest {
    AnimeData animeData;
    User user;
    StorageManager storageManager;
    Bookmark bookmark;
    Workspace workspace;

    protected static final String ZERO_WATCHLIST_INDEX = "-d 0";
    protected static final String LARGE_WATCHLIST_INDEX = "-d 3";

    @BeforeEach
    void setUp() throws AniException {
        ArrayList<Anime> testList = new ArrayList<>();
        Anime testAnime1 = new Anime();
        Anime testAnime2 = new Anime();
        testList.add(testAnime1);
        testList.add(testAnime2);

        ArrayList<Integer> animeList = new ArrayList<>();
        animeList.add(0);
        Watchlist watchlist = new Watchlist("TestWatchlist", animeList);

        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(watchlist);

        bookmark = new Bookmark();
        workspace = new Workspace("TestWorkspace", watchlistList, bookmark);
        user = new User("TestUser", "Male");
        user.setActiveWorkspace(workspace);
        animeData = new AnimeData(testList);
        storageManager = new StorageManager("test");
    }

    @Test
    void execute_zeroInteger_throwsAniException() throws AniException {
        RemoveCommandParser testParser = new RemoveCommandParser();
        RemoveCommand testRemove = testParser.parse(ZERO_WATCHLIST_INDEX);
        assertThrows(AniException.class, () -> {
            testRemove.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_indexLargerThanWatchlistSize_throwsAniException() throws AniException {
        RemoveCommandParser testParser = new RemoveCommandParser();
        RemoveCommand testRemove = testParser.parse(LARGE_WATCHLIST_INDEX);
        assertThrows(AniException.class, () -> {
            testRemove.execute(animeData, storageManager, user);
        });
    }
}
