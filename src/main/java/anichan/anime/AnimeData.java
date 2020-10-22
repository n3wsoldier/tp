package anichan.anime;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimeData {
    public ArrayList<Anime> animeDataList;
    public ArrayList<Anime> animeOriginalDataList;

    public AnimeData(ArrayList<Anime> animeDataList) {
        this.animeDataList = animeDataList;
        this.animeOriginalDataList = new ArrayList<>(this.animeDataList);
    }

    public Anime getAnime(Integer animeIndex) throws IndexOutOfBoundsException, NullPointerException {
        try {
            return animeDataList.get(animeIndex);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        }

    }

    public int getSize() {
        return animeDataList.size();
    }

    public ArrayList<Anime> getAnimeDataList() {
        return animeDataList;
    }

    public void printAll() {
        int i = 1;
        for (Anime anime : animeDataList) {
            System.out.println("---------------------------------");
            System.out.println("Index: " + i);
            System.out.println("Name: " + anime.getAnimeName());
            System.out.println("Episodes: " + anime.getTotalEpisodes());
            System.out.println("Release Date: " + anime.getReleaseDateInString());
            System.out.println("Rating: " + anime.getRating());
            System.out.println("Genre: " + Arrays.toString(anime.getGenre()));
            i += 1;
        }
    }

    public void printOne(int animeIndex) {
        Anime anime = animeDataList.get(animeIndex);
        System.out.println("---------------------------------");
        System.out.println("Index: " + animeIndex);
        System.out.println("Name: " + anime.getAnimeName());
        System.out.println("Episodes: " + anime.getTotalEpisodes());
        System.out.println("Release Date: " + anime.getReleaseDateInString());
        System.out.println("Rating: " + anime.getRating());
        System.out.println("Genre: " + Arrays.toString(anime.getGenre()));
    }

    public String returnAnimeInfo(int animeIndex) {
        Anime anime = animeDataList.get(animeIndex);
        StringBuilder result = new StringBuilder();

        result.append("Index: " + (animeIndex + 1));
        result.append(System.lineSeparator());
        result.append("Name: " + anime.getAnimeName());
        result.append(System.lineSeparator());
        result.append("Episodes: " + anime.getTotalEpisodes());
        result.append(System.lineSeparator());
        result.append("Release Date: " + anime.getReleaseDateInString());
        result.append(System.lineSeparator());
        result.append("Rating: " + anime.getRating());
        result.append(System.lineSeparator());
        result.append("Genre: " + Arrays.toString(anime.getGenre()));

        return result.toString();
    }

    public ArrayList<Anime> findName(String description) {
        ArrayList<Anime> findList = new ArrayList<>();
        System.out.println("Looking for \"" + description + "\"");
        for (Anime anime : animeOriginalDataList) {
            if (anime.getAnimeName().contains(description)) {
                findList.add(anime);
            }
        }
        return findList;
    }

    //Functions below uses original data list
    public Anime getAnimeByID(Integer animeIndex) throws IndexOutOfBoundsException, NullPointerException {
        try {
            return animeOriginalDataList.get(animeIndex);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        }
    }
}
