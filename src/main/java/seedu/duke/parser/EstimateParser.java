package seedu.duke.parser;

import seedu.duke.command.EstimateCommand;
import seedu.duke.exception.AniException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.logger.AniLogger.getAniLogger;

public class EstimateParser extends CommandParser {
    private static final String WORDS_PER_HOUR_OPTION = "wph";
    private static final int NO_WORDS_PER_HOUR_PROVIDED = -1;
    private static final Logger LOGGER = getAniLogger(EstimateParser.class.getName());

    public EstimateCommand parse(String description) throws AniException {
        assert description != null : "description should not be null.";
        String[] paramGiven = parameterSplitter(description);
        if (paramGiven.length > 2) {
            throw new AniException("Estimate command" + TOO_MUCH_FIELDS);
        } else if (paramGiven[0].isBlank()) {
            throw new AniException("Script file not found! Ensure that it is in your workspace folder!");
        }

        String fileName = paramGiven[0].trim();
        if (!isValidFileName(fileName)) {
            throw new AniException("AniChan only accept script files in \".txt\".");
        }

        int wordsPerHour = NO_WORDS_PER_HOUR_PROVIDED;
        if (paramGiven.length == 2) {
            wordsPerHour = parameterParser(paramGiven);
        }

        LOGGER.log(Level.INFO, "Returning a EstimateCommand object with file: "
                                    + fileName + ", and wph: " + wordsPerHour + ".");
        return new EstimateCommand(fileName, wordsPerHour);
    }

    private int parameterParser(String[] paramGiven) throws AniException {
        String[] parsedParts = paramGiven[1].split(" ");
        String option = parsedParts[0].trim();
        if (!parsedParts[0].equals(WORDS_PER_HOUR_OPTION)) {
            throw new AniException("The option \"" + option + "\" is not accepted in \"estimate\".");
        }

        if (parsedParts.length == 1) {
            throw new AniException("Words per hour information is missing!");
        } else if (parsedParts.length > 2) {
            throw new AniException("AniChan can only take in one value for words per hour (\"-wph\").");
        }

        int wordsPerHour = 0;
        String wordsPerHourString = parsedParts[1].trim();
        try {
            wordsPerHour = Integer.parseInt(wordsPerHourString);
        } catch (NumberFormatException exception) {
            throw new AniException("Words per hour (\"-wph\") must be a positive integer!");
        }

        if (wordsPerHour == 0) {
            throw new AniException("Words per hour (\"-wph\") should not be zero, otherwise.. it be forever..");
        }

        return wordsPerHour;
    }

    private boolean isValidFileName(String fileName) throws AniException {
        if (fileName.split(" ").length != 1) {
            throw new AniException("AniChan can only process one script file at a time!");
        }

        return fileName.trim().endsWith(".txt");
    }
}