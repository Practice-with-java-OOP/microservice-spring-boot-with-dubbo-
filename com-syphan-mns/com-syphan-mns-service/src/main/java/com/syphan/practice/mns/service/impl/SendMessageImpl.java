package com.syphan.practice.mns.service.impl;

import com.syphan.practice.mns.api.dto.Mail;
import com.syphan.practice.mns.api.dto.SendMessageDto;
import com.syphan.practice.mns.api.exception.SendMailException;
import com.syphan.practice.mns.api.exception.SendSMSException;
import com.syphan.practice.mns.api.firebase.PushNotificationRequest;
import com.syphan.practice.mns.service.firebase.impl.FCMPushNotificationServiceImpl;
import com.syphan.practice.mns.service.util.SendMailUtils;
import com.syphan.practice.mns.service.util.SendSMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SendMessageImpl {
    private Logger logger = LoggerFactory.getLogger(SendMessageImpl.class);

    @Autowired
    private FCMPushNotificationServiceImpl fcmPushNotificationService;

    private void sendMessage(SendMessageDto data) {
        switch (data.getSendPipeline()) {
            case ONLINE_CHAT: {
                /**
                 * send message to queue for frontend xu ly
                 *
                 jmsMessagingTemplate.convertAndSend("${data.recipientUserType.name}.${recipient.destination}",
                 ObjectMapper().writeValueAsString(MessageRecordDto(
                 message = messageRecord,
                 senderUserType = data.senderUserType,
                 senderInfo = senderInfo)))*/
                break;
            }
            case MAIL: {
                try {
                    Mail mail = new Mail();
                    mail.setTos(Collections.singletonList(data.getDestination()));
                    mail.setContent(data.getContent());
                    mail.setSubject(data.getDestination());
                    mail.setIsHtml(data.getIsHtml());
                    new SendMailUtils().senMailMessage(mail);
                } catch (SendMailException ex) {
                    logger.warn(String.format("%s%s", "The mail failed to be sent. The reason for the failure is: ", ex.getMessage()));
                }

                break;
            }
            case SMS: {
                try {
                    String username = "AKIA3O...."; //username dang nhap vao VMG
                    String password = "BG9TVGX/Ltn86KIkQ....."; //password dang nhap vao VMG
                    String alias = "CO SALON";
                    String endpointURL = "http://brandsms.vn:8018/VMGAPI.asmx?wsdl"; //VMG IP Address
                    new SendSMSUtils().sendSms(username, password, alias, endpointURL, data.getDestination(), data.getContent());
                } catch (NullPointerException ex) {
                    logger.warn("Missing configuration required for SMS interface provider.", ex);
                } catch (SendSMSException ex) {
                    logger.warn(String.format("%s%s", "The SMS failed to be sent. The reason for the failure is: ", ex.getMessage()));
                }
                break;
            }
            case PUSH_NOTIFICATION: {
                Map<String, String> map = new HashMap<>();
                map.put("title", "Test push notification");
                map.put("message", "Đây là notification để test");
                PushNotificationRequest request = new PushNotificationRequest();
                request.setTokens(new ArrayList<>());
                request.setTitle("Test push notification");
                request.setMessage("Đây là notification để test");
                fcmPushNotificationService.pushConsumerNotificationToMultipleTokens(map, request);
            }
            default:
                throw new IllegalStateException("Unexpected value: " + data.getSendPipeline());
        }
    }
}
