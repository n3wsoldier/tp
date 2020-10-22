package anichan.command;

import anichan.human.User;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.parser.BookmarkParser;
import anichan.storage.StorageManager;
import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkAnimeCommand extends Command {

    private static final String ANIME_ID_ERROR = " Anime index is outside AnimeData range (too big or too small).";
    private static final String BOOKMARK_ID_ERROR = " Bookmark index is outside Bookmark range (too big or too small).";
    private static final String BOOKMARK_EXECUTE_ERROR_HEADER = "Bookmark command execute failed:";
    private static final String BOOKMARK_ERROR_MESSAGE = " provided is invalid.";
    private static final String BOOKMARK_EXECUTE_EDIT = "Executing Edit Episode.";
    private static final String BOOKMARK_EXECUTE_ADD = "Executing Add Anime to Bookmark.";
    private static final String BOOKMARK_EXECUTE_DELETE = "Executing Delete Anime from Bookmark.";
    private static final String BOOKMARK_EXECUTE_LIST = "Executing List all anime in Bookmark.";
    private static final String BOOKMARK_EXECUTE_SUCCESS = "Execute Bookmark command successful.";
    private static final String BOOKMARK_LIST_HEADER = "Listing all anime in bookmark:";
    private int bookmarkIndex;
    private int animeIndex;
    private int bookmarkEpisode;
    private String bookmarkAction;
    private String bookmarkNote;
    private static final Logger LOGGER = getAniLogger(BookmarkAnimeCommand.class.getName());

    public BookmarkAnimeCommand() {
        // Set log levels
        // LOGGER.setLevel(Level.WARNING);
        bookmarkAction = null;
        LOGGER.log(Level.INFO, "Successfully loaded fields for Bookmark command.");
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        String result = "";
        Bookmark bookmark = user.getActiveWorkspace().bookmark;
        Workspace workspace = user.getActiveWorkspace();
        switch (bookmarkAction) {
        case BookmarkParser.EPISODE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_EDIT);
            result = editBookmarkEpisode(animeData, workspace);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.ADD_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_ADD);
            result = addBookmarkEntry(animeData, workspace);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.DELETE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_DELETE);
            result = deleteBookmarkEntry(animeData, workspace);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.LIST_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_LIST);
            result = BOOKMARK_LIST_HEADER;
            String bookmarkList = listBookmark(animeData, workspace);
            result += bookmarkList;
            break;
        case BookmarkParser.INFO_PARAM:
            LOGGER.log(Level.INFO, "Executing anime info for bookmark");
            result = "Here is the information for that anime." + System.lineSeparator();
            String animeInfo = getAnimeInfoFromBookmark(animeData, workspace);
            result += animeInfo + System.lineSeparator() + System.lineSeparator();

            result += "Notes for anime:";
            String notesInfo = getAnimeNotesFromBookmark(workspace);
            result += notesInfo;
            break;
        case BookmarkParser.ADD_NOTE_PARAM:
            LOGGER.log(Level.INFO, "Executing bookmark add note");
            result = addNoteToBookmark(animeData, workspace);
            break;
        default:
            break;
        }
        LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_SUCCESS);
        return result;
    }

    private String getAnimeNotesFromBookmark(Workspace workspace) {
        String notesInfo = workspace.getBookmarkNote(bookmarkIndex - 1);
        return notesInfo;
    }

    private String addNoteToBookmark(AnimeData animeData, Workspace workspace) throws AniException {
        String result;
        checkBookmarkIndex(workspace);
        workspace.addBookmarkNote(bookmarkIndex - 1, bookmarkNote);
        Anime animeToDelete = workspace.getAnimeFromBookmark(animeData, bookmarkIndex - 1);
        result = "Adding note:\"" + bookmarkNote + "\" to " + animeToDelete.getAnimeName() + "!";
        return result;
    }

    private String getAnimeInfoFromBookmark(AnimeData animeData, Workspace workspace) throws AniException {
        checkBookmarkIndex(workspace);
        String animeInfo = workspace.getBookmarkInfo(animeData, bookmarkIndex - 1);
        return animeInfo;
    }

    private String deleteBookmarkEntry(AnimeData animeData, Workspace workspace) throws AniException {
        checkBookmarkIndex(workspace);
        String result;
        Anime animeToDelete = workspace.getAnimeFromBookmark(animeData, bookmarkIndex - 1);
        result = "Removing " + animeToDelete.getAnimeName() + "! :(";
        workspace.removeBookmarkEntry(bookmarkIndex - 1);
        return result;
    }


    private String addBookmarkEntry(AnimeData animeData, Workspace workspace) throws AniException {
        checkAnimeIndex(animeData);
        String result;
        Anime animeToAdd = animeData.getAnimeByID(animeIndex - 1);
        result = "Saving " + animeToAdd.getAnimeID() + ". " + animeToAdd.getAnimeName() + " to bookmark.";
        workspace.addBookmarkEntry(animeIndex - 1);
        return result;
    }

    private String editBookmarkEpisode(AnimeData animeData, Workspace workspace) throws AniException {
        checkBookmarkIndex(workspace);
        String result;
        assert bookmarkEpisode >= 0 : "bookmarkEpisode should be positive";
        workspace.editBookmarkEpisode(bookmarkIndex - 1, bookmarkEpisode);
        Anime animeToEdit = workspace.getAnimeFromBookmark(animeData, bookmarkIndex - 1);
        result = "Editing " + animeToEdit.getAnimeName() + " to have " + bookmarkEpisode + " episode";
        return result;
    }

    private void checkBookmarkIndex(Workspace workspace) throws AniException {
        //Bookmark index is one based numbering
        if (bookmarkIndex > workspace.getBookmarkSize() || bookmarkIndex <= 0) {
            String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + BOOKMARK_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
    }

    private void checkAnimeIndex(AnimeData animeData) throws AniException {
        //Anime index is one based numbering
        if (animeIndex > animeData.getSize() || animeIndex <= 0) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        }
    }

    private String listBookmark(AnimeData animeData, Workspace workspace) throws AniException {
        return workspace.getBookmarkListInString(animeData);
    }

    public String getBookmarkAction() {
        return this.bookmarkAction;
    }

    public void setBookmarkAction(String actionString) {
        this.bookmarkAction = actionString;
    }

    public void setBookmarkIndex(String bookmarkIndexString) {
        this.bookmarkIndex = Integer.parseInt(bookmarkIndexString);
    }

    public void setAnimeIndex(String animeIndexString) {
        this.animeIndex = Integer.parseInt(animeIndexString);
    }

    public void setBookmarkEpisode(String bookmarkEpisodeString) {
        this.bookmarkEpisode = Integer.parseInt(bookmarkEpisodeString);
    }

    public void setBookmarkNote(String note) {
        this.bookmarkNote = note;
    }
}
