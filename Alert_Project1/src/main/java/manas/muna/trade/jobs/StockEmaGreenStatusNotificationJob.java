package manas.muna.trade.jobs;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import manas.muna.trade.util.SendMail;
import manas.muna.trade.util.StockUtil;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockEmaGreenStatusNotificationJob {
    public static void main(String args[]){
        for (String stockName : StockUtil.loadStockNames()) {
            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
            Map<String, String> notificationData = StockUtil.readEmaData(stockEmaDataLoad, stockName);
            verifyAndSenfNotification(notificationData);
        }
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) {
        if (Boolean.parseBoolean(notificationData.get("stockIsGreen"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"));
        }
    }
}
