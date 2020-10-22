package anichan.storage;

import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageManager {
    private static final Logger LOGGER = AniLogger.getAniLogger(StorageManager.class.getName());

    private final String storageDirectory;
    private final UserStorage userStorage;
    private final WatchlistStorage watchlistStorage;
    private final BookmarkStorage bookmarkStorage;
    private final ScriptStorage scriptStorage;

    public StorageManager(String storageDirectory) {
        this.storageDirectory = storageDirectory;
        userStorage = new UserStorage(this.storageDirectory);
        watchlistStorage = new WatchlistStorage(this.storageDirectory);
        bookmarkStorage = new BookmarkStorage(this.storageDirectory);
        scriptStorage = new ScriptStorage(this.storageDirectory);
    }

    public String[] retrieveWorkspaceList() {
        File file = new File(storageDirectory);
        String[] workspaceList = file.list((current, name) -> new File(current, name).isDirectory());
        if (workspaceList == null) {
            LOGGER.log(Level.INFO, "Found 0 workspace.");
            return new String[0];
        }

        LOGGER.log(Level.INFO, "Found " + workspaceList.length + " workspace(s).");
        return workspaceList;
    }

    // ========================== User Saving and Loading ==========================

    public void saveUser(User user) throws AniException {
        userStorage.save(user);
    }

    public User loadUser() throws AniException {
        return userStorage.load();
    }

    // ========================== Workspace Saving ==========================

    public void saveWorkspace(Workspace workspace) throws AniException {
        new File(storageDirectory + workspace.getName()).mkdirs();
        watchlistStorage.save(workspace.getName(), workspace.getWatchlistList());
    }

    // ========================== Watchlist Saving and Loading ==========================

    public void saveWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        watchlistStorage.save(workspaceName, watchlistList);
    }

    public String loadWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        return watchlistStorage.load(workspaceName, watchlistList);
    }

    // ========================== Bookmark Saving and Loading ==========================

    public void saveBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        bookmarkStorage.save(workspaceName, bookmark);
    }

    public String loadBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        return bookmarkStorage.load(workspaceName, bookmark);
    }

    // ========================== Script Loading ==========================

    public String loadScript(String workspaceName, String fileName) throws AniException {
        return scriptStorage.readScript(workspaceName, fileName);
    }
}
