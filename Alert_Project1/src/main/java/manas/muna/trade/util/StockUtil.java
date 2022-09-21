package manas.muna.trade.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StockUtil {

    public static String[] loadStockNames() {
        Properties p = new Properties();
        try {
            FileReader reader = new FileReader("D:\\share-market\\Alert_Project1\\src\\main\\resources\\stock.properties");
            p.load(reader);
            p.getProperty("stock_list");
        }catch (Exception e){
            e.printStackTrace();
        }
        return p.getProperty("stock_list").split(",");
    }

    public static String[] loadBuyStockNames() {
        Properties p = new Properties();
        try {
            FileReader reader = new FileReader("D:\\share-market\\Alert_Project1\\src\\main\\resources\\buy-stock.properties");
            p.load(reader);
            p.getProperty("stock_list");
        }catch (Exception e){
            e.printStackTrace();
        }
        return p.getProperty("stock_list").split(",");
    }

    public static Map<String, String> readEmaData(String stockEmaDataLoad, String stockName) {
        Map<String, String> notificationData = new HashMap<>();
        try {
            int countDay = 0;
            int stockIsGreen = 0;
            FileReader filereader = new FileReader(stockEmaDataLoad);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            for (String[] data : allData){
                if (countDay < 3){
                    if (Double.parseDouble(data[0]) < Double.parseDouble(data[1])) {
                        stockIsGreen++;
                    }
                    countDay++;
                }else
                    break;
            }
            if (stockIsGreen == 3){
                notificationData.put("stockIsGreen", "true");
                notificationData.put("stockName", stockName);
                String msg = "Stock "+stockName+" is green last 3 days, Have a look once.";
                notificationData.put("msg", msg);
                String subject = "GREEN: This is "+stockName+" Stock Alert.....";
                notificationData.put("subject", subject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notificationData;
    }

    public static Map<String, String> readEmaBuyStok(String stockEmaDataLoad, String stockName) {
        Map<String, String> notificationData = new HashMap<>();
        try {
            FileReader filereader = new FileReader(stockEmaDataLoad);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            String[] data = allData.get(0);
            if (Double.parseDouble(data[0]) >= Double.parseDouble(data[1])) {
                notificationData.put("stockIsRed", "true");
                notificationData.put("stockName", stockName);
                String msg = "Your Buy Stock "+stockName+"'s EMA is RED, Have a look once.";
                notificationData.put("msg", msg);
                String subject = "RED: This is "+stockName+" Stock Alert.....";
                notificationData.put("subject", subject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return notificationData;
    }
}
