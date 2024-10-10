package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User testUser;
    private AdoptApplication application;


    @BeforeEach
    void runBefore() {
        testUser = new User(1, "Alpha", "Adopter");
        application = new AdoptApplication();
    }

    @Test
    void testConstructor() {
        assertEquals(1, testUser.getId());
        assertEquals("Alpha", testUser.getName());
        assertEquals("Adopter", testUser.getRole());
        assertTrue(testUser.getApplications().isEmpty());
    }

    @Test
    void testSubmitAp() {
        testUser.submitApplication(application);
        assertEquals(1, testUser.getApplications().size());
        assertEquals(application, testUser.getApplications().get(0));

    }
    
}
