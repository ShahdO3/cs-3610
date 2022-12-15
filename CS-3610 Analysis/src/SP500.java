import java.io.*;
import java.util.*;

/**
 * @author Seif El-Bendary, Shahd Osman
 */

public class SP500 {
    private static final List<StockDaily> stockDailyList = new ArrayList<>();

    public static void main(String[] args) {
       readStockDailyFromCSV("C:\\Users\\Shahd Osman\\Documents\\Fall 2022-23\\CS-3610- Analysis and Design of Algorithms\\Historical Prices.csv");

//        Sample Input:
//        stockDailyList.add(new StockDaily("1/2/12", 2001));
//        stockDailyList.add(new StockDaily("2/2/12", 2030));
//        stockDailyList.add(new StockDaily("2/2/12", 2030));
//        stockDailyList.add(new StockDaily("3/2/12",  1900.95));
//        stockDailyList.add(new StockDaily("3/2/12",  1900.95));
//        stockDailyList.add(new StockDaily("4/2/12",  2001));
//        stockDailyList.add(new StockDaily("5/2/12", 2400));
//        stockDailyList.add(new StockDaily("11/2/12", 2001));
//        stockDailyList.add(new StockDaily("14/2/12",  2001));

//        for (int i = 0; i< stockDailyList.size(); i++) {
//            System.out.print(stockDailyList.get(i) + ", ");
//        }
//        System.out.println();
//
//
        List<Interval> l = getShocksIntervals_technique1(0.05);
        int n = 1;
        for (Interval i:
             l) {
            System.out.println(n+": "+i + ", ");
            n++;
        }

//        getShocksIntervals_technique2(3);
//        for (StockDaily n :
//                stockDailyList) {
//            System.out.println(n + ", ");
//        }
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

                String[] attributes = line.split(",");

                String date = attributes[0];
                float value = Float.parseFloat(attributes[1]);

                StockDaily stockDaily = new StockDaily(date, value);

                stockDailyList.add(stockDaily);

                line = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

//    public static void countingSort(int A [ ] , int B [ ] , int max, int size) {
//        int[] C = new int[max];
//        int i , j , k ;
//        for ( i =0; i <max; i ++) C[ i ] =0;
//        for ( i = 0 ; i < size ; i ++)
//        {
//            j = A [ i ] ;
//            C [ j ] ++ ;
//        }
//        for ( i = 1 ; i < max ; i ++) C [ i ] += C [ i - 1 ] ;
//        {
//            j = A [ i ] ; /* j i s the po s itio n i n vector C */
//            k = C [ j ] ; /* k i s the kt h - p o s i t i o n i n the new vector B */
//            B [ k - 1 ] = A [ i ] ; /* weput value o f vector A i n vector B */
//            C [ j ] = C [ j ] - 1 ; /* we decrease the value o f C [ j ] by one, to place other equal valuesi n to the vector */
//        }
//    }

    /**
     * Brute Force algorithm, time complexity of O(n^3)
     * we go through each stockDaily and compare it with each other, we get the % of each value and find the % drop
     * when found we then search for the same value again
     * @param percentage type double, the a% we are finding
     * @return List<Interval> containing the interval where the drop occurred
     */
    public static List<Interval> getShocksIntervals_technique1(double percentage){
        List<Interval> l = new ArrayList<>();
        StockDaily current, drop, backUp; double val; int end = 0;

        for(int i = 0; i< stockDailyList.size()-1; i++){
            current = stockDailyList.get(i);

            val = Math.round(current.value * percentage); //a% of the current value

            for(int j = i+1; j< stockDailyList.size()-1; j++){
                drop = stockDailyList.get(j);
                if(end == 1){ // if we already found the interval in this range we break this loop
                    end = 0; break;
                }
                 if (drop.value == current.value-val){
                     for(int k = j+1; k< stockDailyList.size()-1; k++){
                         backUp = stockDailyList.get(k);

                         if (backUp.value == current.value){
                             Interval interval = new Interval(current, backUp);

                                l.add(interval);
                                i = k+1; j = i+1; end = 1;
                            break; //since we found the interval we stop this loop
                              }}}}}
        return l;
    }

    public static List<Interval> getShocksIntervals_technique2(double percentage){
        List<Interval> l = new ArrayList<>();
        List<StockDaily> duplicates = new ArrayList<>(); //that contain all the stockDaily's with the same values
        stockDailyList.sort(new stockDailyComparator());

        for (int i = 0; i < stockDailyList.size()-2; i++){
            StockDaily temp = stockDailyList.get(i);
            StockDaily temp1 = stockDailyList.get(i+1);

            if (temp.value == temp1.value){
                duplicates.add(temp); duplicates.add(temp1);

            }
        }

        for (int i = 0; i < stockDailyList.size(); i++) {
            StockDaily temp = duplicates.get(i);

            double val = Math.round(temp.value) - (temp.value * percentage);
        }



//        for (StockDaily n :
//                duplicates) {
//            System.out.println(n + ", ");
//        }


        return l;
    }
}

