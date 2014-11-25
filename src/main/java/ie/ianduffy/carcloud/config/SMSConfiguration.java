package ie.ianduffy.carcloud.config;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SMSConfiguration implements EnvironmentAware {


    private final Logger log = LoggerFactory.getLogger(SMSConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private static final String ENV_NEXOMO = "nexmo.";

    private static final String PROP_USERNAME = "username";
    private static final String PROP_PASSWORD = "password";

    @Bean
    public NexmoSmsClient nexmoSmsClient() {
        log.debug("Configuring SMS Client");
        String username = propertyResolver.getProperty(PROP_USERNAME);
        String password = propertyResolver.getProperty(PROP_PASSWORD);
        NexmoSmsClient nexmoSmsClient = null;
        try {
            nexmoSmsClient = new NexmoSmsClient(username, password);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return nexmoSmsClient;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_NEXOMO);
    }
}
