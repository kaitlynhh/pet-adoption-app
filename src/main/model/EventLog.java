package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class EventLog implements Iterable<Event>{

    private static EventLog theLog;
    private Collection<Event> events;

    // Prevent ecternal construction (Singleton Design Pattern)
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // Gets instance of EventLog - creates it
    // if it doesn't already exist.
    // (Singleton design pattern)
    // @return instance of EventLog
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // Adds an event to the event log.
    // @param e the event to be added
    public void logEvent(Event e) {
        events.add(e);
    }

    // Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }


    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }

}
