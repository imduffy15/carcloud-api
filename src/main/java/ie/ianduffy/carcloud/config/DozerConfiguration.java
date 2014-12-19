package ie.ianduffy.carcloud.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DozerConfiguration {

    @Bean
    Mapper mapper() {
        return new DozerBeanMapper(Arrays.asList("dozerMapping.xml"));
    }
}
