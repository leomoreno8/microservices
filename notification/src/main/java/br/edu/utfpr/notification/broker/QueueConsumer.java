package br.edu.utfpr.notification.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor @Service
public class QueueConsumer {
    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    public void recieve(@Payload String fileBody) {
        System.out.println(fileBody);
    }

}
