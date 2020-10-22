package anichan.command;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchlistCommand extends Command {
    private static final String CREATE_OPTION = "n";
    private static final String LIST_OPTION = "l";
    private static final String SELECT_OPTION = "s";    // Categorized as a Modification option.
    private static final String DELETE_OPTION = "d";    // Categorized as a Modification option.
    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistCommand.class.getName());

    private final String option;
    private final String optionInformation;

    public WatchlistCommand(String option, String optionInformation) {
        this.option = option;
        this.optionInformation = optionInformation;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        assert activeWorkspace.getWatchlistList() != null : "Watchlist list should not be null.";
        assert option != null : "option should not be null.";

        switch (option) {
        case CREATE_OPTION:
            return createWatchlist(storageManager, activeWorkspace);
        case LIST_OPTION:
            return listAllWatchlist(activeWorkspace);
        case SELECT_OPTION:
            return selectWatchlist(activeWorkspace);
        case DELETE_OPTION:
            return deleteWatchlist(storageManager, activeWorkspace);
        default:
            throw new AniException("Watchlist command only accepts the options: -n, -l, -s, and -d.");
        }
    }

    private String createWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        Watchlist createdWatchlist = new Watchlist(optionInformation);
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();

        boolean isWatchlistNameUnique = !watchlistList.contains(createdWatchlist);
        if (!isWatchlistNameUnique) {
            throw new AniException("There is already a watchlist named \"" + optionInformation + "\".");
        }

        watchlistList.add(createdWatchlist);
        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist \"" + optionInformation + "\" created successfully.");
        return "Watchlist \"" + optionInformation + "\" has been created successfully!";
    }

    private String listAllWatchlist(Workspace activeWorkspace) {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        if (watchlistList.size() == 0) {
            LOGGER.log(Level.INFO, "Empty watchlistList message because size is 0");
            return "Uhh.. You have no watchlist to list..";
        }

        StringBuilder sbWatchlistList = new StringBuilder();
        sbWatchlistList.append("Currently, you have ");
        sbWatchlistList.append(watchlistList.size()).append(" watchlist(s):");
        for (int i = 0; i < watchlistList.size(); i++) {
            Watchlist watchlist = watchlistList.get(i);
            sbWatchlistList.append(System.lineSeparator());
            sbWatchlistList.append("\t").append(i + 1).append(". ");
            sbWatchlistList.append(watchlist.getName());
        }

        LOGGER.log(Level.INFO, "Listing watchlist of size: " + watchlistList.size());
        return sbWatchlistList.toString();
    }

    private String selectWatchlist(Workspace activeWorkspace) throws AniException {
        int selectIndex = parseInteger(optionInformation);
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateModificationOption(watchlistList, selectIndex);

        Watchlist selectedWatchlist = watchlistList.get(selectIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        if (selectedWatchlist.equals(activeWatchlist)) {
            LOGGER.log(Level.INFO, "Select failed because the active watchlist is selected.");
            throw new AniException("Current active watchlist is \"" + selectedWatchlist.getName() + "\".");
        }

        activeWorkspace.setActiveWatchlist(selectedWatchlist);
        LOGGER.log(Level.INFO, "New active watchlist: " + activeWorkspace.getActiveWatchlistName());
        return "\"" + selectedWatchlist.getName() + "\" is now your active watchlist!";
    }

    private String deleteWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        int deleteIndex = parseInteger(optionInformation);
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateModificationOption(watchlistList, deleteIndex);

        Watchlist deletedWatchlist = watchlistList.get(deleteIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        watchlistList.remove(deleteIndex);

        String commandOutput = "Watchlist \"" + deletedWatchlist.getName() + "\" has been deleted successfully!";
        if (deletedWatchlist.equals(activeWatchlist)) {
            activeWorkspace.setActiveWatchlist(watchlistList.get(0));
            String activeWatchlistName = activeWorkspace.getActiveWatchlistName();
            commandOutput += System.lineSeparator();
            commandOutput += "Changed active watchlist to: \"" + activeWatchlistName + "\".";
        }

        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist: \"" + deletedWatchlist.getName() + "\" deleted successfully.");
        return commandOutput;
    }

    private void validateModificationOption(ArrayList<Watchlist> watchlistList, int index) throws AniException {
        if (watchlistList.size() == 0) {
            throw new AniException("You have no watchlist!");
        }

        if (watchlistList.size() == 1 && option.equals(DELETE_OPTION)) {
            throw new AniException("You cannot delete the last watchlist!");
        }

        if (index < 0 || index >= watchlistList.size()) {
            throw new AniException("\"" + (index + 1) + "\" is not a valid watchlist index.");
        }
    }

    private int parseInteger(String optionInformation) throws AniException {
        try {
            // Input received as one-based numbering, then converted to zero-based numbering.
            return Integer.parseInt(optionInformation) - 1;
        } catch (NumberFormatException exception) {
            throw new AniException("\"" + optionInformation + "\" is not a number!");
        }
    }
}
