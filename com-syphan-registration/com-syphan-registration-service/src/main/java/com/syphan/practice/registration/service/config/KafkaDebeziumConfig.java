package com.syphan.practice.registration.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaDebeziumConfig {

    @Bean
    public io.debezium.config.Configuration config() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/path/cdc/offset/student-offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "dbz-mysql-connector")
                .with("tasks.max", "1")
                .with("database.server.name", "registration_service")
                .with("database.hostname", "mysql")
                .with("database.port", "3306")
                .with("database.user", "root")
                .with("database.password", "root123456")
                .with("database.dbname", "registration_service")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.kafka.bootstrap.servers", "localhost:9092")
                .with("database.history.kafka.topic", "registration_service.public")
                .with("table.whitelist", "public").build();
    }
}
