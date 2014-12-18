//package ie.ianduffy.carcloud.web.rest;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.inject.Inject;
//
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import ie.ianduffy.carcloud.Application;
//import ie.ianduffy.carcloud.domain.Device;
//import ie.ianduffy.carcloud.repository.DeviceRepository;
//
//
///**
// * Test class for the DeviceResource REST controller.
// *
// * @see DeviceResource
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class })
//@ActiveProfiles("dev")
//public class DeviceResourceTest {
//
//    private static final Long DEFAULT_ID = new Long(1L);
//
//    private static final LocalDate DEFAULT_SAMPLE_DATE_ATTR = new LocalDate(0L);
//
//    private static final LocalDate UPD_SAMPLE_DATE_ATTR = new LocalDate();
//
//    private static final String DEFAULT_SAMPLE_TEXT_ATTR = "sampleTextAttribute";
//
//    private static final String UPD_SAMPLE_TEXT_ATTR = "sampleTextAttributeUpt";
//
//    @Inject
//    private DeviceRepository deviceRepository;
//
//    private MockMvc restDeviceMockMvc;
//
//    private Device device;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        DeviceResource deviceResource = new DeviceResource();
//        ReflectionTestUtils.setField(deviceResource, "deviceRepository", deviceRepository);
//
//        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource).build();
//
//        device = new Device();
//        device.setId(DEFAULT_ID);
//    }
//
//    @Test
//    public void testCRUDDevice() throws Exception {
//
//    }
//}
