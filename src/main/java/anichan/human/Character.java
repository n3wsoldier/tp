package anichan.human;

import anichan.exception.AniException;

import java.util.ArrayList;

public class Character extends Human {
    protected ArrayList<VoiceActor> voiceActors = new ArrayList<>();

    public Character(String name) throws AniException {
        super(name);
    }

    public void addVoiceActor(VoiceActor newVoiceActor) {
        voiceActors.add(newVoiceActor);
    }


    public int getTotalVoiceActors() {
        return voiceActors.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
