package com.ducthang.mail_service.consumer;


import com.ducthang.mail_service.DTO.EmailMessage;
import com.ducthang.mail_service.config.RabbitMQConfig;
import com.ducthang.mail_service.service.EmailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailSenderService emailSender;

    public EmailConsumer(EmailSenderService emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveEmail(EmailMessage message) {
        emailSender.send(message.getTo(), message.getSubject(), message.getBody());
    }
}
