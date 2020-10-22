package anichan.human;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.watchlist.Watchlist;

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

    public String getBookmarkListInString(AnimeData animeData) {
        return bookmark.getListInString(animeData);
    }

    public void removeBookmarkEntry(Integer bookmarkIndex) {
        bookmark.removeAnimeBookmark(bookmarkIndex);
    }

    public void addBookmarkEntry(Integer animeIndex) {
        bookmark.addAnimeBookmark(animeIndex);
    }

    public Integer getBookmarkSize() {
        return bookmark.getBookmarkSize();
    }

    public Anime getAnimeFromBookmark(AnimeData animeData, Integer bookmarkIndex) {
        return bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex);
    }

    public void editBookmarkEpisode(Integer bookmarkIndex, Integer bookmarkEpisode) {
        bookmark.editAnimeBookmarkEpisode(bookmarkIndex, bookmarkEpisode);
    }

    public String getBookmarkInfo(AnimeData animeData, Integer bookmarkIndex) {
        return bookmark.getAnimeBookmarkInfo(animeData, bookmarkIndex);
    }

    public void addBookmarkNote(Integer bookmarkIndex, String note) {
        bookmark.addNote(bookmarkIndex, note);
    }

    public String getBookmarkNote(Integer bookmarkIndex) {
        return bookmark.getNoteInString(bookmarkIndex);

    }

    @Override
    public String toString() {
        return workspaceName;
    }


}
