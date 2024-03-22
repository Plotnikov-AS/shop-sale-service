package ru.mai.shop.sale.integration.kafka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import ru.mai.shop.sale.integration.kafka.config.properties.KafkaTopicProperties;
import ru.mai.shop.sale.integration.kafka.config.properties.enums.Topic;

@EnableKafka
@Configuration
@EnableConfigurationProperties(KafkaTopicProperties.class)
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaTopicProperties topicProperties;

    public String getTopicValue(Topic topic) {
        return topicProperties.getTopics().get(topic).getName();
    }
}
