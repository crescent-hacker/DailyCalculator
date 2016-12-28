package bean;

import java.util.*;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class StatModel {
    /**
     * structures for store
     */
    private SortedMap<Long,PeriodData> periodDataMap;

    /**
     * structures for stat
     */
    private SortedMap<Long,StatItem> periodStatItemMap;

    /**
     * Constructor
     */
    public StatModel() {
        periodDataMap = new TreeMap<Long,PeriodData>(new Comparator<Long>() {
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        periodStatItemMap = new TreeMap<Long,StatItem>(new Comparator<Long>() {
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Add daily user record
     */
    public void addPeriodData(Record record) {
        Long periodStamp = record.getPeriod();
        //Get period data from map; if not exists, then create it
        PeriodData periodData = periodDataMap.get(periodStamp);
        if(periodData == null){
            periodData = new PeriodData(periodStamp);
            periodDataMap.put(periodStamp,periodData);
        }
        //add user under the period
        periodData.addUser(record);
    }

    /**************************
     * GETTER AND SETTER
     ****************************/
    public SortedMap<Long, PeriodData> getPeriodDataMap() {
        return periodDataMap;
    }

    public void setPeriodDataMap(SortedMap<Long, PeriodData> periodDataMap) {
        this.periodDataMap = periodDataMap;
    }

    public Map<Long, StatItem> getPeriodStatItemMap() {
        return periodStatItemMap;
    }
}
