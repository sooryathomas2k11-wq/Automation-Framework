package org.example.csv;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVUtils {

    public static Iterator<Object[]> readCSV(String filePath){
        List<Object[]>data=new ArrayList<>();
        try(Reader reader=new FileReader(filePath);
            CSVParser csvParser=new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){
            for (CSVRecord record : csvParser) {
                List<String> recordData = new ArrayList<>();
                record.forEach(recordData::add);
                data.add(recordData.toArray(new Object[0]));
            }

        } catch (Exception e) {
          e.printStackTrace();
        }
      return data.iterator();
    }
}
