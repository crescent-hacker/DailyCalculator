package bean;

import java.util.*;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class PeriodData {
    private SortedMap<Integer,User> users;
    private Long periodStamp;

    /**
     * Constructor
     */
    public PeriodData(Long periodStamp) {
        this.periodStamp = periodStamp;
        this.users = new TreeMap<Integer,User>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * add unique user into map
     */
    public void addUser(Record record) {
        Integer userID = record.getUserID();
        //get user from map; if not exists, then create it
        User user = users.get(userID);
        if(user == null){
            user = new User(userID);
            users.put(userID,user);
        }
        //add session count for a user
        user.increaseSessionCounter();
        user.addGroup(record);
    }

    /******* Getter and Setter **********/
    public Map<Integer, User> getUsers() {
        return users;
    }

    public Long getPeriodStamp() {
        return periodStamp;
    }

    public void setPeriodStamp(Long periodStamp) {
        this.periodStamp = periodStamp;
    }

}
