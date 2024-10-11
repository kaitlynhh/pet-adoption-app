package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PetTest {

    private Pet testPet;

    @BeforeEach
    void runBefore() {
        testPet = new Pet("Otto", "female", "Dog", "Golden");

    }

    @Test
    void testConstructor() {
        assertEquals("Otto", testPet.getPetName());
        assertEquals("female", testPet.getGender());
        assertEquals("Dog", testPet.getSpecies());
        assertEquals("Golden", testPet.getBreed());
        assertEquals("available", testPet.getAdoptionStatus());
    }

    @Test
    void testMarkAsAdopted() {
        testPet.markAsAdopted();
        assertEquals("adopted", testPet.getAdoptionStatus());
    }

    @Test
    void testIsAvailable() {
        assertTrue(testPet.isAvailable());
        testPet.markAsAdopted();
        assertFalse(testPet.isAvailable());
    }


}
