package seedu.duke.storage;

import seedu.duke.exception.AniException;
import seedu.duke.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;

public class WatchlistStorage extends Storage {
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_DECODE = " \\| ";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_ENCODE = " | ";

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
        String fileString = readFile(watchlistFilePath);
        if (fileString.isBlank()) {
            return "Empty watchlist file.";
        }

        boolean hasInvalidWatchlist = false;
        String[] fileLines = fileString.split(System.lineSeparator());
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
            return "No watchlist loaded successfully (all invalid).";
        } else if (hasInvalidWatchlist) {
            return "Not all loaded successfully (some invalid).";
        }

        return "Loaded successfully.";
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
        assert (!encodedWatchlistString.isBlank()) : "Encoded watchlist string should not be blank.";
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

        String[] animes = animeListString.split(", ");
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

        return (lineSplit[1].startsWith("[")) && (lineSplit[1].endsWith("]"));
    }

    private boolean isValidAnimeIndex(String animeIndex) {
        boolean isAnimeIndexBlank = animeIndex.isBlank();
        boolean isAnimeIndexInteger = isPositiveInteger(animeIndex);

        return !isAnimeIndexBlank && isAnimeIndexInteger;
    }
}
