package com.readings.adapters.alerts;
import com.readings.application.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailAlertService implements AlertService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${alert.mail.from}")
    private String from;

    @Value("${alert.mail.to}")
    private String to;

    @Value("${alert.mail.subject}")
    private String defaultSubject;


    @Override
    public void sendAlert(String report,String subject,String toParam,String bodyInfo) {

       if (report == null || report.trim().isEmpty()){
           return;
       }

       String finalSubject = (subject!=null && !subject.isEmpty()) ? subject: defaultSubject;

       String finalTo = (toParam!=null && !toParam.isEmpty()) ? toParam : this.to;

       StringBuilder sb = new StringBuilder();
       sb.append("\n").append(report);

       SimpleMailMessage message = new SimpleMailMessage();
       message.setFrom(from);
       message.setTo(finalTo);
       message.setSubject(finalSubject);
       message.setText(sb.toString());

       mailSender.send(message);
   }
}