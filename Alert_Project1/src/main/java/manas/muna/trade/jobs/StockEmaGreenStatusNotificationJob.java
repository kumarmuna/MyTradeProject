package manas.muna.trade.jobs;

import manas.muna.trade.util.SendMail;
import manas.muna.trade.util.StockUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        System.out.println("StockEmaGreenStatusNotificationJob started.......");
        for (String stockName : StockUtil.loadStockNames()) {
//            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
            Path path = Paths.get("D:\\share-market\\Alert_Project1\\src\\main\\resources\\history_ema_data\\"+stockName+".csv");
            Map<String, String> notificationData = StockUtil.readEmaData(path.toString(), stockName);
            verifyAndSenfNotification(notificationData);
        }
        System.out.println("StockEmaGreenStatusNotificationJob end.......");
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) {
        if (Boolean.parseBoolean(notificationData.get("stockIsGreen"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"), notificationData.get("subject"));
        }
    }
}
