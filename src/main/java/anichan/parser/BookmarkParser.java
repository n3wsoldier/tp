package anichan.parser;

import anichan.command.BookmarkAnimeCommand;
import anichan.exception.AniException;
import static anichan.logger.AniLogger.getAniLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkParser extends CommandParser {

    public static final String ADD_PARAM = "a";
    public static final String DELETE_PARAM = "d";
    public static final String EPISODE_PARAM = "e";
    public static final String LIST_PARAM = "l";
    public static final String INFO_PARAM = "i";
    public static final String ADD_NOTE_PARAM = "n";
    public static final String DASH_PARAM = "-";
    private static final String PARAMETER_ERROR_HEADER = "Parameter :";
    private static final String DESCRIPTION_ERROR_HEADER = "Description :";
    private static final String BOOKMARK_LOAD_ERROR_HEADER = "Could not load bookmark command :";
    private static final Logger LOGGER = getAniLogger(BookmarkParser.class.getName());

    private BookmarkAnimeCommand bookmarkAnimeCommand;

    public BookmarkParser() {
        bookmarkAnimeCommand = new BookmarkAnimeCommand();
        // LOGGER.setLevel(Level.WARNING);
    }

    public BookmarkAnimeCommand parse(String description) throws AniException {
        String[] paramGiven = getSplitDescription(description);
        if (paramGiven.length > 1) {
            parameterParser(paramGiven[1]);
            setFirstParameter(paramGiven[0]);
        } else {
            setSingleParameter(description);
        }

        return bookmarkAnimeCommand;
    }

    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(" ");
        switch (paramParts[0].trim()) {
        case EPISODE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark edit episode param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkAnimeCommand.setBookmarkEpisode(paramParts[1].trim());
            break;
        case ADD_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark Add param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkAnimeCommand.setAnimeIndex(paramParts[1].trim());
            break;
        case DELETE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark delete param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkAnimeCommand.setBookmarkIndex(paramParts[1].trim());
            break;
        case LIST_PARAM:
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            break;
        case ADD_NOTE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            bookmarkAnimeCommand.setBookmarkNote(paramParts[1].trim());
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }

    private void setFirstParameter(String paramGiven) throws AniException {
        //Action edit(e) requires first parameter as bookmarkIndex
        if (bookmarkAnimeCommand.getBookmarkAction().equals("e")
                || bookmarkAnimeCommand.getBookmarkAction().equals("n")) {
            if (!isInt(paramGiven.trim())) {
                String invalidBookmarkIndex = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark index for edit episode requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidBookmarkIndex);
                throw new AniException(invalidBookmarkIndex);
            }
            bookmarkAnimeCommand.setBookmarkIndex(paramGiven.trim());
        } else {
            boolean isEmpty = paramGiven.trim().equals("");
            if (!isEmpty) {
                String invalidFirstParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Add or Delete should not have extra param.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidFirstParameter);
                throw new AniException(invalidFirstParameter);
            }
        }
    }

    private void setSingleParameter(String paramGiven) throws AniException {
        if (!isInt(paramGiven.trim())) {
            String invalidBookmarkIndex = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + " Bookmark index for info requires integer.";
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
        bookmarkAnimeCommand.setBookmarkAction(INFO_PARAM);
        bookmarkAnimeCommand.setBookmarkIndex(paramGiven.trim());
    }

    private String[] getSplitDescription(String description) throws AniException {
        String[] paramGiven = description.split(DASH_PARAM);
        if (paramGiven.length > 2) {
            String invalidDescription = DESCRIPTION_ERROR_HEADER + description + TOO_MUCH_FIELDS;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidDescription);
            throw new AniException(invalidDescription);
        }
        //        else if (paramGiven.length < 2) {
        //            String invalidDescription = DESCRIPTION_ERROR_HEADER + description + REQUIRE_ADDITIONAL_FIELD;
        //            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidDescription);
        //            throw new AniException(invalidDescription);
        //        }
        return paramGiven;
    }
}
