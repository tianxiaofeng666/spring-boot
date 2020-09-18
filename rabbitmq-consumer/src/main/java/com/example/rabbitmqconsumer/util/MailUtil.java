package com.example.rabbitmqconsumer.util;

import com.example.rabbitmqconsumer.pojo.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Autowired
    private JavaMailSender mailSender;

    private final static Logger log = LoggerFactory.getLogger(MailUtil.class);

    /**
     * 发送简单邮件
     *
     * @param mail
     */
    public boolean send(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1248083709@qq.com");
        message.setTo(mail.getEamil());
        message.setSubject(mail.getTopic());
        message.setText(mail.getText());
        try{
            mailSender.send(message);
            log.info("邮件发送成功");
            return true;
        }catch(Exception e){
            log.error("邮件发送失败, to: {}, title: {}", mail.getEamil(), mail.getTopic(), e);
            return false;
        }
    }
}
