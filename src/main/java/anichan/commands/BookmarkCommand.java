package anichan.commands;

import anichan.human.User;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.parser.BookmarkParser;
import anichan.storage.StorageManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command that allows the user to bookmark an anime or add additional information.
 */
public class BookmarkCommand extends Command {

    private static final String ANIME_ID_ERROR = " Anime index is outside AnimeData range "
            + "(Bigger than number of anime).";
    private static final String ANIME_ID_EXIST_ERROR = " Anime index is already in bookmark.";
    private static final String BOOKMARK_ID_ERROR = " Bookmark index is outside Bookmark range "
            + "(Bigger than bookmark entries).";
    private static final String NOTES_ID_ERROR = " Note index is outside Bookmark range "
            + "(Bigger than number of notes).";
    private static final String BOOKMARK_EPISODE_ERROR = " is invalid." + System.lineSeparator()
            + "Episode provided is bigger than the total episode.";
    private static final String BOOKMARK_EXECUTE_ERROR_HEADER = "Bookmark command execute failed:";
    private static final String BOOKMARK_ERROR_MESSAGE = " provided is invalid.";
    private static final String BOOKMARK_NOTE_ERROR_MESSAGE = " provided contain \"~\" which is not allowed.";
    private static final String BOOKMARK_EXECUTE_EDIT = "Executing Edit Episode.";
    private static final String BOOKMARK_EXECUTE_ADD = "Executing Add Anime to Bookmark.";
    private static final String BOOKMARK_EXECUTE_DELETE = "Executing Delete Anime from Bookmark.";
    private static final String BOOKMARK_EXECUTE_LIST = "Executing List all anime in Bookmark.";
    private static final String BOOKMARK_EXECUTE_INFO = "Executing anime info for bookmark.";
    private static final String BOOKMARK_EXECUTE_ADD_NOTE = "Executing bookmark add note.";
    private static final String BOOKMARK_EXECUTE_REMOVE_NOTE = "Executing bookmark remove note.";
    private static final String BOOKMARK_EXECUTE_SUCCESS = "Execute Bookmark command successful.";
    private static final String BOOKMARK_LIST_HEADER = "Listing all anime in bookmark:";
    private static final String BOOKMARK_INFO_HEADER = "Here is the information for that anime.";
    private static final String BOOKMARK_NOTE_FORBIDDEN_CHAR = "~";
    public static final String EPISODE_HEADER = "Current Episode: ";
    public static final String NOTES_HEADER = "Notes for anime:";
    public static final String ANIME_ID_ZERO_ERROR = " Anime index start from 1.";
    public static final String NOTE_ID_ZERO_ERROR = " Note index start from 1.";
    public static final String BOOKMARK_ID_ZERO_ERROR = " Bookmark index start from 1.";
    public static final int ZERO_PARAM = 0;
    private int bookmarkIndex;
    private int animeIndex;


    private int noteIndex;
    private int bookmarkEpisode;
    private String bookmarkAction;
    private String bookmarkNote;
    private static final Logger LOGGER = AniLogger.getAniLogger(BookmarkCommand.class.getName());

    public BookmarkCommand() {
        // Set log levels
        // LOGGER.setLevel(Level.WARNING);
        bookmarkAction = null;
        LOGGER.log(Level.INFO, "Successfully loaded fields for Bookmark command.");
    }

