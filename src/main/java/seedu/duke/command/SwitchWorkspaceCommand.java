package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.storage.StorageManager;
import static seedu.duke.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SwitchWorkspaceCommand extends Command {
    protected static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    protected static final String REQUIRE_ADDITIONAL_FIELD = " requires an additional field";
    private static final Logger LOGGER = getAniLogger(SwitchWorkspaceCommand.class.getName());
    protected static final String SWITCH_SUCCESS_HEADER = "Workspace changed to ";

    private String switchToThisWorkspace;


    public SwitchWorkspaceCommand() {
        // LOGGER.setLevel(Level.WARNING);
        this.description = description;
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        //Find the user and setActiveUser to it
        user.switchActiveWorkspace(switchToThisWorkspace);
        return SWITCH_SUCCESS_HEADER + switchToThisWorkspace;
    }

    public void setSwitchToThisWorkspace(String switchToThisWorkspace) {
        this.switchToThisWorkspace = switchToThisWorkspace;
    }
}
