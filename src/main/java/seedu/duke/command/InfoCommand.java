package seedu.duke.command;

import seedu.duke.anime.Anime;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.storage.StorageManager;
import static seedu.duke.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoCommand extends Command {
    protected static final String OUT_OF_BOUND_INDEX_ERROR = "Anime ID is invalid!";
    
    private Integer animeIndex;
    private static final Logger LOGGER = getAniLogger(InfoCommand.class.getName());

    public InfoCommand() {
        // LOGGER.setLevel(Level.WARNING);
    }

    /**
     * Shows information of anime.
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        int indexSize = animeData.getSize();
        StringBuilder result = new StringBuilder();
        
        if (animeIndex < 0) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        } else if (animeIndex >= indexSize) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        }
        
        result.append("Here is the information for the anime:");
        result.append(System.lineSeparator());
        String animeInfo = animeData.returnAnimeInfo(animeIndex);
        result.append(animeInfo);

        return result.toString();
    }

    public void setAnimeIndex(Integer animeIndex) {
        this.animeIndex = animeIndex - 1;
    }
}
