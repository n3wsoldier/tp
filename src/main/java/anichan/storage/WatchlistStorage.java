package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;
import anichan.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchlistStorage extends Storage {
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_DECODE = " \\| ";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_ENCODE = " | ";
    private static final String DELIMITER_FOR_ENCODED_ANIME_LIST = ", ";
    private static final String ENCODED_ANIME_LIST_FIRST_CHARACTER = "[";
    private static final String ENCODED_ANIME_LIST_LAST_CHARACTER = "]";

    private static final String EMPTY_WATCHLIST_FILE = "Empty watchlist file.";
    private static final String NO_WATCHLIST_LOADED = "No watchlist loaded successfully (all invalid).";
    private static final String SOME_WATCHLIST_LOADED = "Not all loaded successfully (some invalid).";
    private static final String LOAD_SUCCESS = "Loaded successfully.";
    private static final String ENCODED_WATCHLIST_CANNOT_BE_BLANK = "Encoded watchlist string should not be blank.";

    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistStorage.class.getName());

    private final String storageDirectory;

    public WatchlistStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    // ========================== Save and Load ==========================

    public void save(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        String watchlistDirectory = storageDirectory + workspaceName + File.separator;
        String watchlistFilePath = watchlistDirectory + WATCHLIST_FILE_NAME;
        String encodedWatchlistString = encode(watchlistList);

        new File(watchlistDirectory).mkdirs();
        writeFile(watchlistFilePath, encodedWatchlistString);
    }

    public String load(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        String watchlistFilePath = storageDirectory + workspaceName + File.separator + WATCHLIST_FILE_NAME;
        String fileContent = readFile(watchlistFilePath);
        if (fileContent.isBlank()) {
            LOGGER.log(Level.WARNING, "Empty watchlist file: " + watchlistFilePath);
            return EMPTY_WATCHLIST_FILE;
        }

        boolean hasInvalidWatchlist = false;
        String[] fileLines = fileContent.split(System.lineSeparator());
        LOGGER.log(Level.FINE, "Processing: " + System.lineSeparator() + fileContent);
        for (String line : fileLines) {
            Watchlist decodedWatchlist = decode(line);
            boolean isValidWatchlist = (decodedWatchlist != null) && !(watchlistList.contains(decodedWatchlist));
            if (!isValidWatchlist) {
                hasInvalidWatchlist = true;
                continue;
            }

            watchlistList.add(decodedWatchlist);
        }

        if (hasInvalidWatchlist && watchlistList.size() == 0) {
            LOGGER.log(Level.WARNING, "All invalid watchlist entries at: " + watchlistFilePath);
            return NO_WATCHLIST_LOADED;
        } else if (hasInvalidWatchlist) {
            LOGGER.log(Level.WARNING, "Some invalid watchlist entries at: " + watchlistFilePath);
            return SOME_WATCHLIST_LOADED;
        }

        LOGGER.log(Level.INFO, "Loaded successfully: " + watchlistFilePath);
        return LOAD_SUCCESS;
    }

    // ========================== Encode and Decode ==========================

    private String encode(ArrayList<Watchlist> watchlistList) {
        StringBuilder sbWatchlist = new StringBuilder();
        for (Watchlist watchlist : watchlistList) {
            sbWatchlist.append(watchlist.getName());
            sbWatchlist.append(WATCHLIST_LINE_DELIMITER_FOR_ENCODE);
            sbWatchlist.append(watchlist.getAnimeList().toString());
            sbWatchlist.append(System.lineSeparator());
        }

        String encodedWatchlistString = sbWatchlist.toString();
        assert (!encodedWatchlistString.isBlank()) : ENCODED_WATCHLIST_CANNOT_BE_BLANK;
        return encodedWatchlistString;
    }

    private Watchlist decode(String line) {
        String[] lineSplit = line.split(WATCHLIST_LINE_DELIMITER_FOR_DECODE, 2);
        if (!isValidWatchlistString(lineSplit)) {
            return null;
        }

        String watchlistName = lineSplit[0];
        String animeListString = lineSplit[1].substring(1, lineSplit[1].length() - 1);

        ArrayList<Integer> animeList = new ArrayList<>();
        if (animeListString.isBlank()) {
            return new Watchlist(watchlistName, animeList);
        }

        String[] animes = animeListString.split(DELIMITER_FOR_ENCODED_ANIME_LIST);
        for (String animeIndex : animes) {
            if (!isValidAnimeIndex(animeIndex)) {
                return null;
            }
            animeList.add(Integer.parseInt(animeIndex));
        }

        return new Watchlist(watchlistName, animeList);
    }

    // ========================== Validation ==========================

    private boolean isValidWatchlistString(String[] lineSplit) {
        boolean isValidSplitLength = (lineSplit.length == 2);
        if (!isValidSplitLength) {
            return false;
        }

        return (lineSplit[1].startsWith(ENCODED_ANIME_LIST_FIRST_CHARACTER))
                && (lineSplit[1].endsWith(ENCODED_ANIME_LIST_LAST_CHARACTER));
    }

    private boolean isValidAnimeIndex(String animeIndex) {
        boolean isAnimeIndexBlank = animeIndex.isBlank();
        boolean isAnimeIndexInteger = isPositiveInteger(animeIndex);

        return !isAnimeIndexBlank && isAnimeIndexInteger;
    }
}
