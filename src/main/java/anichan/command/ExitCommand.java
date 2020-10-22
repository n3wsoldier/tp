package anichan.command;

import anichan.human.User;
import anichan.anime.AnimeData;
import anichan.storage.StorageManager;

public class ExitCommand extends Command {
    protected static final String NULL_STRING = "";

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) {
        setShouldExit();
        return NULL_STRING;
    }
}
