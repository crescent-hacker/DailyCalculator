package component;

import config.Constant;
import bean.Record;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class DataReader {
    //date formatter
    private SimpleDateFormat simpleDateFormater;
    //analyzer
    private Analyzer analyzer;

    /**
     * constructor
     */
    public DataReader(Analyzer analyzer){
        this.analyzer = analyzer;
        this.simpleDateFormater = new SimpleDateFormat(Constant.DATE_FORMAT_STR);
        this.simpleDateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));

    }

    /**
     * read file from the target file
     */
    public void readFile(String filePath) throws RuntimeException, IOException, ParseException {
        File targetFile = new File(filePath);

        //read lines
        BufferedReader br = new BufferedReader(new FileReader(targetFile));
        String line; // tmp line var
        boolean isFirstLine = true; // bool to indicate if its first line
        while ((line = br.readLine()) != null) {
            //check if it's first line
            if(isFirstLine) {
                isFirstLine = false;
                continue;
            }
            //stop reading when hit the maximum limit line
            if(Constant.END_SIGNAL.equals(line))
                break;
            //handle lines
            Record record = this.processLineData(line);
            analyzer.processRecord(record);
            //todo, if reach the analyse line limit, then launch a thread to analyze the current read data
        }

        br.close();

    }

    /**
     * process line data into event
     * @param line
     * @return
     */
    public Record processLineData(String line) throws ParseException,RuntimeException {
        //split
        String[] strs = line.split(Constant.FIELD_SPLITER, -1);
        //check fields amount
        if(strs.length - 1 != Constant.FIELDS_AMOUNT)
            throw new RuntimeException("Fields amount is not correct.\n"+line);
        //timestamp
        String timestampStr = strs[0].replace("\"","");
        long timestamp = simpleDateFormater.parse(timestampStr).getTime();

        //session index
        int sessionIdx = Integer.parseInt(strs[1]);

        //event name and group
        String[] eventFields = strs[2].split(Constant.EVENT_NAME_SPLITER);
        String firstLevel="",secondLevel=""; // when length of event fields is 0, the fields amount will be wrong as we check above
        if(eventFields.length == 1){
            //Categorised as special event
            firstLevel = Constant.SPECIAL_EVENT_CATEGORY;
            secondLevel = eventFields[0].trim();
        }
        if(eventFields.length == 2) {
            //Group normally
            firstLevel = eventFields[0].trim();
            secondLevel = eventFields[1].trim();
        }

        //desc,version,platform,device
        String description = strs[3].replace("\"","");
        String version = strs[4];
        String platform = strs[5];
        String device = strs[6].replace("\"","");

        //userID
        int userID;
        if("".equals(strs[7]))
            userID = Constant.BLANK_USER_ID; //the user id is blank
        else
            userID = Integer.parseInt(strs[7]);

        //params
        String params = strs[8];

        Record record = new Record(timestamp,sessionIdx,firstLevel,secondLevel,description,version,platform,device,userID,params);
        return record;
    }

}
