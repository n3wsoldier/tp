package seedu.duke.human;

import seedu.duke.exception.AniException;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManagement {
    Storage storage;
    private static final Logger LOGGER = Logger.getLogger(UserManagement.class.getName());
    protected ArrayList<User> userList = new ArrayList<>();
    protected User activeUser;

    public UserManagement(Storage storage) {
        this.storage = storage;
        activeUser = null;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User inputUser) {
        activeUser = inputUser;

        if (activeUser != null) {
            LOGGER.log(Level.INFO, "User switched: " + inputUser.getName());
        }
    }

    public int getTotalUsers() {
        return userList.size();
    }

    public Storage getStorage() {
        return storage;
    }

    public User addUser(String name, String dob, String gender) throws ParseException, AniException {
        User newUser = new User(name, dob, gender);
        checkIfUserExist(name);

        userList.add(newUser);
        storage.saveUser(newUser);

        LOGGER.log(Level.INFO, "User created: " + name + " | " + dob + " | " + gender);
        return newUser;
    }

    private void checkIfUserExist(String name) throws AniException {
        for (User existingUser : userList) {
            if (existingUser.getName().equals(name)) {
                throw new AniException("A user with " + name + " already exist. Choose a different name!");
            }
        }
    }

    public void addUserDialogue(Ui ui) {
        boolean userCreated = false;
        LOGGER.log(Level.WARNING, "No existing user found, prompting user to create one!");

        while (!userCreated) {
            try {
                ui.printMessage("What's your name?");
                String name = ui.readInput();
                ui.printMessage("Hello " + name + "! What might your date of birth be? (DD/MM/YYYY)");
                String dob = ui.readInput();
                ui.printMessage("What might your gender be? (Male/Female/Other)");
                String gender = ui.readInput();

                activeUser = addUser(name, dob, gender);
                ui.printMessage(" Successfully added new user:");
                ui.printMessage(activeUser.getName());
                ui.printMessage(activeUser.getDobString());
                ui.printMessage(activeUser.getGender().toString());
                userCreated = true;
            } catch (ParseException | AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        }
    }
}