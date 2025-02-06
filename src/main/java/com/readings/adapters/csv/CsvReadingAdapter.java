package com.readings.adapters.csv;

import com.readings.domain.Reading;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReadingAdapter {

    public List<Reading>  readFromCsv (String filePath) throws Exception {

        List<Reading> readings = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){

            String header = bufferedReader.readLine();
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String [] fields = line.split(",");

                if(fields.length==3){
                    String clientId = fields[0].trim();
                    String period = fields[1].trim();
                    double value = Double.parseDouble(fields[2].trim());

                    readings.add(new Reading(clientId,period,value));
                }
            }
        }
        return readings;
    }

}
