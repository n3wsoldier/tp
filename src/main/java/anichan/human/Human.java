package anichan.human;

import anichan.exception.AniException;

public abstract class Human {
    protected String name;

    public Human(String name) throws AniException {
        if (!name.isEmpty()) {
            setName(name);
        } else {
            throw new AniException("Is your name empty?");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
