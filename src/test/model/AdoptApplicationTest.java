package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdoptApplicationTest {
    private AdoptApplication testApplication;

    @BeforeEach
    void runBefore() {
        testApplication = new AdoptApplication(1,2,3);

    }

    @Test
    void testConstructor() {
        assertEquals(1, testApplication.getApid());
        assertEquals(2, testApplication.getUserid());
        assertEquals(3, testApplication.getPetid());
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
