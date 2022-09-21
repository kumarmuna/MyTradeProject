package manas.muna.trade.jobs;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import manas.muna.trade.util.StockUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadingExcelAndCalculateEMAJob {
//    public static void main(String args[]) {
//        for (String stockName : StockUtil.loadStockNames()) {
//            System.out.print("Loading for.... "+stockName);
//            Map<String, Double> todaysEMA = readCVSData("D:\\share-market\\history_data\\"+stockName+".csv", 51.83, 51.90);
//            storeTodaysEma("D:\\share-market\\history_ema_data\\"+stockName+".csv", todaysEMA);
//        }
//    }

    public static void execute() {
        for (String stockName : StockUtil.loadStockNames()) {
            System.out.println("Loading for.... "+stockName);
            Path path = Paths.get(".\\src\\main\\resources\\history_data\\"+stockName+".csv");
            Path path1 = Paths.get(".\\src\\main\\resources\\history_ema_data\\"+stockName+".csv");
            Map<String, Double> yesterdayEMA = StockUtil.readPreviousDayEma(path1.toString());
            Map<String, Double> todaysEMA = readCVSData(path.toString(), yesterdayEMA.get("EMA30"), yesterdayEMA.get("EMA9"));
            storeTodaysEma(path1.toString(), todaysEMA);
        }
    }

    private static void storeTodaysEma(String filePath, Map<String, Double> todaysEMA) {
        double ema9 = todaysEMA.get("todaysEMA9");
        double ema30 = todaysEMA.get("todaysEMA30");

        File file = new File(filePath);
        String[] header = {"EMA30","EMA9"};
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            List<String[]> allData = csvReader.readAll();
            FileWriter outputfile = new FileWriter(file, false);
            CSVWriter writer = new CSVWriter(outputfile);

            String[] data = {Double.toString(ema30), Double.toString(ema9)};
            allData.add(0,data);
            allData.add(0,header);
            for (String[] dt : allData){
                writer.writeNext(dt);
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Double> readCVSData(String file, double prevDayEma30, double prevDayEma9) {
        double ema30 = 0;
        double ema9 = 0;
        double multiplier30 = 2.0/(30+1);
        double multiplier9 = 2.0/(9+1);
        Map<String, Double> todaysEMA = new HashMap<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            Collections.reverse(allData);
            //4th index column is Close
            int count = 0;
            double sum = 0;

            for (String[] row : allData) {
                if (count <= 30){
                //System.out.print(row[4]);
                sum = sum + Double.parseDouble(row[4]);
                if (count == 9){
                    ema9 = Double.parseDouble(allData.get(0)[4]) * multiplier9 + prevDayEma9 * (1-multiplier9);
                }
                count++;
                }else
                    break;
            }
            ema30 = Double.parseDouble(allData.get(0)[4]) * multiplier30 + prevDayEma30 * (1-multiplier30);
            todaysEMA.put("todaysEMA30", ema30);
            todaysEMA.put("todaysEMA9", ema9);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return todaysEMA;
    }
}