    /**
     * Handles the main execution of bookmark command using the bookmark action.
     * <ul>
     *     <li>e: edit bookmark episode</li>
     *     <li>a: add a bookmark entry</li>
     *     <li>d: delete a bookmark entry</li>
     *     <li>l: list all bookmark entry</li>
     *     <li>i: display all information for a bookmark entry </li>
     *     <li>n: add a note to bookmark entry</li>
     *     <li>r: remove a note from bookmark entry</li>
     * </ul>
     *
     * @param animeData      used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user           used to modify user data
     * @return a printable string that contains the bookmark output message
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        String result = "";
        Workspace workspace = user.getActiveWorkspace();
        Bookmark bookmark = user.getActiveWorkspace().getBookmark();
        switch (bookmarkAction) {
        case BookmarkParser.EPISODE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_EDIT);
            result = editBookmarkEpisode(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.ADD_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_ADD);
            result = addBookmarkEntry(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.DELETE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_DELETE);
            result = deleteBookmarkEntry(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.LIST_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_LIST);
            result = BOOKMARK_LIST_HEADER;
            String bookmarkList = listBookmark(animeData, bookmark);
            result += bookmarkList;
            break;
        case BookmarkParser.INFO_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_INFO);
            result = BOOKMARK_INFO_HEADER + System.lineSeparator();
            result += getBookmarkInfo(animeData, bookmark);
            break;
        case BookmarkParser.ADD_NOTE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_ADD_NOTE);
            result = addNoteToBookmark(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.REMOVE_NOTE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_REMOVE_NOTE);
            result = removeNoteFromBookmark(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        default:
            break;
        }
        LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_SUCCESS);
        return result;
    }

    /**
     * Retrieve the information of bookmark entry.
     * Information retrieve is anime info, episode info and anime notes.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return a printable string that contain information of bookmark entry
     * @throws AniException when an error occurred while executing the command
     */
    private String getBookmarkInfo(AnimeData animeData, Bookmark bookmark) throws AniException {
        String result = "";
        String animeInfo = getAnimeInfoFromBookmark(animeData, bookmark);
        result += animeInfo + System.lineSeparator() + System.lineSeparator();

        Integer bookmarkEpisodeInfo = bookmark.getBookmarkEpisode(bookmarkIndex - 1);
        if (bookmarkEpisodeInfo != 0) {
            result += EPISODE_HEADER;
            result += bookmarkEpisodeInfo;
            result += System.lineSeparator() + System.lineSeparator();
        }

        result += NOTES_HEADER;
        String notesInfo = getAnimeNotesFromBookmark(bookmark);
        result += notesInfo;
        return result;
    }

    /**
     * Retrieve all Notes from a bookmark entry.
     *
     * @param bookmark used to manage bookmark entries
     * @return the notes in list
     */
    private String getAnimeNotesFromBookmark(Bookmark bookmark) {
        String notesInfo = bookmark.getNoteInString(bookmarkIndex - 1);
        return notesInfo;
    }

