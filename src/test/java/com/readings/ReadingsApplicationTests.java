package com.readings;

import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class ReadingsApplicationTests {

	@Test
	void testComputeMedian() {

		ReadingDetectorService service = new ReadingDetectorService();
		List<Double> values = Arrays.asList(10.0,100.0,50.0,40.0,60.0);

		double median = service.getMedian(values);
		Assertions.assertEquals(50.0,median,0.01);

		Assertions.assertTrue(service.isSuspicious(10,median));
		Assertions.assertTrue(service.isSuspicious(100,median));
		Assertions.assertFalse(service.isSuspicious(40,median));
		Assertions.assertFalse(service.isSuspicious(50,median));
		Assertions.assertFalse(service.isSuspicious(60,median));
	}

	@Test
	void testComputeMediansByClient (){

		ReadingDetectorService service = new ReadingDetectorService();
		List <Reading> readings = Arrays.asList(
				new Reading("David","2016-01",10),
				new Reading("David","2016-02",12),
				new Reading("David","2016-03",14),
				new Reading("Alberto","2016-01",100),
				new Reading("Alberto","2016-02",110),
				new Reading("Alberto","2016-03",120)
		);
		var medians = service.getMedianByClient(readings);

		Assertions.assertEquals(12.0,medians.get("David"),0.01);
		Assertions.assertEquals(110.0,medians.get("Alberto"),0.01);
	}

}
