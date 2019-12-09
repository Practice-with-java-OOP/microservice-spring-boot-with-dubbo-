package com.syphan.practice.mns.service.util;

import com.syphan.practice.mns.api.dto.Mail;
import com.syphan.practice.mns.api.exception.SendMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StopWatch;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailUtils {

    private Logger logger = LoggerFactory.getLogger(SendMailUtils.class);

    private JavaMailSenderImpl mailSender;

    private void init() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(""); //email-smtp.us-west-2.amazonaws.com
        mailSender.setPort(587);
        mailSender.setUsername("phantiensy195@gmail.com");
        mailSender.setPassword("mypassword");
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", false);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.from", "support@cosalon.vn");
        mailSender.setJavaMailProperties(properties);
    }

    public void senMailMessage(Mail mail) {
        init();
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("support@cosalon.vn");

            if (mail.getTos() != null && !mail.getTos().isEmpty()) {
                helper.setTo(mail.getTos().toArray(new String[0]));
            }
            if (mail.getCcs() != null && !mail.getCcs().isEmpty()) {
                helper.setCc(mail.getCcs().toArray(new String[0]));
            }
            if (mail.getBccs() != null && !mail.getBccs().isEmpty()) {
                helper.setBcc(mail.getBccs().toArray(new String[0]));
            }

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), mail.getIsHtml());
            mailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            logger.debug(String.format("%s%s", "MessagingException occurred when while sending mail: ", ex.getMessage()));
            throw new SendMailException(ex);
        } catch (MailException ex) {
            logger.debug(String.format("%s%s", "MailException occurred when while sending mail: ", ex.getMessage()));
            throw new SendMailException(ex);
        } finally {
            stopWatch.stop();
            logger.debug(String.format("%s%s%s", "This email sent a total of ", stopWatch.getTotalTimeSeconds(), "s."));
        }
    }
}
