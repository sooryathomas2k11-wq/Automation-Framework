package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVUtils {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);

    public static Iterator<Object[]> readCSV(String filePath) {
        List<Object[]> data = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord record : csvParser) {
                List<String> recordData = new ArrayList<>();
                record.forEach(recordData::add);
                data.add(recordData.toArray(new Object[0]));
            }

        } catch (Exception e) {
            logger.error("Failed to read CSV file: {}", filePath, e);
            throw new RuntimeException(e); // fail fast
        }
        return data.iterator();
    }
}