    /**
     * Remove note from a bookmark entry.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return message of removing note
     * @throws AniException when an error occurred while executing the command
     */
    private String removeNoteFromBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        checkNoteIndex(bookmark);
        String result = "Deleting note: ";
        result += bookmark.removeNote(bookmarkIndex - 1, noteIndex - 1);
        Anime animeToRemoveNote = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result += System.lineSeparator() + "From: " + animeToRemoveNote.getAnimeName() + ".";
        return result;
    }

    /**
     * Add a note to a bookmark entry.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return message of adding note
     * @throws AniException when an error occurred while executing the command
     */
    private String addNoteToBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        checkNoteForForbiddenChar();
        bookmark.addNote(bookmarkIndex - 1, bookmarkNote);
        Anime animeToDelete = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        String result;
        result = "Adding note:\"" + bookmarkNote + "\""
                + " to " + animeToDelete.getAnimeName() + "!";
        return result;
    }

    /**
     * Retrieve anime info from bookmark entry.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return the anime information in string
     * @throws AniException when an error occurred while executing the command
     */
    private String getAnimeInfoFromBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String animeInfo = bookmark.getAnimeBookmarkInfo(animeData, bookmarkIndex - 1);
        return animeInfo;
    }

    /**
     * Delete a bookmark entry.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return delete bookmark entry message
     * @throws AniException when an error occurred while executing the command
     */
    private String deleteBookmarkEntry(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String result;
        Anime animeToDelete = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result = "Removing " + animeToDelete.getAnimeName() + "! :(";
        bookmark.removeAnimeBookmark(bookmarkIndex - 1);
        return result;
    }

    /**
     * Add a bookmark entry.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return add bookmark entry message
     * @throws AniException when an error occurred while executing the command
     */
    private String addBookmarkEntry(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkAnimeIndex(animeData);
        checkAnimeNotInBookmark(bookmark);
        String result;
        bookmark.addAnimeBookmark(animeIndex - 1);
        Anime animeToAdd = animeData.getAnime(animeIndex - 1);
        result = "Saving " + animeToAdd.getAnimeID() + ". " + animeToAdd.getAnimeName() + " to bookmark.";
        return result;
    }

    /**
     * Check that the bookmark does not already have the bookmark entry.
     *
     * @param bookmark used to manage bookmark entries
     * @throws AniException if there is bookmark entry already exist
     */
    private void checkAnimeNotInBookmark(Bookmark bookmark) throws AniException {
        if (bookmark.checkExist(animeIndex - 1)) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_EXIST_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        }
    }

    /**
     * Edit bookmark episode.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return edit bookmark episode message
     * @throws AniException when an error occurred while executing the command
     */
    private String editBookmarkEpisode(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String result;
        Anime animeToEdit = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        checkEpisode(animeToEdit.getTotalEpisodes());
        bookmark.editAnimeBookmarkEpisode(bookmarkIndex - 1, bookmarkEpisode);
        result = "Editing " + animeToEdit.getAnimeName() + " to have " + bookmarkEpisode + " episode(s).";
        return result;
    }

    /**
     * Check that the bookmark index is valid.
     *
     * @param bookmark used to manage bookmark entries
     * @throws AniException if bookmark id is outside the number of bookmark
     */
    private void checkBookmarkIndex(Bookmark bookmark) throws AniException {
        //Bookmark index is one based numbering
        if (bookmarkIndex > bookmark.getBookmarkSize() ) {
            String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + BOOKMARK_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        } else if (bookmarkIndex == ZERO_PARAM){
            String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + BOOKMARK_ID_ZERO_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
    }

    /**
     * Check that note does not consist "~".
     *
     * @throws AniException if note consist "~"
     */
    private void checkNoteForForbiddenChar() throws AniException {
        if (bookmarkNote.contains(BOOKMARK_NOTE_FORBIDDEN_CHAR)) {
            String invalidBookmarkNote = "Bookmark note " + bookmarkNote + BOOKMARK_NOTE_ERROR_MESSAGE;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkNote);
            throw new AniException(invalidBookmarkNote);
        }
    }

    /**
     * Check that the episode is not larger than the total episode.
     *
     * @param totalEpisode the total episode for an anime
     * @throws AniException if the bookmark episode to be edited is bigger than total episode
     */
    private void checkEpisode(int totalEpisode) throws AniException {
        if (bookmarkEpisode > totalEpisode) {
            String invalidBookmarkNote = "Bookmark episode " + bookmarkEpisode + BOOKMARK_EPISODE_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkNote);
            throw new AniException(invalidBookmarkNote);
        }
    }

    /**
     * Check that note id is valid.
     *
     * @param bookmark used to manage bookmark entries
     * @throws AniException if the note does not exist
     */
    private void checkNoteIndex(Bookmark bookmark) throws AniException {
        //Bookmark index is one based numbering
        if (noteIndex > bookmark.getNotesSize(bookmarkIndex - 1)) {
            String invalidBookmarkIndex = "Notes index " + noteIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + NOTES_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        } else if (noteIndex == ZERO_PARAM) {
            String invalidBookmarkIndex = "Notes index " + noteIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + NOTE_ID_ZERO_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
    }

    /**
     * Check that the anime id is valid.
     *
     * @param animeData used to retrieve anime information
     * @throws AniException if the anime id is outside the range of our anime data source
     */
    private void checkAnimeIndex(AnimeData animeData) throws AniException {
        //Anime index is one based numbering
        if (animeIndex > animeData.getSize()) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        } else if (animeIndex == ZERO_PARAM) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_ZERO_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        }
    }

    /**
     * Retrieve bookmark list from bookmark.
     *
     * @param animeData used to retrieve anime information
     * @param bookmark  used to manage bookmark entries
     * @return the bookmark list in string
     */
    private String listBookmark(AnimeData animeData, Bookmark bookmark) {
        return bookmark.getListInString(animeData);
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

    public void setNoteIndex(String noteIndex) {
        this.noteIndex = Integer.parseInt(noteIndex);
    }

}
