package anichan.watchlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.bookmark.Bookmark;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WatchlistTest {
    private static final int SAMPLE_ANIME_INDEX_ONE = 1;
    private static final int SAMPLE_ANIME_INDEX_TWO = 2;

    private Watchlist firstWatchlist;
    private Watchlist secondWatchlist;

    @BeforeEach
    void setUp() {
        ArrayList<Integer> animeList = new ArrayList<>();
        animeList.add(SAMPLE_ANIME_INDEX_ONE);
        animeList.add(SAMPLE_ANIME_INDEX_TWO);

        firstWatchlist = new Watchlist("First Watchlist");
        secondWatchlist = new Watchlist("Second Watchlist", animeList);
    }

    @Test
    void testEqual() {
        // Test Equals on duplicated named watchlist.
        Watchlist duplicateNameWatchlist = new Watchlist("First Watchlist");
        assertTrue(duplicateNameWatchlist.equals(firstWatchlist));
        assertFalse(duplicateNameWatchlist.equals(secondWatchlist));

        // Test Equals on non-Watchlist object.
        Bookmark bookmark = new Bookmark();
        assertFalse(firstWatchlist.equals(bookmark));

        // Test Equals on Null object.
        assertFalse(firstWatchlist.equals(null));
    }
}
