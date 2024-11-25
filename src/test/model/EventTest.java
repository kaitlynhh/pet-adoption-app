package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event evt;
    private Date dt;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        evt = new Event("Sensor open at door"); // (1)
        dt = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", evt.getDescription());
        assertEquals(dt, evt.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(dt.toString() + "\n" + "Sensor open at door", evt.toString());
    }
}
