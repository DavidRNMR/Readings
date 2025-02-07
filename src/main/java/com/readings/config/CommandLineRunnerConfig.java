package com.readings.config;

import com.readings.adapters.csv.CsvReadingAdapter;
import com.readings.adapters.xml.XmlReadingAdapter;
import com.readings.application.AlertService;
import com.readings.application.ReadingsUseCase;
import com.readings.domain.Reading;
import com.readings.domain.ReadingDetectorService;
import com.readings.domain.SuspiciousReadingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommandLineRunnerConfig implements CommandLineRunner {

    @Autowired
    private CsvReadingAdapter csvReadingAdapter;

    @Autowired
    private XmlReadingAdapter xmlReadingAdapter;

    @Autowired
    private AlertService alertService;

    @Value("${alert.mail.to}")
    private String to;

    @Value("${alert.mail.subject}")
    private String subject;


    @Override
    public void run(String... args){

        if (args.length <1){

            System.err.println("Uso: java -jar readings.jar <archivo.csv|archivo.xml>");
        }

        String filePathStr = args[0];

        try {

            List<Reading> allReadings= null;

            if (filePathStr.toLowerCase().endsWith(".csv")) {

                allReadings = csvReadingAdapter.readFromCsv(filePathStr);
            }
            else if (filePathStr.toLowerCase().endsWith(".xml")) {

                allReadings = xmlReadingAdapter.readFromXml(filePathStr);
            }
            else {

                System.err.println("Formato no soportado: " + filePathStr);
            }

            ReadingDetectorService detectorService = new ReadingDetectorService();
            ReadingsUseCase useCase = new ReadingsUseCase(detectorService);

            List<SuspiciousReadingInfo> suspicious = useCase.getSuspiciousReading(allReadings);

            String report = useCase.buildSuspiciousReport(suspicious);

            System.out.println(report);

            if (!suspicious.isEmpty()) {

                String bodyInfo = "Se han detectado las siguientes lecturas sospechosas:\n";

                alertService.sendAlert(
                        report,
                        subject,
                        to,
                        bodyInfo
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
