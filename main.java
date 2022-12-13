import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
/**
     * Simple Java program to read CSV file in Java. In this program we will read
     * list of books stored in CSV file as comma separated values.
     *
     * @author WINDOWS 8
     *
     */
    public class main {

        public static void main(String[] args) {
            List<StockDaily> stockDailyList = readStockDailyFromCSV("C:\\Users\\Shahd Osman\\Documents\\Fall 2022-23\\CS-3610- Analysis and Design of Algorithms\\Historical Prices.csv");

            // let's print all the person read from CSV file
            for (StockDaily stockDaily : stockDailyList) {
                System.out.println(stockDailyList);
            }
            System.out.println("h");
        }

        private static List<StockDaily> readStockDailyFromCSV(String fileName) {
            List<StockDaily> stockDailyList = new ArrayList<>();
            Path pathToFile = Paths.get(fileName);

            // create an instance of BufferedReader
            // using try with resource, Java 7 feature to close resources
            try (BufferedReader br = Files.newBufferedReader(pathToFile,
                    StandardCharsets.US_ASCII)) {

                // read the first line from the text file
                String line = br.readLine();
                line = br.readLine();
                // loop until all lines are read
                while (line != null) {

                    // use string.split to load a string array with the values from
                    // each line of
                    // the file, using a comma as the delimiter
                    String[] attributes = line.split(",");

                    StockDaily stockDaily = createStockDaily(attributes);

                    // adding book into ArrayList
                    stockDailyList.add(stockDaily);

                    // read next line before looping
                    // if end of file reached, line would be null
                    line = br.readLine();
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            return stockDailyList;
        }

        private static StockDaily createStockDaily(String[] metadata) {
            String date = metadata[0];
            float value = Float.parseFloat(metadata[1]);


            // create and return book of this metadata
            return new StockDaily(date, value);
        }

    }

