package com.readings.config;

import com.readings.adapters.csv.CsvReadingAdapter;
import com.readings.adapters.xml.XmlReadingAdapter;
import com.readings.application.ReadingsUseCase;
import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommandLineRunnerConfig implements CommandLineRunner {

    @Autowired
    private CsvReadingAdapter csvReadingAdapter;

    @Autowired
    private XmlReadingAdapter xmlReadingAdapter;

    @Override
    public void run(String... args) throws Exception {

        if (args.length <1){
            System.err.println("Uso: java -jar readings.jar <archivo.csv|archivo.xml>");
        }

        String filePath = args[0];

        try {
            List<Reading> allReadings = null;

            if(filePath.toLowerCase().endsWith(".csv")){
                allReadings = csvReadingAdapter.readFromCsv(filePath);
            }
            else if (filePath.toLowerCase().endsWith(".xml")){
                allReadings = xmlReadingAdapter.readFromXml(filePath);
            }
            else{
                System.err.println("Debe ser un archivo csv o xml: " + filePath);
            }

            ReadingDetectorService detectorService = new ReadingDetectorService();
            ReadingsUseCase readingsUseCase = new ReadingsUseCase(detectorService);

            readingsUseCase.processReadings(allReadings);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
