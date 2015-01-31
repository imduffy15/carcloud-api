package ie.ianduffy.carcloud.service;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
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

    public void sendSMS(String number, String message) {
        TextMessage textMessage = new TextMessage(from, number, message);
        SmsSubmissionResult[] results = null;
        try {
            results = nexmoSmsClient.submitMessage(textMessage);
            log.debug(results.toString());
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
        log.debug(results.toString());
    }
}
