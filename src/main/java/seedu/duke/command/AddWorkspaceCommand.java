package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.Workspace;
import seedu.duke.storage.StorageManager;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class AddWorkspaceCommand extends Command {

    protected String newWorkspaceName;

    public AddWorkspaceCommand() {
    }

    public void setWorkspaceName(String newWorkspaceName) {
        this.newWorkspaceName = newWorkspaceName;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace newWorkspace = user.addWorkspace(newWorkspaceName.trim());

        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("Default"));
        newWorkspace.setWatchlistList(watchlistList);
        storageManager.saveWorkspace(newWorkspace);

        return "Successfully added new workspace:" + newWorkspace;
    }
}
