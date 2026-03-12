package com.pgplatform.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private String channel; // WHATSAPP, EMAIL, SMS
    private String recipient; // phone or email
    private String template; // RENT_REMINDER, PAYMENT_DUE, INVOICE_GENERATED
    private Map<String, String> data;
}
