package com.readings.application;

import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;

import java.util.List;
import java.util.Map;

public class ReadingsUseCase {

    private final ReadingDetectorService detectorService;


    public ReadingsUseCase(ReadingDetectorService detectorService) {
        this.detectorService = detectorService;
    }

    public void processReadings(List<Reading> readingList){

        Map<String,Double> medians = detectorService.getMedianByClient(readingList);


        System.out.println("| Client               | Period             | Suspicious         | Median");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Reading reading : readingList){

            double median = medians.get(reading.getClientId());

            if(detectorService.isSuspicious(reading.getValue(),median)){
                String line = String.format("| %-20s | %-18s | %-18.2f | %.2f",
                        reading.getClientId(),reading.getPeriod(),reading.getValue(),median);

                System.out.println(line);
            }
        }
    }
}
