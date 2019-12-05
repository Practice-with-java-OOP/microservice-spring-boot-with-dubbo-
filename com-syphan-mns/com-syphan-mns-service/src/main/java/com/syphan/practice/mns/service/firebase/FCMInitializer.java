package com.syphan.practice.mns.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitializer {

    FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("fcm/consumer-service-account.json")
                            .getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()
                    || FirebaseApp.getApps().stream().noneMatch(fb -> fb.getName().equals(FCMInitializer.class.getName()))) {
                firebaseApp = FirebaseApp.initializeApp(options, FCMInitializer.class.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
