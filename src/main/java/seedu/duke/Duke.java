package seedu.duke;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.anime.AnimeStorage;
import seedu.duke.command.Command;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.UserManagement;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String USER_PROFILE_FILE_NAME = "userprofile.txt";
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String ANIME_DATA_SOURCE_FOLDER = "/src/main/resources/Data";

    private final Ui ui;
    private final Parser parser;
    private final Storage storage;

    private AnimeStorage animeStorage;
    private AnimeData animeData;

    private UserManagement userManagement;
    private User activeUser;
    private Watchlist activeWatchlist;
    private ArrayList<Watchlist> activeWatchlistList;

    public Duke() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        userManagement = new UserManagement(storage);

        // Load user and watchlist list.
        ui.printWelcomeMessage();
        ui.printHorizontalLine();
        userManagement.setActiveUser(storage.loadUser(ui));
        activeWatchlistList = storage.loadWatchlist(ui);
        ui.printHorizontalLine();

        // Initial SET UP for AnimeStorage / WatchLists / UserManagement / bookmark

        assert userManagement != null;
        // Create new user if no existing user detected
        activeUser = userManagement.getActiveUser();
        if (activeUser == null) {
            userManagement.addUserDialogue(ui);
            activeUser = userManagement.getActiveUser();
            assert userManagement.getActiveUser() != null;
        }

        if (activeWatchlistList.isEmpty()) {
            activeWatchlist = new Watchlist("Default");
            activeWatchlistList.add(activeWatchlist);
            try {
                storage.saveWatchlist(activeWatchlistList);
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        } else {
            activeWatchlist = activeWatchlistList.get(0);
        }

        try {
            animeStorage = new AnimeStorage(ANIME_DATA_SOURCE_FOLDER);
            animeData = new AnimeData(animeStorage.readAnimeDatabase());
        } catch (IOException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    public void run() {
        boolean shouldExit = false;
        while (!shouldExit) {
            try {
                String userInput = ui.readUserInput(activeUser.getHonorificName(), activeWatchlist.getName());
                Command command = parser.getCommand(userInput);
                String commandOutput = command.execute(animeData, activeWatchlistList, activeWatchlist, userManagement);
                ui.printMessage(commandOutput);
                shouldExit = command.getShouldExit();
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        }
        ui.printGoodbyeMessage();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    // TODO: Organize the methods below to their respective class.
    // TEMPORARY, REMOVE ALL BELOW WHEN DONE REFACTORING!
    private static final Scanner CONSOLE = new Scanner(System.in);

    //Sample Usage of AnimeList Class [To Be Deleted]
    private void createAnimeList() {
        System.out.println("===Running Sample Anime List Class===");
        ArrayList<Anime> animeStorageList = new ArrayList<>();
        String[] releaseDate = {"2020", "12", "30"};
        String[] genre = {"Science", "Action", "Dance"};
        String animeName = "Adventures of Adventurers";
        int animeID = 1;
        int rating = 65;
        int avgEpisodeLength = 30;
        int totalEpisodes = 24;
        Anime testAnime = new Anime(animeName, releaseDate, rating, genre,
                avgEpisodeLength, totalEpisodes);

        String[] releaseDate2 = {"1997", "4", "15"};
        String[] genre2 = {"Adventure", "Action", "Dance"};
        String animeName2 = "Actions in Action";
        animeID = 2;
        int rating2 = 80;
        int avgEpisodeLength2 = 20;
        int totalEpisodes2 = 13;
        Anime testAnime2 = new Anime(animeName2, releaseDate2, rating2, genre2,
                avgEpisodeLength2, totalEpisodes2);

        animeStorageList.add(testAnime);
        animeStorageList.add(testAnime2);

        AnimeData animeData = new AnimeData(animeStorageList);
        animeData.printAll();

        System.out.println("===End of Sample Anime List Class===");
    }
}
