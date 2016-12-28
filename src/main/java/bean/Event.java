package bean;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class Event {
    private String eventName;
    private String groupName;

    /**
     * Constructor
     */
    public Event(String eventName,String groupName) {
        this.eventName = eventName;
        this.groupName = groupName;
    }



    /******* Getter and Setter **********/
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
