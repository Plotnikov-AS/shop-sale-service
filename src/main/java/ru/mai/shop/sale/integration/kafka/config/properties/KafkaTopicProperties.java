package ru.mai.shop.sale.integration.kafka.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.mai.shop.sale.integration.kafka.config.properties.enums.Topic;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaTopicProperties {
    private Map<Topic, TopicProperty> topics;

    @Data
    public static class TopicProperty {
        private String name;
    }
}
