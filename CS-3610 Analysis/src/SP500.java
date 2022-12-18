import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Seif El-Bendary, Shahd Osman
 */

public class SP500 {
    private static final List<StockDaily> stockDailyList = new ArrayList<>();

    public static void main(String[] args) throws ParseException {
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
        List<Interval> l2 = getShocksIntervals_technique2(0.05);
        int n = 1;
        for (Interval i:
             l) {
            System.out.println(n+": "+i + ", ");
            n++;
        }
        n = 1;
        System.out.println("\nTechnique 2:\n");
        for (Interval i:
                l2) {
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

//        stockDailyList.sort(new dateComparator());
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
                                i = k; j = i+1; end = 1;
                            break; //since we found the interval we stop this loop
                              }}}}}
        return l;
    }

    /**
     * o(nlogn)
     * @param percentage type double, the a% we are finding
     * @return List<Interval> containing the interval where the drop occurred
     * @throws ParseException since we are parsing a string to Date
     */
    public static List<Interval> getShocksIntervals_technique2(double percentage) throws ParseException {
        List<Interval> l = new ArrayList<>();

        List<List<StockDaily>> duplicates = new ArrayList<>(); //that contain all the stockDaily's with the same values

        stockDailyList.sort(new stockDailyComparator()); //o(n logn)

        for (int i = 0; i < stockDailyList.size()-2; i++){ //o(n)
            StockDaily temp = stockDailyList.get(i);
            StockDaily temp1 = stockDailyList.get(i+1);

            if (temp.value == temp1.value){
                if (!duplicates.isEmpty()) {
                    if (temp.value == duplicates.get(duplicates.size()-1).get(0).value){
                        duplicates.get(duplicates.size()-1).add(temp);
                        duplicates.get(duplicates.size()-1).add(temp1);
                    }else {

                        List<StockDaily> s = new ArrayList<>();
                        s.add(temp);
                        s.add(temp1);
                        duplicates.add(s);
                    }

                }else {
                    List<StockDaily> s = new ArrayList<>();
                    s.add(temp);
                    s.add(temp1);
                    duplicates.add(s);
                }

            }
        }


        Interval lastI = new Interval(duplicates.get(0).get(0), duplicates.get(0).get(0));
        Date largestSavedDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1999");


        for (int i = 0; i < duplicates.size()-1; i++) { // o(n)

            for (int j = 0; j< duplicates.get(i).size()-1; j+=2){
                List<StockDaily> curr = duplicates.get(i);

                curr.sort(new dateComparator());
                StockDaily temp = curr.get(j);
                StockDaily temp2 = curr.get(j+1);
//                System.out.println("temp = "+temp + "temp2 = "+temp2);

                Date lastAddedEndD =new SimpleDateFormat("MM/dd/yyyy").parse(lastI.end.getDate());
                Date lastAddedStartD =new SimpleDateFormat("MM/dd/yyyy").parse(lastI.start.getDate());

                double val = temp.value - Math.round((temp.value * percentage));

                int index = Collections.binarySearch(stockDailyList, new StockDaily("", val), new DuplicateComparator()); // o(logn)

                Date start =new SimpleDateFormat("MM/dd/yyyy").parse(temp.getDate());
                Date end =new SimpleDateFormat("MM/dd/yyyy").parse(temp2.getDate());

                if(index>0) {

//                    if ((lastAddedEndD.before(end)) && (lastAddedStartD.after(start))) {
                    if (((start.after(lastAddedEndD)) && (end.after(lastAddedEndD)) )
                            || ((start.before(lastAddedStartD)) && (end.before(lastAddedStartD)))){
                        if (end.after(largestSavedDate)) {
                            Date drop = new SimpleDateFormat("MM/dd/yyyy").parse(stockDailyList.get(index).getDate());

                            if (((drop.before(start)) && (drop.after(end))) || ((drop.after(start)) && (drop.before(end)))) {
                                Interval interval = new Interval(temp, temp2);
                                l.add(interval);
                                largestSavedDate = end;

                                lastI = interval;
                            }
                        }
                    }
                }
        }}



//        for (List<StockDaily> n :
//                duplicates) {
//            System.out.println(n + ", ");
//        }


        return l;
    }
}

