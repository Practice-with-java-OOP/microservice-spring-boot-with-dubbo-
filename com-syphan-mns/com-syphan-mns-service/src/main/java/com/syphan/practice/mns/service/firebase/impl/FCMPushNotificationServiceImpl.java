package com.syphan.practice.mns.service.firebase.impl;

import com.syphan.practice.mns.api.firebase.PushNotificationRequest;
import com.syphan.practice.mns.service.firebase.ConsumerFCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMPushNotificationServiceImpl {

    @Autowired
    private ConsumerFCMService fcmService;

    public void pushConsumerNotificationToMultipleTokens(Map<String, String> data, PushNotificationRequest request) {
        try {
            fcmService.sendMessageToMultipleTokens(data, request);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void pushConsumerNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void pushConsumerNotificationToMultipleTokens(PushNotificationRequest request) {
        fcmService.sendMessageToMultipleTokens(request);
    }
}
