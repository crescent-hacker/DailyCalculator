package bean;

import config.Constant;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class Record {
    private long timestamp;
    private long period; //the start timestamp of each period/day
    private int sessionIndex;
    private String firstLevel;
    private String secondLevel;
    private String description;
    private String version;
    String platform;
    String device;
    int userID;
    String params;

    /**
     * Constructor
     */
    public Record(long timestamp, int sessionIndex, String firstLevel, String secondLevel, String description, String version, String platform, String device, int userID, String params) {
        this.timestamp = timestamp;
        this.period = timestamp - (timestamp%(Constant.PERIOD));
        this.sessionIndex = sessionIndex;
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
        this.description = description;
        this.version = version;
        this.platform = platform;
        this.device = device;
        this.userID = userID;
        this.params = params;
    }

    /******* Getter and Setter **********/
    public long getTimestamp() {
        return timestamp;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getSessionIndex() {
        return sessionIndex;
    }

    public void setSessionIndex(int sessionIndex) {
        this.sessionIndex = sessionIndex;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
