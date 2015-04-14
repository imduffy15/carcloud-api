package ie.ianduffy.carcloud.service;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Slf4j
@Async
@Service
public class SMSService {

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
