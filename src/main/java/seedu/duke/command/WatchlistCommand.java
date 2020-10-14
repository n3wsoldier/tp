package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class WatchlistCommand extends Command {
    private static final String CREATE_OPTION = "-n";

    private final String option;
    private String optionInformation;

    public WatchlistCommand(String description) {
        String[] descriptionSplit = description.split(" ", 2);
        option = descriptionSplit[0];
        optionInformation = "";
        if (descriptionSplit.length == 2) {
            optionInformation = descriptionSplit[1];
        }
    }

    @Override
    public String execute(AnimeData animeData, ArrayList<Watchlist> activeWatchlistList, Watchlist activeWatchlist,
                          UserManagement userManagement) throws AniException {
        if (CREATE_OPTION.equals(option)) {
            createWatchlist(userManagement.getStorage(), activeWatchlistList);
            return "Watchlist created successfully!";
        } else {
            throw new AniException("Watchlist command only accepts the option: \"-n\".");
        }
    }

    public void createWatchlist(Storage storage, ArrayList<Watchlist> activeWatchlistList) throws AniException {
        if (optionInformation.isBlank()) {
            throw new AniException("Watchlist name cannot be empty.");
        }

        Watchlist newWatchlist = new Watchlist(optionInformation);
        activeWatchlistList.add(newWatchlist);
        storage.saveWatchlist(activeWatchlistList);
    }
}