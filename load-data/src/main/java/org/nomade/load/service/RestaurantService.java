package org.nomade.load.service;

import com.mongodb.client.MongoCollection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.nomade.load.model.Restaurant;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService extends BaseService {

    private static final String ROOT = "C:\\projects\\java\\data\\org.nomade.load-data";
    private static final String CSV_FILENAME = "restaurant_clean.csv";

    public void load() throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(ROOT, CSV_FILENAME));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            List<Restaurant> restaurants = new ArrayList<>();

            for (CSVRecord csvRecord : csvRecords) {

                Restaurant resto = new Restaurant();
                resto.setId(Long.parseLong(csvRecord.get(0)));
                resto.setArea(csvRecord.get("area"));
                resto.setCost(Long.parseLong(csvRecord.get("cost")));

//                transform data
                String cuisineRaw = csvRecord.get("cuisine");
                cuisineRaw = cuisineRaw.replaceAll("'\\s'", "', '");
                List<String> cuisine = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(cuisineRaw);
                    for (Object o : array) {
                        cuisine.add(o.toString());
                    }
                } catch (Exception ignore) { }

                resto.setCuisine(cuisine);
                resto.setLatitude(Double.parseDouble(csvRecord.get("latitude")));
                resto.setLongitude(Double.parseDouble(csvRecord.get("longitude")));
                resto.setName(csvRecord.get("name"));
                resto.setPreference(Integer.parseInt(csvRecord.get("preference")));
                resto.setRatings(Double.parseDouble(csvRecord.get("ratings")));
                resto.setType(csvRecord.get("type"));
                resto.setVote(Integer.parseInt(csvRecord.get("votes")));

                restaurants.add(resto);
            }

            getCollection().insertMany(restaurants);

            System.out.println(restaurants.size() + "/" + csvParser.getRecordNumber() + " restaurant(s) imported.");
        }
    }

    public void dropCollection() {
        getCollection().drop();
    }

    MongoCollection<Restaurant> getCollection() {
        return getDatabase().getCollection("restaurant", Restaurant.class);
    }
}
