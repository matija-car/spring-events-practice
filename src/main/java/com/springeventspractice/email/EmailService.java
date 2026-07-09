package com.springeventspractice.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void sendWelcomeEmail(String toEmail, String username) {
        // In a real system, this would call an SMTP client, SendGrid API, etc.
        log.info("Building welcome email for {} ({})", username, toEmail);
        log.info("Email sent to {}: Subject='Welcome, {}!' Body='Thanks for joining us.'", toEmail, username);

    }
}
