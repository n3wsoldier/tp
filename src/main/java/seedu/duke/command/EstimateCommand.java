package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.Workspace;
import seedu.duke.storage.StorageManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.logger.AniLogger.getAniLogger;

public class EstimateCommand extends Command {
    private static final int NO_WORDS_PER_HOUR_PROVIDED = -1;
    private static final int MINUTES_PER_HOUR = 60;

    // On average, translator translates about 400 to 600 words in an hour, so a multiple
    // of 100 (400, 500, 600) is chosen to provide users with various estimation times so
    // they can identify which of these 3 values is a better estimate for them.
    private static final int[] AVERAGE_TRANSLATOR_WORDS_PER_HOUR = {400, 500, 600};
    private static final Logger LOGGER = getAniLogger(EstimateCommand.class.getName());

    private final String scriptFileName;
    private final int wordsPerHour;

    public EstimateCommand(String scriptFileName, int wordsPerHour) {
        this.scriptFileName = scriptFileName;
        this.wordsPerHour = wordsPerHour;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        String fileString = storageManager.readScript(activeWorkspace.getName(), scriptFileName);
        int wordCount = fileString.split(" ").length;
        LOGGER.log(Level.INFO, wordCount + " words in the script (" + scriptFileName + ").");

        StringBuilder commandResult = new StringBuilder();
        if (wordsPerHour != NO_WORDS_PER_HOUR_PROVIDED) {
            double timeNeeded = wordCount / (double) wordsPerHour;
            commandResult.append("You would need ");
            commandResult.append(getHoursAndMinutesNeeded(timeNeeded));
        } else {
            for (int averageWordsPerHour : AVERAGE_TRANSLATOR_WORDS_PER_HOUR) {
                double timeNeeded = wordCount / (double) averageWordsPerHour;
                commandResult.append("Average translator (");
                commandResult.append(averageWordsPerHour);
                commandResult.append(" words per hour) takes: ");
                commandResult.append(getHoursAndMinutesNeeded(timeNeeded));
                commandResult.append(System.lineSeparator());
            }

            // Remove extra new line.
            commandResult.setLength(commandResult.length() - System.lineSeparator().length());
        }

        LOGGER.log(Level.INFO, "Returning estimate result for " + scriptFileName
                                    + " with " + wordsPerHour + "wph.");
        return commandResult.toString();
    }

    private String getHoursAndMinutesNeeded(double timeNeeded) {
        double hoursNeeded = Math.floor(timeNeeded);
        double minutesNeeded = (timeNeeded - hoursNeeded) * MINUTES_PER_HOUR;

        String hoursAndMinutesNeeded = (int) hoursNeeded + " hour(s)";
        if ((int)minutesNeeded != 0) {
            hoursAndMinutesNeeded += " " + (int) minutesNeeded + " minute(s).";
        } else {
            hoursAndMinutesNeeded += ".";
        }

        LOGGER.log(Level.INFO, "Converted " + timeNeeded + " to: " + hoursAndMinutesNeeded);
        return hoursAndMinutesNeeded;
    }
}
