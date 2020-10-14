package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.AddUserCommand;
import seedu.duke.command.EditProfileCommand;
import seedu.duke.command.BrowseCommand;
import seedu.duke.command.WatchlistCommand;
import seedu.duke.command.AddToWatchlistCommand;
import seedu.duke.command.BookmarkAnimeCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.exception.AniException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static Logger LOGGER = Logger.getLogger(Command.class.getName());

    /**
     * Prints the main menu of the application
     * and requests for command.
     */
    public Command getCommand(String fullCommand) throws AniException {
        String[] fullCommandSplit = parseUserInput(fullCommand);
        String description = "";
        String command = fullCommandSplit[0];
        if (fullCommandSplit.length > 1) {
            description = fullCommandSplit[1];
        }

        switch (command) {
        case "adduser":
            return new AddUserCommand(description);
                
        case "editprofile":
            return new EditProfileCommand(description);
 
        case "browse":
            return new BrowseCommand(description);
     
        case "watchlist":
            return new WatchlistCommand(description);
           
        case "add":
            return new AddToWatchlistCommand(description);

        case "bookmark":
            return new BookmarkAnimeCommand(description);
                
        case "help":
            return new HelpCommand();

        case "exit":
            return new ExitCommand();
                
        default:
            throw new AniException("Unknown command");
        }
    }

    public static String[] parseUserInput(String input) throws AniException {
        if (input == null || input.isEmpty()) {
            LOGGER.log(Level.WARNING, "Exception occurred");
            throw new AniException("Input is empty");
        }
        LOGGER.log(Level.INFO, "Parser processing succeeded");

        String[] inputSplit = input.split(" ", 2);
        return inputSplit;
    }
}