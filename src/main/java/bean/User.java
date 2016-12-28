package bean;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class User {
    private int userID;
    private int sessionCounter; // how many sessions per unique user per period
    private SortedMap<String, Group> groups; //structure for grouping

    /**
     * Constructor
     */
    public User(int userID) {
        this.userID = userID;
        this.sessionCounter = 0;
        groups = new TreeMap<String, Group>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * add group if not existed
     *
     * @param record
     */
    public void addGroup(Record record) {
        String groupName = record.getFirstLevel();
        //Get group from map; if not exists, then create it
        Group group = groups.get(groupName);
        if (group == null) {
            group = new Group(groupName);
            groups.put(groupName, group);
        }
        //add event under this group
        group.addEvent(record);
    }

    /**
     * increase session counter
     */
    public void increaseSessionCounter() {
        this.sessionCounter++;
    }

    /******* Getter and Setter **********/
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSessionCounter() {
        return sessionCounter;
    }

    public SortedMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(SortedMap<String, Group> groups) {
        this.groups = groups;
    }
}
