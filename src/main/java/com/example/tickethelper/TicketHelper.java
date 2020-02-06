package com.example.tickethelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Component
public class TicketHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketHelper.class);

    private JavaMailSenderImpl sender;

//    @Value("${ticket.fromStation}")
//    private String startStation;
//
//    @Value("${ticket.toStation}")
//    private String toStation;
//
//    @Value("${ticket.date}")
//    private String[] dates;
//
//    @Value("${email.from}")
//    private String fromEmail;
//
//    @Value("${email.to}")
//    private String toEmail;

    public TicketHelper(@Autowired JavaMailSenderImpl sender) {
        this.sender = sender;
    }

    @Scheduled(fixedDelay = 10000)
    public void grabTicket() {
        LOGGER.info("29-Grabbing.......");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2019-08-29&leftTicketDTO.from_station=JAC&leftTicketDTO.to_station=NJH&purpose_codes=ADULT"
                , String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String[] array = responseEntity.getBody().split("\\|");
            if (array.length == 1) {
                LOGGER.warn("29 Response Content is not wanted.");
                return;
            }
            if (!array[28].equals("") && !array[28].equals("无")) {
                String subject = "29号有票，硬卧 " + array[28] + "张.";
                String text = responseEntity.getBody();
                LOGGER.info(subject);
                LOGGER.info(text);
                sendEmail(subject, text);
            }
        }
        sleepSomeTime();
    }

    @Scheduled(fixedDelay = 20000)
    public void grabTicket2() {
        LOGGER.info("30-Grabbing.......");
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2019-08-30&leftTicketDTO.from_station=JAC&leftTicketDTO.to_station=NJH&purpose_codes=ADULT"
                , String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String[] array = responseEntity.getBody().split("\\|");
            if (array.length == 1) {
                LOGGER.warn("30 Response Content is not wanted.");
                return;
            }
            if (!array[28].equals("") && !array[28].equals("无")) {
                String subject = "30号有票，硬卧 " + array[28] + "张.";
                String text = responseEntity.getBody();
                LOGGER.info(subject);
                LOGGER.info(text);
                sendEmail(subject, text);
            }
        }
        sleepSomeTime();
    }

    private void sendEmail(String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(new String[]{"954618625@qq.com"});
        simpleMailMessage.setFrom("zhenyuxienju@163.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        // 发送邮件
        sender.send(simpleMailMessage);
    }

    private void sleepSomeTime() {
        Random random = new Random();
        int randomTime = random.nextInt(10) * 10000;
        try {
            Thread.sleep(randomTime);
        } catch (InterruptedException e) {
            LOGGER.error("Should never happen");
        }
    }
}
