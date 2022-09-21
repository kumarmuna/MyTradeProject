package manas.muna.trade.jobs;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RunJobScheduler extends TimerTask {

    @Override
    public void run() {
        System.out.println("Job run started....." + new Date() + " :: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        Date date5pm = new java.util.Date();
        date5pm.setHours(17);
        date5pm.setMinutes(0);
        Date date6pm = date5pm;
        date6pm.setHours(18);
        date6pm.setMinutes(0);
        Date date7pm = date5pm;
        date7pm.setHours(19);
        date7pm.setMinutes(0);
        Date date7_3pm = date5pm;
        date7_3pm.setHours(19);
        date7_3pm.setMinutes(30);
        Timer timer = new Timer(); // Instantiating a timer object

        StoreStockHistoryToCvsJobScheduler task1 = new StoreStockHistoryToCvsJobScheduler();
        timer.schedule(task1, date5pm, 86400000); // Scheduling it to be executed with fixed rate at every two seconds

        ReadingExcelAndCalculateEMAJobScheduler task2 = new ReadingExcelAndCalculateEMAJobScheduler(); // Creating another FixedRateSchedulingUsingTimerTask
        timer.schedule(task2, date6pm, 86400000); // Scheduling it to be executed with fixed delay at every two seconds

        StockEmaRedNotificationForBuyStockJobScheduler task3 = new StockEmaRedNotificationForBuyStockJobScheduler(); // Creating another FixedRateSchedulingUsingTimerTask
        timer.schedule(task3, date7pm, 86400000); // Scheduling it to be executed with fixed delay at every two seconds

        StockEmaGreenStatusNotificationJobScheduler task4 = new StockEmaGreenStatusNotificationJobScheduler();
        timer.schedule(task4, date7_3pm, 86400000); // Scheduling it to be executed with fixed rate at every two seconds

//        RunJobScheduler task1 = new RunJobScheduler();
//        timer.schedule(task1, date5pm, 120000); // Scheduling it to be executed with fixed rate at every two seconds
//
//        RunJobScheduler task2 = new RunJobScheduler(); // Creating another FixedRateSchedulingUsingTimerTask
//        timer.schedule(task2, date6pm, 120000); // Scheduling it to be executed with fixed delay at every two seconds
//
//        RunJobScheduler task3 = new RunJobScheduler(); // Creating another FixedRateSchedulingUsingTimerTask
//        timer.schedule(task3, date7pm, 120000); // Scheduling it to be executed with fixed delay at every two seconds
//
//        RunJobScheduler task4 = new RunJobScheduler();
//        timer.schedule(task4, date7_3pm, 120000); // Scheduling it to be executed with fixed rate at every two seconds

    }
}
