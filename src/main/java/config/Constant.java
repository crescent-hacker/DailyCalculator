package config;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class Constant {
    final static public int FIELDS_AMOUNT = 9;
    final static public String END_SIGNAL =  "Maximum result limit reached,";
    final static public String FIELD_SPLITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    final static public String DATE_FORMAT_STR = "MMM dd, yyyy hh:mm a";
    final static public String OUTPUT_DATE_FORMAT_STR = "dd/MM/yyyy";
    final static public String EVENT_NAME_SPLITER = "-";
    final static public long PERIOD = 24*60*60*1000L; // 1 day
    final static public String SPECIAL_EVENT_CATEGORY = "Special";
    final static public int BLANK_USER_ID = -1;
}
