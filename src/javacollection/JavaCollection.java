package javacollection;

/**
 *
 * @author Warocha Wichankul 61130700352
 */
import java.io.BufferedWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import org.apache.commons.csv.CSVPrinter;

public class JavaCollection {
    public String saleNo;
    public String product;
    
    public static void main(String[] args) throws IOException {
        //Declare Variable
        String INPUT_FILE = "./Sale.txt";
        String OUTPUT_FILE = "./Compare.csv";
        char QUOTE = '\'';
        char ESCAPE = '"';
        char DELIMITER = '|';
        HashMap<Integer,JavaCollection> hMap = new HashMap<>();
        LinkedHashMap<Integer,JavaCollection> lhMap = new LinkedHashMap<>();
        TreeMap<Integer,JavaCollection> tMap = new TreeMap<>();
        HashMap<Integer,Long> hTimeMap = new HashMap<>();
        HashMap<Integer,Long> lhTimeMap = new HashMap<>();
        HashMap<Integer,Long> tTimeMap = new HashMap<>();
        int[] searchs = new int[] {1, 10, 100, 1000, 10000, 20000, 30000, 40000, 50000, 60000, 60855, 70000};
        
        try {
            //Read file
            Reader reader = Files.newBufferedReader(Paths.get(INPUT_FILE));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withQuote(QUOTE).withEscape(ESCAPE));
            for (CSVRecord csvRecord : csvParser) {
                //Get Value from file
                Integer no = Integer.parseInt(csvRecord.get(0));
                String saleNo = csvRecord.get(1);
                String product = csvRecord.get(2);
                //Assign value to Object                
                JavaCollection obj = new JavaCollection();
                obj.saleNo = saleNo;
                obj.product = product;
                //Put Object to Map
                hMap.put(no, obj);
                lhMap.put(no, obj);
                tMap.put(no, obj);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        //Compare
        System.out.println("HashMap");
        for (int i=0; i< searchs.length; i++)
        {
            long startTimeH = System.nanoTime();
            try{
                JavaCollection hM = hMap.get(searchs[i]);
                long endTimeH = System.nanoTime();
                long HTime = endTimeH-startTimeH;
                hTimeMap.put(searchs[i], HTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",HTime) + " nanoseconds");
            } catch (Exception e) {
                long endTimeH = System.nanoTime();
                long HTime = endTimeH-startTimeH;
                hTimeMap.put(searchs[i], HTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",HTime) + " nanoseconds");
            }
        }
        System.out.println("LinkedHashMap");
        for (int i=0; i< searchs.length; i++)
        {
            long startTimeLH = System.nanoTime();
            try{
                JavaCollection lhM = lhMap.get(searchs[i]);
                long endTimeLH = System.nanoTime();
                long LHTime = endTimeLH-startTimeLH;
                lhTimeMap.put(searchs[i], LHTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",LHTime) + " nanoseconds");
            } catch (Exception e) {
                long endTimeLH = System.nanoTime();
                long LHTime = endTimeLH-startTimeLH;
                lhTimeMap.put(searchs[i], LHTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",LHTime) + " nanoseconds");
            }
        }
        System.out.println("TreeMap");
        for (int i=0; i< searchs.length; i++)
        {
            long startTimeT = System.nanoTime();
            try{
                JavaCollection tm = tMap.get(searchs[i]);
                long endTimeT = System.nanoTime();
                long TTime = endTimeT-startTimeT;
                tTimeMap.put(searchs[i], TTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",TTime) + " nanoseconds");
            } catch (Exception e) {
                long endTimeT = System.nanoTime();
                long TTime = endTimeT-startTimeT;
                tTimeMap.put(searchs[i], TTime);
                System.out.println("Search Key : " + String.format("%6d",searchs[i]) + ", Time : " + String.format("%7d",TTime) + " nanoseconds");
            }
        }
        //Write Output Time to CSV File for Compare        
        try (
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_FILE));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("ID", "HashMap Time (ns)", "LinkedHashMap Time (ns)", "TreeMap Time (ns)")
                .withDelimiter(DELIMITER));
        ) 
        {
            for (int i=0; i< searchs.length; i++){
                csvPrinter.printRecord(searchs[i], hTimeMap.get(searchs[i]), lhTimeMap.get(searchs[i]), tTimeMap.get(searchs[i]));
            }
            csvPrinter.flush();            
        }
    }   
}
