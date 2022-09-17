package manas.muna.trade.jobs;

import manas.muna.trade.util.SendMail;
import manas.muna.trade.util.StockUtil;

import java.util.Map;

public class StockEmaRedNotificationJob {
    public static void main(String args[]) {
        for (String stockName : StockUtil.loadBuyStockNames()) {
            String stockEmaDataLoad = "D:\\share-market\\history_ema_data\\"+stockName+".csv";
            Map<String, String> notificationData = StockUtil.readEmaBuyStok(stockEmaDataLoad, stockName);
            verifyAndSenfNotification(notificationData);
        }
    }

    private static void verifyAndSenfNotification(Map<String, String> notificationData) {
        if (Boolean.parseBoolean(notificationData.get("stockIsRed"))){
            SendMail.sendMail(notificationData.get("msg"), notificationData.get("stockName"));
        }
    }
}
