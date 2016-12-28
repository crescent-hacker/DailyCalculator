import component.Analyzer;
import component.DataReader;
import bean.StatModel;

/**
 * Author: Difan Chen
 * Date: 28/12/2016
 */
public class TaskRunner {
    public static void main(String[] args) {
        StatModel statModel = new StatModel();
        Analyzer analyzer = new Analyzer(statModel);
        DataReader dataReader = new DataReader(analyzer);
        try {
            dataReader.readFile("/path/to/file");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        analyzer.statistic();
        analyzer.showStat();
    }
}
