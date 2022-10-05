package manas.muna.trade.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StockUtil {

    public static String[] loadStockNames() {
        Properties p = new Properties();
        try {
            Path path = Paths.get("D:\\share-market\\Alert_Project1\\src\\main\\resources\\stock.properties");
            FileReader reader = new FileReader(path.toString());
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
            Path path = Paths.get("D:\\share-market\\Alert_Project1\\src\\main\\resources\\buy-stock.properties");
            FileReader reader = new FileReader(path.toString());
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
            File file = new File(stockEmaDataLoad);
            if (!file.exists())
                file.createNewFile();
            FileReader filereader = new FileReader(file);
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
            File file = new File(stockEmaDataLoad);
            if (!file.exists())
                file.createNewFile();
            FileReader filereader = new FileReader(file);
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

    public static Map<String, Double> readPreviousDayEma(String stockEmaDataLoad) {
        Map<String, Double> yesterdayEMA = new HashMap<>();
        try {
            File file = new File(stockEmaDataLoad);
            if (!file.exists())
                file.createNewFile();
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            if (allData.size()!=0) {
                String[] data = allData.get(0);
                yesterdayEMA.put("EMA30", Double.parseDouble(data[0]));
                yesterdayEMA.put("EMA9", Double.parseDouble(data[0]));
            }else {
                yesterdayEMA.put("EMA30", 0.0);
                yesterdayEMA.put("EMA9", 0.0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return yesterdayEMA;
    }
}
