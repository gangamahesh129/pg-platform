package com.pgplatform.notification.service;

import com.pgplatform.notification.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class NotificationService {

    // Placeholder - integrate with Twilio/WhatsApp Business API, SendGrid, etc.
    public void send(NotificationRequest request) {
        log.info("Notification sent: channel={}, recipient={}, template={}",
                request.getChannel(), request.getRecipient(), request.getTemplate());
        // TODO: Integrate with actual providers:
        // - WhatsApp: Twilio WhatsApp API, WhatsApp Business API
        // - Email: SendGrid, AWS SES
        // - SMS: Twilio
    }

    public void sendRentReminder(String phoneNumber, String guestName, Double amount, String dueDate) {
        send(NotificationRequest.builder()
                .channel("WHATSAPP")
                .recipient(phoneNumber)
                .template("RENT_REMINDER")
                .data(Map.of(
                        "guestName", guestName,
                        "amount", String.valueOf(amount),
                        "dueDate", dueDate
                ))
                .build());
    }

    public void sendInvoiceGenerated(String email, String invoiceId, Double amount) {
        send(NotificationRequest.builder()
                .channel("EMAIL")
                .recipient(email)
                .template("INVOICE_GENERATED")
                .data(Map.of(
                        "invoiceId", invoiceId,
                        "amount", String.valueOf(amount)
                ))
                .build());
    }
}
