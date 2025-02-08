package com.readings.integration;

import com.readings.adapters.csv.CsvReadingAdapter;
import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class ReadingsIntegrationTest {

    @Test
    void testCsvIntegration () throws Exception {

        URL resource = getClass().getResource("/2016-readings.csv");

        Assertions.assertNotNull(resource,"No se encontro test-readings.csv");

        String filePath = Paths.get(resource.toURI()).toString();
        CsvReadingAdapter csvReadingAdapter = new CsvReadingAdapter();

        List<Reading> readings = csvReadingAdapter.readFromCsv(filePath);

        Assertions.assertEquals(120,readings.size(),"Debe haber 12 lecturas");

        ReadingDetectorService readingDetectorService = new ReadingDetectorService();
        var medians = readingDetectorService.getMedianByClient(readings);
        double median = medians.get("583ef6329d7b9");

        Assertions.assertEquals(42996.5,median,0.1,"La mediana es 42996.5");
        Assertions.assertTrue(readingDetectorService.isSuspicious(3564,median),"3564 es sospechosa");
        Assertions.assertFalse(readingDetectorService.isSuspicious(43597,median),"43597 no es sospechosa");

    }
}
