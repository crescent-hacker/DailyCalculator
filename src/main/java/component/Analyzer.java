package component;

import bean.*;
import config.Constant;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class Analyzer {
    //output date format
    private SimpleDateFormat simpleDateFormator;
    //statistic model
    private StatModel statModel;

    public Analyzer(StatModel statModel) {
        this.statModel = statModel;
        simpleDateFormator = new SimpleDateFormat(Constant.OUTPUT_DATE_FORMAT_STR);
    }

    public StatModel getStatModel() {
        return statModel;
    }

    public void setStatModel(StatModel statModel) {
        this.statModel = statModel;
    }

    /**
     * process line as data of fields
     */
    public void processRecord(Record record) {
        statModel.addPeriodData(record);
    }

    /**
     * Stat(per day):
     * 1. Unique users
     * 2. Average event count
     * 3. Total events
     * 4. Total sessions
     */
    public void statistic() {
        //stat storage
        Map<Long, StatItem> statMap = statModel.getPeriodStatItemMap();
        //stat begin
        Map<Long, PeriodData> storeMap = statModel.getPeriodDataMap();
        for (Map.Entry<Long, PeriodData> periodEntry : storeMap.entrySet()) {
            StatItem statItem = new StatItem();
            Map<Integer, User> users = periodEntry.getValue().getUsers();
            statItem.setUniqueUsers(users.size());
            if (users.get(Constant.BLANK_USER_ID) != null)
                statItem.setBlankUserExist(true);
            //total events and sessions
            int eventCounter = 0, sessionCounter = 0;
            for (Map.Entry<Integer, User> userEntry : users.entrySet()) {
                sessionCounter += userEntry.getValue().getSessionCounter();
                for (Map.Entry<String, Group> groupEntry : userEntry.getValue().getGroups().entrySet())
                    eventCounter += groupEntry.getValue().getEvents().size();
            }
            statItem.setTotalEvents(eventCounter);
            statItem.setTotalSessions(sessionCounter);
            // avg event count = total events / number of unique users
            float avgEventCount = (float)eventCounter/users.size();
            statItem.setAvgEventCountPerUser(avgEventCount);

            statMap.put(periodEntry.getKey(),statItem);
        }
    }

    /**
     * show stat result
     */
    public void showStat() {
        Map<Long, StatItem> statMap = statModel.getPeriodStatItemMap();
        for (Map.Entry<Long, StatItem> periodEntry : statMap.entrySet()) {
            StatItem statItem = periodEntry.getValue();
            //print date
            String dateStr = simpleDateFormator.format(new Date(periodEntry.getKey()));
            System.out.println("\n\n=============================================");
            System.out.println("=========       Date " + dateStr + "     =========");
            System.out.println("=============================================");
            //unique users per day
            String additionalMsg = "";
            if (statItem.isBlankUserExist())
                additionalMsg = " including blank user ID as one unique user.";
            System.out.println("\nUnique users: " + statItem.getUniqueUsers() + additionalMsg);
            //average event count per day
            System.out.println("\nAverage event count: " + statItem.getAvgEventCountPerUser());
            //total events and sessions per day
            System.out.println("\nTotal events: " + statItem.getTotalEvents());
            System.out.println("\nTotal sessions: " + statItem.getTotalSessions());

        }
    }
}
