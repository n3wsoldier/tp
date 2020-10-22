package anichan.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.parser.AddWorkspaceParser;
import anichan.storage.StorageManager;

import java.io.File;
import java.util.ArrayList;

class AddWorkspaceCommandTest {
    private static final String VALID_TEST_DIRECTORY = "src" + File.separator + "test" + File.separator
                                                       + "data" + File.separator;

    AnimeData animeData;
    User user;
    StorageManager storageManager;

    @BeforeEach
    void setUp() throws AniException {
        ArrayList<Anime> testList = new ArrayList<>();
        Anime testAnime1 = new Anime();
        testList.add(testAnime1);
        animeData = new AnimeData(testList);
        storageManager = new StorageManager(VALID_TEST_DIRECTORY);
        user = new User("Tom", "Male");
    }

    @Test
    void execute_validName_ThrowsAniException() throws AniException {
        AddWorkspaceParser testParse = new AddWorkspaceParser();

        AddWorkspaceCommand testAddWorkspace = testParse.parse("-n Crunchy rail 12345");
        Assertions.assertDoesNotThrow(() -> testAddWorkspace.execute(animeData, storageManager, user));
    }
}
