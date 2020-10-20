package seedu.duke.parser;

import seedu.duke.bookmark.Bookmark;
import seedu.duke.command.BookmarkAnimeCommand;
import seedu.duke.command.BrowseCommand;
import seedu.duke.exception.AniException;
import static seedu.duke.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkParser extends CommandParser {

    protected static final String ADD_PARAM = "a";
    protected static final String DELETE_PARAM = "d";
    protected static final String EPISODE_PARAM = "e";
    protected static final String LIST_PARAM = "l";
    private static final Logger LOGGER = getAniLogger(seedu.duke.parser.BookmarkParser.class.getName());


    private BookmarkAnimeCommand bookmarkAnimeCommand;

    public BookmarkParser() {
        bookmarkAnimeCommand = new BookmarkAnimeCommand();
        // LOGGER.setLevel(Level.WARNING);
    }

    public BookmarkAnimeCommand parse(String description) throws AniException {
        String[] paramGiven = getSplitDescription(description);
        parameterParser(paramGiven[1]);
        setFirstParameter(paramGiven[0]);
        return bookmarkAnimeCommand;
    }

    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(" ");
        switch (paramParts[0].trim()) {
        case EPISODE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            Boolean isValidBookmarkEpisode = bookmarkAnimeCommand.setBookmarkEpisode(paramParts[1].trim());
            if (!isValidBookmarkEpisode) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark edit episode param requires integer.";
                LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case ADD_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            Boolean isValidAnimeIndex = bookmarkAnimeCommand.setAnimeIndex(paramParts[1].trim());
            if (!isValidAnimeIndex) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark Add param requires integer.";
                LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case DELETE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            Boolean isValidBookmarkIndex = bookmarkAnimeCommand.setBookmarkIndex(paramParts[1].trim());
            if (!isValidBookmarkIndex) {
                String invalidParameter = "-" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark delete param requires integer.";
                LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidParameter);
                throw new AniException(invalidParameter);
            }
            break;
        case LIST_PARAM:
            bookmarkAnimeCommand.setBookmarkAction(paramParts[0]);
            break;
        default:
            String invalidParameter = "-" + paramGiven + " is an invalid parameter!";
            throw new AniException(invalidParameter);
        }
    }

    private void setFirstParameter(String paramGiven) throws AniException {
        //Action edit(e) requires first parameter as bookmarkIndex
        if (bookmarkAnimeCommand.getBookmarkAction().equals("e")) {
            boolean isValidBookmarkIndex = bookmarkAnimeCommand.setBookmarkIndex(paramGiven.trim());
            if (!isValidBookmarkIndex) {
                String invalidBookmarkIndex = "" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Bookmark index for edit episode requires integer.";
                LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidBookmarkIndex);
                throw new AniException(invalidBookmarkIndex);
            }
        } else {
            boolean isEmpty = paramGiven.trim().equals("");
            if (!isEmpty) {
                String invalidFirstParameter = "" + paramGiven + " is an invalid parameter!"
                        + System.lineSeparator() + " Add or Delete should not have extra param.";
                LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidFirstParameter);
                throw new AniException(invalidFirstParameter);
            }
        }
    }

    private String[] getSplitDescription(String description) throws AniException {
        String[] paramGiven = description.split("-");
        if (paramGiven.length > 2) {
            String invalidDescription =  description + " has too many parameter!";
            LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidDescription);
            throw new AniException(invalidDescription);
        } else if (paramGiven.length < 2) {
            String invalidDescription =  description + " has too few parameter!";
            LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidDescription);
            throw new AniException(invalidDescription);
        }
        return paramGiven;
    }

    //    private void paramLengthCheck(String[] paramParts) throws AniException {
    //        // Parameter Additional Field Check
    //        if (paramParts.length < 2) {
    //            String invalidParameter = "Parameter : " + paramParts[0] + " requires an additional field";
    //            LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidParameter);
    //            throw new AniException(invalidParameter);
    //        } else if (paramParts.length > 2) {
    //            String invalidParameter = "Parameter : " + paramParts[0] + " has too much fields";
    //            LOGGER.log(Level.WARNING, "Could not load bookmark command:" + invalidParameter);
    //            throw new AniException(invalidParameter);
    //        }
    //    }
}
