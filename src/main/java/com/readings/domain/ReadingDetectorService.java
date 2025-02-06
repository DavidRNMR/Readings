package com.readings.domain;

import java.util.*;
import java.util.stream.Collectors;

public class ReadingDetectorService {

    public double getMedian (List<Double> values){

        List<Double> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int size = sorted.size();

        if (size==0) return 0;

        if(size % 2 ==0){

            double left = sorted.get(size/2) -1;
            double right = sorted.get(size/2);

            return (left + right) / 2.0;
        }
        else {
            return sorted.get(size/2);
        }
    }

    public Map<String,Double> getMedianByClient (List<Reading> readings){

        Map<String,List<Reading>> byClient = readings.stream()
                .collect(Collectors.groupingBy(Reading::getClientId));

        Map<String,Double> medians = new HashMap<>();

        for(Map.Entry<String,List<Reading>> entry : byClient.entrySet()){

            String clientId = entry.getKey();
            List<Reading> clientReadings = entry.getValue();

            List<Double> usage = clientReadings.stream()
                    .map(Reading::getValue)
                    .collect(Collectors.toList());

            double median = getMedian(usage);
            medians.put(clientId,median);

        }
        return medians;
    }

    public Boolean isSuspicious (double readingValue, double median){

        if (median == 0){
            return false;
        }
        return (readingValue < 0.5 * median) || (readingValue > 1.5 * median);
    }
}
