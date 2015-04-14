package ie.ianduffy.carcloud;

import ie.ianduffy.carcloud.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class,
    MetricRepositoryAutoConfiguration.class
})
public class Application {

    @Inject
    private Environment env;

    private static void addDefaultProfile(SpringApplication app,
                                          SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")) {
            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        addDefaultProfile(app, source);

        app.run(args);
    }

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}",
                Arrays.toString(env.getActiveProfiles()));
        }
    }
}
