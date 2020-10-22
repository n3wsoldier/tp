package anichan.anime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimeDataTest {
    AnimeData animeData;
    protected static final Integer NEGATIVE_INTEGER = -1;
    protected static final Integer NULL_PARAM = null;


    @BeforeEach
    void setUp() throws AniException {
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        animeStorageList.add(new Anime());
        animeStorageList.add(new Anime());
        animeData = new AnimeData(animeStorageList);
    }

    @Test
    void getAnime_negativeInteger_expectException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            animeData.getAnime(NEGATIVE_INTEGER);
        });
    }

    @Test
    void getAnime_nullInput_expectException() {
        assertThrows(NullPointerException.class, () -> {
            animeData.getAnime(NULL_PARAM);
        });
    }

    @Test
    void getAnimeByID_negativeInteger_expectException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            animeData.getAnimeByID(NEGATIVE_INTEGER);
        });
    }

    @Test
    void getAnimeByID_nullInput_expectException() {
        assertThrows(NullPointerException.class, () -> {
            animeData.getAnimeByID(NULL_PARAM);
        });
    }
}
