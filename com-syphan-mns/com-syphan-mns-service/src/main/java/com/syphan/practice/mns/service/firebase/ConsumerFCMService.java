package com.syphan.practice.mns.service.firebase;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.syphan.practice.mns.api.firebase.PushNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ConsumerFCMService {

    @Autowired
    private FCMInitializer fcmInitializer;

//    private FirebaseApp firebaseApp;
//
//    @PostConstruct
//    public void init() {
//        try {
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("fcm/consumer-service-account.json")
//                            .getInputStream())).build();
//            if (FirebaseApp.getApps().isEmpty()
//                    || FirebaseApp.getApps().stream().noneMatch(fb -> fb.getName().equals(ConsumerFCMService.class.getName()))) {
//                firebaseApp = FirebaseApp.initializeApp(options, ConsumerFCMService.class.getName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * send notification with multiple tokens using FCM
     *
     * @param data
     * @param request
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void sendMessageToMultipleTokens(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        MulticastMessage message = getPreconfiguredMessageWithDataToMultipleTokens(data, request);
        sendAndGetResponse(message);
    }

    private MulticastMessage getPreconfiguredMessageWithDataToMultipleTokens(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMulticastMessageBuilder(request).putAllData(data).addAllTokens(request.getTokens()).build();
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return MulticastMessage.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(new Notification(request.getTitle(), request.getMessage()));
    }

    private BatchResponse sendAndGetResponse(MulticastMessage message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance(fcmInitializer.firebaseApp).sendMulticastAsync(message).get();
    }

    /**
     * send notification with one token using FCM
     *
     * @param request
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        sendAndGetResponse(message);
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken()).build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(new Notification(request.getTitle(), request.getMessage()));
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance(fcmInitializer.firebaseApp).sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    /**
     * not using data
     * @param request
     * @throws InterruptedException
     * @throws ExecutionException
     */

    public void sendMessageToMultipleTokens(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        MulticastMessage message = getPreconfiguredMulticastMessageToToken(request);
        sendAndGetResponse(message);
    }

    private MulticastMessage getPreconfiguredMulticastMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMulticastMessageBuilder(request).addAllTokens(request.getTokens()).build();
    }
}
