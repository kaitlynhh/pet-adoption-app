package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdoptApplicationTest {
    private AdoptApplication testApplication;

    @BeforeEach
    void runBefore() {
        testApplication = new AdoptApplication("Alpha","Otto");

    }

    @Test
    void testConstructor() {
        assertEquals("Alpha", testApplication.getUsername());
        assertEquals("Otto", testApplication.getPetname());
        assertEquals("pending", testApplication.getStatus());
    }

    @Test
    void testUpdateStatus() {
        testApplication.updateStatus("approved");
        assertEquals("approved", testApplication.getStatus());
        testApplication.updateStatus("submitted");
        assertEquals("submitted", testApplication.getStatus());
    }


}
