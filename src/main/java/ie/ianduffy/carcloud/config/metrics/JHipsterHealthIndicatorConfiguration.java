package ie.ianduffy.carcloud.config.metrics;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
public class JHipsterHealthIndicatorConfiguration {

    @Inject
    private DataSource dataSource;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Bean
    public HealthIndicator dbHealthIndicator() {
        return new DatabaseHealthIndicator(dataSource);
    }

    @Bean
    public HealthIndicator mailHealthIndicator() {
        return new JavaMailHealthIndicator(javaMailSender);
    }
}
