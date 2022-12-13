import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seif El-Bendary, Shahd Osman
 */

public class SP500 {
    private static final List<StockDaily> stockDailyList = new ArrayList<>();

    public static void main(String[] args) {
       readStockDailyFromCSV("C:\\Users\\Shahd Osman\\Documents\\Fall 2022-23\\CS-3610- Analysis and Design of Algorithms\\Historical Prices.csv");

//        stockDailyList.add(new StockDaily("1/2/12", 2001));
//        stockDailyList.add(new StockDaily("2/2/12", 2030));
//        stockDailyList.add(new StockDaily("3/2/12", (float) 1900.95));
//        stockDailyList.add(new StockDaily("4/2/12",  2001));
//        stockDailyList.add(new StockDaily("11/2/12", 2001));
//        stockDailyList.add(new StockDaily("2/2/12", 2030));
//        stockDailyList.add(new StockDaily("3/2/12", (float) 1900.95));
//        stockDailyList.add(new StockDaily("14/2/12",  2001));
//        stockDailyList.add(new StockDaily("5/2/12", 2400));

//         let's print all the person read from CSV file
//        for (int i = 0; i< stockDailyList.size(); i++) {
//            System.out.print(stockDailyList.get(i) + ", ");
//        }
        System.out.println();

        List<Interval> l = getShocksIntervals_technique1(0.25);
        int n = 1;
        for (Interval i:
             l) {
            System.out.println(n+": "+i + ", ");
            n++;
        }
    }

    private static void readStockDailyFromCSV(String path) {

        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            // read the first line from the text file

            String line = br.readLine();
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");


                String date = attributes[0];
                float value = Float.parseFloat(attributes[1]);


                StockDaily stockDaily = new StockDaily(date, value);

                // adding book into ArrayList
                stockDailyList.add(stockDaily);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public static List<Interval> getShocksIntervals_technique1(double percentage){
        List<Interval> l = new ArrayList<>();
        StockDaily current, next; double val;


        for(int i = 0; i< stockDailyList.size()-1; i++){
            current = stockDailyList.get(i);
//            if (l.contains(current) == true)
//                break;

            val = Math.round((current.value * percentage)); //-a%

            for(int j = i; j< stockDailyList.size()-1; j++){
                next = stockDailyList.get(j);
//
//
//                System.out.println("val: "+ val + " next.val: " + next.value);
//                System.out.println(val == next.value);

                 if ((float)next.value == current.value-val){
                 for(int k = j; k< stockDailyList.size()-1; k++){
                     next = stockDailyList.get(k);

                     val = Math.round((current.value * percentage)); //a%

//                     System.out.println("Inner Loop:\nval: "+ val + " next.val: " + next.value);
//                     System.out.println(val == next.value);

                     if ((float)next.value == current.value){
                         Interval interval = new Interval(current, next);
                         if (l.contains(interval) == false)
                            l.add(interval);
                     }
                 }
             }
        }}

        return l;
    }
}

