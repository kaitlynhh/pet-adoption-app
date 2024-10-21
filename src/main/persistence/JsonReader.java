package persistence;

import model.AdoptApplication;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
// import org.junit.experimental.categories.Category;

// Represents a reader that reads user's applications from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads applications from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        // String name = jsonObject.getString("name");
        // String role = jsonObject.getString("role");
        // User user = new User(name, role);
        // addApplications(user, jsonObject);
        return new User("Lars", "adopter");
    }

    // MODIFIES: user
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addApplications(User user, JSONObject jsonObject) {
        // JSONArray jsonArray = jsonObject.getJSONArray("applications");
        // for (Object json : jsonArray) {
        //     JSONObject nextApplication = (JSONObject) json;
        //     addApplication(user, nextApplication);
        // }
    }

    // MODIFIES: user
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addApplication(User user, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String petname = jsonObject.getString("petname");
        // Category category = Category.valueOf(jsonObject.getString("category"));
        AdoptApplication application = new AdoptApplication(username, petname);
        user.submitApplication(application);
    }
}
