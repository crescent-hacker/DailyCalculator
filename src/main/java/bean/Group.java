package bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class Group {
    Map<String,Event> events;
    String groupName;

    /**
     * Constructor
     */
    public Group(String groupName) {
        this.groupName = groupName;
        this.events = new HashMap<String, Event>();
    }

    /**
     * Add event from a record -- for grouping
     */
    public void addEvent(Record record) {
        String eventName = record.getSecondLevel();
        //Get event from map; if not exists, then create it
        Event event = events.get(eventName);
        if(event == null){
            event = new Event(eventName,record.getFirstLevel());
            events.put(eventName ,event);
        }
    }

    /******* Getter and Setter **********/
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }
}
