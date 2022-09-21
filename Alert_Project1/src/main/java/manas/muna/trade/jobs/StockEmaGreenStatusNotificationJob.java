package manas.muna.trade.jobs;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import manas.muna.trade.util.SendMail;
import manas.muna.trade.util.StockUtil;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockEmaGreenStatusNotificationJob {
//    public static void main(String args[]){
//        for (String stockName : StockUtil.loadStockNames()) {
//            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
//            Map<String, String> notificationData = StockUtil.readEmaData(stockEmaDataLoad, stockName);
//            verifyAndSenfNotification(notificationData);
//        }
//    }

    public static void execute() {
        for (String stockName : StockUtil.loadStockNames()) {
//            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
            Path path = Paths.get(".\\src\\main\\resources\\history_ema_data\\"+stockName+".csv");
            Map<String, String> notificationData = StockUtil.readEmaData(path.toString(), stockName);
            verifyAndSenfNotification(notificationData);
        }
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) {
        if (Boolean.parseBoolean(notificationData.get("stockIsGreen"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"), notificationData.get("subject"));
        }
    }
}
