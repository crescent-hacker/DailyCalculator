package bean;

/**
 * Author: Difan Chen
 * Date: 29/12/2016
 */
public class StatItem {
    private boolean isBlankUserExist;
    private int totalEvents;
    private int totalSessions;
    private int uniqueUsers;
    private float avgEventCountPerUser;

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(int totalSessions) {
        this.totalSessions = totalSessions;
    }

    public int getUniqueUsers() {
        return uniqueUsers;
    }

    public void setUniqueUsers(int uniqueUsers) {
        this.uniqueUsers = uniqueUsers;
    }

    public boolean isBlankUserExist() {
        return isBlankUserExist;
    }

    public void setBlankUserExist(boolean blankUserExist) {
        isBlankUserExist = blankUserExist;
    }

    public float getAvgEventCountPerUser() {
        return avgEventCountPerUser;
    }

    public void setAvgEventCountPerUser(float avgEventCountPerUser) {
        this.avgEventCountPerUser = avgEventCountPerUser;
    }
}
