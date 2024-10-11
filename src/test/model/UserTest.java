package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User testUser;
    private AdoptApplication application;


    @BeforeEach
    void runBefore() {
        testUser = new User("Alpha", "Adopter");
        application = new AdoptApplication("Alpha","Otto");
    }

    @Test
    void testConstructor() {
        assertEquals("Alpha", testUser.getName());
        assertEquals("Adopter", testUser.getRole());
        assertTrue(testUser.getApplications().isEmpty());
        assertTrue(testUser.getStories().isEmpty());
        assertTrue(testUser.getAdoptedPets().isEmpty());
    }

    @Test
    void testSubmitAp() {
        testUser.submitApplication(application);
        assertEquals(1, testUser.getApplications().size());
        assertEquals(application, testUser.getApplications().get(0));

    }

    @Test
    void testAddAdoptStory() {
        testUser.addAdoptStory("I take my dog to the dog park today!");
        assertEquals(1, testUser.getStories().size());
        assertEquals("I take my dog to the dog park today!", testUser.getStories().get(0));
        testUser.addAdoptStory("I celebrate my dog's birthday today!");
        assertEquals(2, testUser.getStories().size());
        assertEquals("I celebrate my dog's birthday today!", testUser.getStories().get(1));
    }
    
}
