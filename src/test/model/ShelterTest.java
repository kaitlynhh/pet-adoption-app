package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShelterTest {
    private Shelter testShelter;
    private Pet dog;
    private Pet cat;

    @BeforeEach
    void runBefore() {
        testShelter = new Shelter();
        dog = new Pet(1, "Kumo", 3, "Male", "dog", "Labrador");
        cat = new Pet(2, "Otto", 2, "Female", "cat", "Birman");
    }

    @Test
    void testConstructor() {
        assertTrue(testShelter.getPets().isEmpty());
    }

    @Test
    void testAddPet() {
        testShelter.addPet(dog);
        assertEquals(1, testShelter.getPets().size());
        assertEquals(dog, testShelter.getPets().get(0));
        testShelter.addPet(cat);
        assertEquals(2, testShelter.getPets().size());
        assertEquals(dog, testShelter.getPets().get(0));
        assertEquals(cat, testShelter.getPets().get(1));
    }

}
