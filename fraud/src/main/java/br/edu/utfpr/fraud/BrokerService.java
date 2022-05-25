package br.edu.utfpr.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Properties;

@RequiredArgsConstructor @Service
public class BrokerService {

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    public void send(FraudCheckHistory fraudCheckHistory) {
        ifNotExistsCreateNewQueueToInstance("one");
        rabbitTemplate.convertAndSend(exchange, "one",  fraudCheckHistory.toString());
    }

    private void ifNotExistsCreateNewQueueToInstance(String queueName) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        Properties queue = rabbitAdmin.getQueueProperties(String.format(this.queueName, queueName));
        if (Objects.isNull(queue)) {
            rabbitAdmin.declareQueue(new Queue(String.format(this.queueName, queueName)));
            rabbitAdmin.declareBinding(new Binding(String.format(this.queueName, queueName),
                    Binding.DestinationType.QUEUE, exchange, queueName, null));
        }
    }
}
