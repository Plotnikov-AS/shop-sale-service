package ru.mai.shop.sale.integration.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.mai.shop.sale.generated.dto.ProductDto;
import ru.mai.shop.sale.integration.kafka.config.KafkaConfig;
import ru.mai.shop.sale.integration.kafka.config.properties.enums.Topic;
import ru.mai.shop.sale.integration.kafka.dto.NewSaleEvent;
import ru.mai.shop.sale.integration.kafka.dto.RefundEvent;
import ru.mai.shop.sale.integration.kafka.service.KafkaProducer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper jsonMapper;
    private final KafkaConfig kafkaConfig;

    @Override
    public void sendNewSaleEvent(List<ProductDto> products) {
        NewSaleEvent event = new NewSaleEvent()
                .setProducts(products);
        send(event, kafkaConfig.getTopicValue(Topic.NEW_SALE));
    }

    @Override
    public void sendRefundEvent(List<ProductDto> products) {
        RefundEvent event = new RefundEvent()
                .setProducts(products);
        send(event, kafkaConfig.getTopicValue(Topic.REFUND));
    }

    private void send(Object event, String topic) {
        try {
            UUID messageId = UUID.randomUUID();
            Message<String> message = MessageBuilder.createMessage(
                    jsonMapper.writeValueAsString(event), createMessageHeaders(topic, messageId));
            kafkaTemplate.send(message)
                    .whenComplete((result, ex) -> logResult(ex, event, messageId));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось преобразовать событие в json", e);
        }
    }

    private MessageHeaders createMessageHeaders(String topic, UUID messageId) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("message_id", messageId);
        headers.put("message_version", "1.0");
        headers.put("message_mime_type", "application/json; encoding=\"UTF-8\"");
        headers.put("kafka_topic", topic);
        headers.put("kafka_nativeHeaders", "");

        return new MessageHeaders(headers);
    }

    private void logResult(Throwable ex, Object message, UUID messageId) {
        if (Objects.isNull(ex)) {
            log.info("Событие с ID {} успешно отправлено", messageId);
        } else {
            log.error("Не удалось отправить событие с ID {}", messageId, ex);
        }

        if (log.isTraceEnabled()) {
            log.trace("Message: {}", message);
        }
    }
}
