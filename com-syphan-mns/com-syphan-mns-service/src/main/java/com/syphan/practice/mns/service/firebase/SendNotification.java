package com.syphan.practice.mns.service.firebase;

import com.syphan.practice.mns.api.firebase.PushNotificationRequest;
import com.syphan.practice.mns.service.firebase.impl.FCMPushNotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendNotification {

    @Autowired
    private FCMPushNotificationServiceImpl fcmPushNotificationService;

    public void send() {
        Map<String, String> map = new HashMap<>();
        map.put("title", "Test push notification");
        map.put("message", "Đây là notification để test");
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTokens(new ArrayList<>());
        request.setTitle("Test push notification");
        request.setMessage("Đây là notification để test");
        fcmPushNotificationService.pushConsumerNotificationToMultipleTokens(map, request);
    }
}
