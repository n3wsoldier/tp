package anichan.bookmark;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;

import java.util.ArrayList;

public class Bookmark {
    private ArrayList<Integer> animeBookmarkList;
    private ArrayList<Integer> animeEpisode;
    private ArrayList<Note> noteList;

    public Bookmark() {
        this.animeEpisode = new ArrayList<>();
        this.animeBookmarkList = new ArrayList<>();
        this.noteList = new ArrayList<>();
    }

    public Bookmark(ArrayList<Integer> animeBookmarkList, ArrayList<Integer> animeEpisode) {
        this.animeBookmarkList = animeBookmarkList;
        this.animeEpisode = animeEpisode;
        //temp
        this.noteList = new ArrayList<>();
        for (int i : animeBookmarkList) {
            noteList.add(new Note());
        }
    }

    public void addAnimeBookmark(Integer animeIndex) {
        this.animeBookmarkList.add(animeIndex);
        this.animeEpisode.add(-1);
        this.noteList.add(new Note());
    }

    public void addAnimeBookmarkEpisode(Integer animeIndex, Integer episodeNumber) {
        this.animeBookmarkList.add(animeIndex);
        this.animeEpisode.add(episodeNumber);
        this.noteList.add(new Note());
    }

    public void removeAnimeBookmark(int bookmarkIndex) {
        this.animeBookmarkList.remove(bookmarkIndex);
        this.animeEpisode.remove(bookmarkIndex);
    }

    public void editAnimeBookmarkEpisode(int bookmarkIndex, int episode) {
        this.animeEpisode.set(bookmarkIndex, episode);
    }

    public ArrayList<Integer> getAnimeBookmarkList() {
        return animeBookmarkList;
    }

    public ArrayList<Integer> getAnimeEpisode() {
        return animeEpisode;
    }

    public Anime getAnimeBookmarkByIndex(AnimeData animeData, Integer bookmarkIndex) {
        int animeIndex = this.animeBookmarkList.get(bookmarkIndex);
        return animeData.getAnimeByID(animeIndex);
    }

    public int getBookmarkSize() {
        return animeBookmarkList.size();
    }

    public void addNote(Integer bookmarkIndex, String note) {
        this.noteList.get(bookmarkIndex).addNote(note);
    }

    public void addNote(Integer bookmarkIndex, String note, String date) throws AniException {
        this.noteList.get(bookmarkIndex).addNote(note, date);
    }

    public String getNoteInString(Integer bookmarkIndex) {
        StringBuilder sbNoteList = new StringBuilder(System.lineSeparator());
        if (noteList.get(bookmarkIndex).getSize() == 0) {
            sbNoteList.append("\tNotes is empty.. :(");
            sbNoteList.append(System.lineSeparator());
        } else {
            for (int i = 0; i < noteList.get(bookmarkIndex).getSize(); i++) {
                sbNoteList.append(i + 1);
                sbNoteList.append(". ");
                sbNoteList.append(noteList.get(bookmarkIndex).getNote(i) + System.lineSeparator());
            }
        }
        return sbNoteList.toString();
    }

    public String getListInString(AnimeData animeData) {
        StringBuilder sbAnimeList = new StringBuilder(System.lineSeparator());
        if (animeBookmarkList.size() == 0) {
            sbAnimeList.append("\tUhh.. It's empty.. :(");
            sbAnimeList.append(System.lineSeparator());
        }
        for (int i = 0; i < animeBookmarkList.size(); i++) {
            sbAnimeList.append("\t");
            sbAnimeList.append(i + 1);
            sbAnimeList.append(". ");
            int animeIndex = this.animeBookmarkList.get(i);
            sbAnimeList.append(animeData.getAnimeByID(animeIndex));
            if (animeEpisode.get(i) != -1) {
                sbAnimeList.append(" Ep: ");
                sbAnimeList.append(animeEpisode.get(i));
            }
            sbAnimeList.append(System.lineSeparator());
        }
        return sbAnimeList.toString();
    }

    public String getAnimeBookmarkInfo(AnimeData animeData, Integer bookmarkIndex) {
        return animeData.returnAnimeInfo(this.animeBookmarkList.get(bookmarkIndex));
    }
}
