package com.rbalazs.notifications.service;

import com.rbalazs.notifications.enums.NotificationsAppConstants;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service which sends Email Notifications via Spring {@link JavaMailSender}.
 *
 * @author Rodrigo Balazs
 */
@Service
public class EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    @Value("${spring.mail.username}")
    private String springMailUsername;

    @Value("${spring.mail.password}")
    private String springMailPassword;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailNotificationService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends a New Order notification to the customer email given as parameter.
     *
     * @param customerEmail the customer email to send the notification message.
     * @param orderId the new order identifier
     */
    public void sendNewOrderNotification(final String customerEmail, final Long orderId) {

        if(StringUtils.isEmpty(springMailUsername) || StringUtils.isEmpty(springMailPassword)){
            LOGGER.info("the new Order Notification Email was not sent because the SMTP server is not configured correctly "
                    + "('spring.mail.username' and 'spring.mail.password' properties needs to be assigned)");
            return;
        }

        Validate.notEmpty(customerEmail, "the customer email cannot be null nor empty");
        Validate.isTrue(orderId >= 0, "The order identifier must be greater or equal to zero");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customerEmail);
        message.setSubject(NotificationsAppConstants.NEW_ORDER_EMAIL_SUBJECT);
        message.setText(NotificationsAppConstants.NEW_ORDER_NOTIFICATION + orderId);
        javaMailSender.send(message);
    }
}
