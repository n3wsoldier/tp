package anichan.storage;

import anichan.bookmark.Bookmark;
import anichan.exception.AniException;

import java.io.File;
import java.util.ArrayList;

public class BookmarkStorage extends Storage {
    private static final String BOOKMARK_FILE_NAME = "bookmark.txt";
    private static final String BOOKMARK_LINE_DELIMITER = ",";

    private final String storageDirectory;

    public BookmarkStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }
    // ========================== Save and Load ==========================

    public void save(String workspaceName, Bookmark bookmark) throws AniException {
        String bookmarkDirectory = storageDirectory + workspaceName + File.separator;
        String bookmarkFilePath = bookmarkDirectory + BOOKMARK_FILE_NAME;
        String encodedWatchlistString = encode(bookmark);
        new File(bookmarkDirectory).mkdirs();
        writeFile(bookmarkFilePath, encodedWatchlistString);
    }

    public String load(String workspaceName, Bookmark bookmark) throws AniException {
        String bookmarkFilePath = storageDirectory + workspaceName + File.separator + BOOKMARK_FILE_NAME;
        String fileString = readFile(bookmarkFilePath);
        if (fileString.isBlank()) {
            return "Empty bookmark file.";
        }

        String[] fileLines = fileString.split(System.lineSeparator());
        return decode(fileLines, bookmark);
    }

    // ========================== Encode and Decode ==========================

    private String encode(Bookmark bookmark) {
        StringBuilder sbBookmark = new StringBuilder();
        ArrayList<Integer> animeBookmarkList = bookmark.getAnimeBookmarkList();
        ArrayList<Integer> animeEpisode = bookmark.getAnimeEpisode();
        for (int i = 0; i < bookmark.getBookmarkSize(); i++) {
            sbBookmark.append(animeBookmarkList.get(i));
            sbBookmark.append(",");
            sbBookmark.append(animeEpisode.get(i));
            sbBookmark.append(System.lineSeparator());
        }

        sbBookmark.setLength(sbBookmark.length() - 2);  // Remove ", " for the last item in the string.
        String encodedBookmarkString = sbBookmark.toString();
        assert (encodedBookmarkString.isBlank()) : "Encoded bookmark string should not be blank.";
        return encodedBookmarkString;
    }

    private String decode(String[] fileLines, Bookmark bookmark) {
        boolean hasCorruptedBookmark = false;
        for (String line : fileLines) {
            String[] lineSplit = line.split(BOOKMARK_LINE_DELIMITER, 2);
            if (!isValidBookmarkString(lineSplit)) {
                hasCorruptedBookmark = true;
                continue;
            }

            int bookmarkIndex = Integer.parseInt(lineSplit[0]);
            int bookmarkEpisode = Integer.parseInt(lineSplit[1]);
            bookmark.addAnimeBookmarkEpisode(bookmarkIndex, bookmarkEpisode);
        }

        if (hasCorruptedBookmark) {
            return "Not all loaded successfully.";
        } else {
            return "Loaded successfully.";
        }
    }

    // ========================== Validation ==========================

    private boolean isValidBookmarkString(String[] lineSplit) {
        boolean isValidSplitLength = (lineSplit.length == 2);
        if (!isValidSplitLength) {
            return false;
        }

        boolean isFirstPartInteger = isPositiveOrNegativeInteger(lineSplit[0]);
        boolean isSecondPartInteger = isPositiveOrNegativeInteger(lineSplit[1]);
        return isFirstPartInteger && isSecondPartInteger;
    }
}
