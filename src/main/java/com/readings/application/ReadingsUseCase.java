package com.readings.application;

import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;
import com.readings.domain.SuspiciousReadingInfo;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReadingsUseCase {

    private final ReadingDetectorService detectorService;


    public ReadingsUseCase(ReadingDetectorService detectorService) {
        this.detectorService = detectorService;
    }

    public  List<SuspiciousReadingInfo> getSuspiciousReading(List<Reading> allReadings){

        Map<String, Double> medians = detectorService.getMedianByClient(allReadings);
        List<SuspiciousReadingInfo> suspiciousList = new ArrayList<>();

        for (Reading reading : allReadings) {

            double median = medians.get(reading.getClientId());

            if (detectorService.isSuspicious(reading.getValue(), median)) {
                suspiciousList.add(new SuspiciousReadingInfo(
                        reading.getClientId(),
                        reading.getPeriod(),
                        reading.getValue(),
                        median
                ));
            }
        }
        return suspiciousList;
    }

    public String buildSuspiciousReport(List<SuspiciousReadingInfo>suspiciousReadings){

        StringBuilder sb = new StringBuilder();
        sb.append("| Client               | Month              | Suspicious         | Median\n");
        sb.append("-------------------------------------------------------------------------------\n");

        for (SuspiciousReadingInfo suspiciousReadingInfo: suspiciousReadings){

            YearMonth yearMonth = YearMonth.parse(suspiciousReadingInfo.getPeriod());
            String monthName = yearMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            sb.append(String.format("| %-20s | %-18s | %-18.2f | %.2f\n",
                    suspiciousReadingInfo.getClientId(),
                    monthName,
                    suspiciousReadingInfo.getValue(),
                    suspiciousReadingInfo.getMedian()));
        }
        return sb.toString();
    }
}
