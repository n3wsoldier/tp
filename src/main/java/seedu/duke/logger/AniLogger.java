package seedu.duke.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AniLogger {
    private static final int MAX_LOG_COUNT = 1;
    private static final int MAX_LOG_SIZE = (int) (Math.pow(1024, 3) * 5); // 5 Megabytes
    private static final String LOG_FILE = "data/AniChan.log";

    private static final Level LOG_FILE_LEVEL = Level.INFO;
    private static final Level CONSOLE_LEVEL = Level.SEVERE;

    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    public static Logger getAniLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.setUseParentHandlers(false);

        clearHandlers(logger);
        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(className);
    }


    private static void addConsoleHandler(Logger logger) {
        assert logger != null : "Logger is null!";

        if (consoleHandler == null) {
            consoleHandler = createConsoleHandler();
        }

        logger.addHandler(consoleHandler);
    }

    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(CONSOLE_LEVEL);
        return consoleHandler;
    }


    private static void addFileHandler(Logger logger) {
        assert logger != null : "Logger is null!";

        try {
            if (fileHandler == null) {
                fileHandler = createFileHandler();
            }

            logger.addHandler(fileHandler);
        } catch (IOException exceptionMessage) {
            logger.log(Level.WARNING, "Failed to add file handler for logger.");
        }
    }


    private static FileHandler createFileHandler() throws IOException {
        File file = new File(LOG_FILE);
        file.getParentFile().mkdirs();
        file.createNewFile();

        SimpleFormatter formatter = new SimpleFormatter();

        FileHandler fileHandler = new FileHandler(LOG_FILE, MAX_LOG_SIZE,
                MAX_LOG_COUNT, true);
        fileHandler.setFormatter(formatter);
        fileHandler.setLevel(LOG_FILE_LEVEL);

        return fileHandler;
    }




    private static void clearHandlers(Logger logger) {
        assert logger != null : "Logger is null!";

        Handler[] handlers = logger.getHandlers();

        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
    }

}
