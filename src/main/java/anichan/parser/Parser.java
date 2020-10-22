package anichan.parser;

import anichan.command.HelpCommand;
import anichan.command.Command;
import anichan.command.ExitCommand;
import anichan.exception.AniException;
import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = getAniLogger(Parser.class.getName());

    /**
     * Prints the main menu of the application
     * and requests for command.
     */
    public Command getCommand(String fullCommand) throws AniException {
        // LOGGER.setLevel(Level.WARNING);
        LOGGER.log(Level.INFO, "Parse: " + fullCommand);

        String[] fullCommandSplit = parseUserInput(fullCommand);
        String description = "";
        String command = fullCommandSplit[0];

        if (fullCommandSplit.length > 1) {
            description = fullCommandSplit[1];
        }

        switch (command) {
        case "addws":
            return new AddWorkspaceParser().parse(description);

        case "switchws":
            return new SwitchWorkspaceParser().parse(description);

        case "browse":
            return new BrowseParser().parse(description);

        case "search":
            return new SearchParser().parse(description);

        case "watchlist":
            return new WatchlistParser().parse(description);
            
        case "view":
            return new ViewWatchlistParser().parse(description);

        case "add":
            return new AddToWatchlistParser().parse(description);
        
        case "remove":
            return new RemoveCommandParser().parse(description);

        case "bookmark":
            return new BookmarkParser().parse(description);

        case "estimate":
            return new EstimateParser().parse(description);
        
        case "info":
            return new InfoParser().parse(description);

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
