package ie.ianduffy.carcloud.service;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.messages.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Service for sending SMSs.
 */
@Service
public class SMSService {

    private final Logger log = LoggerFactory.getLogger(SMSService.class);

    @Inject
    private Environment env;

    private String from;

    @Inject
    private NexmoSmsClient nexmoSmsClient;

    @PostConstruct
    public void init() {
        this.from = env.getProperty("nexmo.from");
    }

    @Async
    public void sendSMS(String to, String message) {
        TextMessage textMessage = new TextMessage(from, to, message);
        try {
            nexmoSmsClient.submitMessage(textMessage);
        } catch (Exception e) {
            log.warn("SMS could not be sent to user '{}', exception is: {}",
                     to, e.getMessage());
        }
    }
}
