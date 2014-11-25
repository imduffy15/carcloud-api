package ie.ianduffy.carcloud.service;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Service for sending SMSs.
 * <p/>
 * <p>
 * We use the @Async annotation to send SMSs asynchronously.
 * </p>
 */
@Service
public class SMSService {

    private final Logger log = LoggerFactory.getLogger(SMSService.class);

    @Inject
    private NexmoSmsClient nexmoSmsClient;

    @Inject
    private Environment env;

    private String from;

    @PostConstruct
    public void init() {
        this.from = env.getProperty("nexmo.from");
    }

    @Async
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
