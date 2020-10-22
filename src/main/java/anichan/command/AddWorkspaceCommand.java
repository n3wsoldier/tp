package anichan.command;

import anichan.human.User;
import anichan.human.Workspace;
import anichan.watchlist.Watchlist;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.storage.StorageManager;

import java.util.ArrayList;

public class AddWorkspaceCommand extends Command {

    protected String newWorkspaceName;

    public AddWorkspaceCommand() {
    }

    public void setWorkspaceName(String newWorkspaceName) {
        this.newWorkspaceName = newWorkspaceName;
    }

    public String getNewWorkspaceName() {
        return newWorkspaceName;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace newWorkspace = user.addWorkspace(newWorkspaceName.trim());

        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("Default"));
        newWorkspace.setWatchlistList(watchlistList);
        storageManager.saveWorkspace(newWorkspace);

        return "Successfully added new workspace: " + newWorkspace;
    }
}
