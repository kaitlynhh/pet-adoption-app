package persistence;

import model.AdoptApplication;
import model.User;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// import org.junit.experimental.categories.Category;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoApplication() {
        JsonReader reader = new JsonReader("./data/testReaderNoApplication.json");
        try {
            User user = reader.read();
            assertEquals("Lars", user.getName());
            assertEquals("Adopter", user.getRole());
            assertEquals(0, user.getApplications().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("Lars", user.getName());
            List<AdoptApplication> applications = user.getApplications();
            assertEquals(2, applications.size());
            checkApplication("Lars", "Otto", "submitted", applications.get(0));
            checkApplication("Lars", "Bob", "submitted", applications.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
