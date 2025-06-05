package com.ducthang.mail_service.consumer;


import com.ducthang.mail_service.DTO.EmailMessage;
import com.ducthang.mail_service.config.RabbitMQConfig;
import com.ducthang.mail_service.service.EmailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailSenderService emailSenderService;

    public EmailConsumer(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveEmail(EmailMessage message) {
        emailSenderService.send(message.getTo(), message.getSubject(), message.getBody());
    }
}

