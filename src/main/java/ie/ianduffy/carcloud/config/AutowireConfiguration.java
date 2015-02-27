package ie.ianduffy.carcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutowireConfiguration {

    @Bean
    public AutowireHelper autowireHelper() {
        return AutowireHelper.getInstance();
    }

}
