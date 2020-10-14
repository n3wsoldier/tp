package seedu.duke.anime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.storage.Storage;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimeStorage {

    /* Files */
    private static final String RELATIVE_DIR = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = File.separator;
    private static final Logger LOGGER = Logger.getLogger(Anime.class.getName());
    private File dataFile;
    private String[] pathnames;

    //public static void main(String[] args) throws IOException {
    //    AnimeStorage  animeStorage = new AnimeStorage("/data/AniListData");
    //    AnimeData animeList = new AnimeData(animeStorage.readAnimeDatabase());
    //    animeList.printAll();
    //}



    public AnimeStorage(String fileFolder) throws IOException {
        // Set log levels
        LOGGER.setLevel(Level.WARNING);
        System.out.println(RELATIVE_DIR);
        LOGGER.info("Loading filenames from DataSource folder.");
        this.dataFile = new File(prepareFile(fileFolder));
        pathnames = dataFile.list();
        String test = getData("/Data/AniList-Data1.json");
        System.out.println(test);
        System.out.println(dataFile.getPath());
        for(String path: pathnames) {
            System.out.println(path);
        }
        LOGGER.info("Loading filenames successful.");
    }

    private String prepareFile(String fileFolder) {
        return RELATIVE_DIR + fileFolder.replace("\\",FILE_SEPARATOR).replace("/",FILE_SEPARATOR);
    }

    public ArrayList<Anime> readAnimeDatabase() throws IOException {
        LOGGER.info("Retrieving information from DataSource.");
        ArrayList<Anime> animeDataList = new ArrayList<>();
        for (String pathname : pathnames) {
            LOGGER.info("Currently extracting and parsing from " + dataFile.getPath() + FILE_SEPARATOR
                    + pathname);
            FileReader fileData = new FileReader(dataFile.getPath() + FILE_SEPARATOR
                    + pathname);
            //System.out.println(fileData);
            parseJson(animeDataList, fileData);
        }
        LOGGER.info("Retrieval and Parsing for anime object in DataSource Successful.");
        return animeDataList;
    }

    private void parseJson(ArrayList<Anime> animeDataList,FileReader  fileData) {
        JSONParser parser = new JSONParser();
        JSONArray jsonList = new JSONArray();
        try {
            jsonList = (JSONArray) parser.parse(fileData);

        } catch (ParseException | IOException e) {
            LOGGER.warning("Parsing file failed!");
            e.printStackTrace();

        }

        Iterator iterator = jsonList.iterator();
        JSONObject jsonObject = new JSONObject();
        while (iterator.hasNext()) {
            String animeName;
            int animeEpisode = 0;
            int animeRating = 0;
            int animeDuration = 0;

            //Advance the Iterator
            jsonObject = (JSONObject) iterator.next();
            jsonObject = (JSONObject) jsonObject.get("data");
            jsonObject = (JSONObject) jsonObject.get("Media");

            //Getting anime name
            JSONObject jsonTitle = (JSONObject) jsonObject.get("title");
            if (jsonTitle.get("english") == null) {
                animeName = (String) jsonTitle.get("romaji");
            } else {
                animeName = (String) jsonTitle.get("english");
            }

            //getting anime episode

            if (jsonObject.get("episodes") != null) {
                animeEpisode =  (int) (long) jsonObject.get("episodes");
            }

            //getting start date
            JSONObject jsonDate = (JSONObject) jsonObject.get("startDate");
            String[] animeReleaseDate;
            animeReleaseDate = new String[] { String.valueOf((long) jsonDate.get("year")),
                    String.valueOf((long) jsonDate.get("month")), String.valueOf((long) jsonDate.get("day"))};

            //getting rating
            if (jsonObject.get("averageScore") != null) {
                animeRating =  (int) (long) jsonObject.get("averageScore");
            }

            //getting genre
            ArrayList<String> animeGenre;
            animeGenre = new ArrayList<>();
            JSONArray jsonGenre = (JSONArray) jsonObject.get("genres");
            for (Object genre : jsonGenre) {
                animeGenre.add((String) genre);
            }
            String[] animeGenreArray;
            animeGenreArray = new String[animeGenre.size()];
            animeGenreArray = animeGenre.toArray(animeGenreArray);

            //getting duration
            if (jsonObject.get("duration") != null) {
                animeDuration =  (int) (long) jsonObject.get("duration");
            }

            Anime anime = new Anime(animeName, animeReleaseDate, animeRating, animeGenreArray, animeDuration,
                    animeEpisode);
            animeDataList.add(anime);
        }
    }

    public  String getData(String filename) throws IOException {

        InputStream inputStream = AnimeStorage.class.getResourceAsStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String datafromjson = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                datafromjson += line;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            throw e;
        }
        return datafromjson;
    }
}
