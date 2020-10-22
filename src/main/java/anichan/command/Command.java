package anichan.command;

import anichan.human.User;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.storage.StorageManager;

public abstract class Command {
    String description;
    private boolean shouldExit = false;

    public boolean getShouldExit() {
        return shouldExit;
    }

    public void setShouldExit() {
        this.shouldExit = true;
    }

    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        throw new AniException("This method should be implemented in the child class");
    }
}
