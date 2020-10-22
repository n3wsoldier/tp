package anichan.command;

import anichan.human.User;
import anichan.anime.AnimeData;
import anichan.storage.StorageManager;
import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;


public class HelpCommand extends Command {
    private static final Logger LOGGER = getAniLogger(HelpCommand.class.getName());
    private static String output;
    
    public HelpCommand() {
        // LOGGER.setLevel(Level.WARNING);
        this.output = buildHelpOutput();
    }
    
    /**
     * Shows help function.
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) {
        return output;
    }
    
    private String buildHelpOutput() {
        StringBuilder result = new StringBuilder();
        LOGGER.log(Level.INFO, "Start of build help output");
        
        result.append("Create a new user profile:");
        result.append(System.lineSeparator());
        result.append(" adduser -n <NAME> -dob <dd/MM/yyyy> -g <GENDER>");

        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Switch to another user:");
        result.append(System.lineSeparator());
        result.append(" switchuser -n <NAME>");

        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Browse through the anime list:");
        result.append(System.lineSeparator());
        result.append(" browse [-s SORT_CATEGORY] [-o SORT_ORDER] [-p PAGE_NUMBER]");
        result.append(System.lineSeparator());
        result.append("     - SORT_CATEGORY: name, rating");
        result.append(System.lineSeparator());
        result.append("     - SORT_ORDER: asc, dsc");
        result.append(System.lineSeparator());
        result.append("     - PAGE_NUMBER: 1, 2, 3, ...");

        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Create a new watchlist:");
        result.append(System.lineSeparator());
        result.append(" watchlist -n <WATCHLIST_NAME>");
        
        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" List all watchlist:");
        result.append(System.lineSeparator());
        result.append(" watchlist -l");

        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Add an anime to active watchlist:");
        result.append(System.lineSeparator());
        result.append(" add -a <ANIME_NAME>");

        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Bookmark an anime:");
        result.append(System.lineSeparator());
        result.append(" bookmark -a <ANIME_ID>");
        
        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Delete bookmark for an anime:");
        result.append(System.lineSeparator());
        result.append(" bookmark -d <BOOKMARK_ID>");
        
        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        
        result.append(" Edit a bookmark:");
        result.append(System.lineSeparator());
        result.append(" bookmark <BOOKMARK_ID> -e <EPISODE>");

        LOGGER.log(Level.INFO, "End of build help output");
        
        return result.toString();
    }
}
