package ie.ianduffy.carcloud;

import ie.ianduffy.carcloud.repository.AuthorityRepository;
import ie.ianduffy.carcloud.repository.DeviceRepository;
import ie.ianduffy.carcloud.repository.TrackRepository;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.service.TrackService;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.error.RestResponseEntityExceptionHandler;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.sql.Ref;

import javax.inject.Inject;

public abstract class AbstractResourceTest {

    @Inject
    protected AuthorityRepository authorityRepository;

    @Inject
    protected PasswordEncoder passwordEncoder;

    @Inject
    protected UserRepository userRepository;

    @Inject
    protected UserService userService;

    @Inject
    protected DeviceService deviceService;

    @Inject
    protected DeviceRepository deviceRepository;

    @Inject
    protected TrackService trackService;

    @Inject
    protected TrackRepository trackRepository;

    protected MockMvc mockMvc;

    public void setup(Object controller) {
        ReflectionTestUtils.setField(userService, "authorityRepository", authorityRepository);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        ReflectionTestUtils.setField(deviceService, "deviceRepository", deviceRepository);
        ReflectionTestUtils.setField(deviceService, "userService", userService);

        ReflectionTestUtils.setField(trackService, "deviceService", deviceService);
        ReflectionTestUtils.setField(trackService, "trackRepository", trackRepository);

        final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();

        final StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerBeanDefinition("advice", new RootBeanDefinition(RestResponseEntityExceptionHandler.class, null, null));

        exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);

        exceptionHandlerExceptionResolver.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).setHandlerExceptionResolvers(exceptionHandlerExceptionResolver).build();
    }
}
