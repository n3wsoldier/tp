package seedu.duke.human;

import seedu.duke.bookmark.Bookmark;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class Workspace {


    public Bookmark bookmark;

    protected String workspaceName;


    private Watchlist activeWatchlist;
    private ArrayList<Watchlist> watchlistList;

    public Workspace(String workspaceName) {
        this.workspaceName = workspaceName;

        bookmark = new Bookmark();
        watchlistList = new ArrayList<>();
    }

    public Workspace(String workspaceName, ArrayList<Watchlist> watchlistList, Bookmark bookmark) {
        this.workspaceName = workspaceName;
        this.watchlistList = watchlistList;
        this.bookmark = bookmark;
    }

    public void setActiveWatchlist(Watchlist activeWatchlist) {
        this.activeWatchlist = activeWatchlist;
    }

    public void setWatchlistList(ArrayList<Watchlist> watchlistList) {
        this.watchlistList = watchlistList;
    }

    public String getName() {
        return workspaceName;
    }

    public Watchlist getActiveWatchlist() {
        return activeWatchlist;
    }

    public String getActiveWatchlistName() {
        return activeWatchlist.getName();
    }

    public ArrayList<Watchlist> getWatchlistList() {
        return watchlistList;
    }

    @Override
    public String toString() {
        return "\n Workspace: " + workspaceName;
    }
}
