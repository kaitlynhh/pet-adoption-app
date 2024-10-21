package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.experimental.categories.Category;

import model.AdoptApplication;

public class JsonTest {
    protected void checkApplication(String username, String petname,
            String status, AdoptApplication application) {
        assertEquals(username, application.getUsername());
        assertEquals(petname, application.getPetname());
        assertEquals(status, application.getStatus());
    }
}
