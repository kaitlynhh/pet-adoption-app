package persistence;

import model.User;
import model.AdoptApplication;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Lars","Adopter");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoApplication() {
        try {
            User user = new User("Lars", "Adopter");
            JsonWriter writer = new JsonWriter("./data/testWriterNoApplication.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoApplication.json");
            user = reader.read();
            assertEquals("Lars", user.getName());
            assertEquals("Adopter", user.getRole());
            assertEquals(0, user.getApplications().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("Lars", "Adopter");
            user.submitApplication(new AdoptApplication("Lars", "Otto"));
            user.submitApplication(new AdoptApplication("Lars", "Bob"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("Lars", user.getName());
            List<AdoptApplication> applications = user.getApplications();
            assertEquals(2, applications.size());
            checkApplication("Lars", "Otto", "pending", applications.get(0));
            checkApplication("Lars", "Bob", "pending", applications.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
