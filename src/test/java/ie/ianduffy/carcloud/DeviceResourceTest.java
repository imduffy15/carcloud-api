package ie.ianduffy.carcloud;

import ie.ianduffy.carcloud.assembler.DeviceDTOAssembler;
import ie.ianduffy.carcloud.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.DeviceService;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import ie.ianduffy.carcloud.web.error.RestResponseEntityExceptionHandler;
import ie.ianduffy.carcloud.web.rest.AccountResource;
import ie.ianduffy.carcloud.web.rest.DeviceResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see ie.ianduffy.carcloud.service.UserService
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class DeviceResourceTest extends AbstractResourceTest{

    @Inject
    private DeviceDTOAssembler deviceDTOAssembler;

    @Before
    public void setup() {
        DeviceResource deviceResource = new DeviceResource();
        ReflectionTestUtils.setField(deviceResource, "deviceDTOAssembler", deviceDTOAssembler);
        ReflectionTestUtils.setField(deviceResource, "deviceService", deviceService);
        super.setup(deviceResource);
    }

    @Test
    public void testCreateDevice() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(2L);
        deviceDTO.setDescription("Test device");

        mockMvc.perform(post("/app/rest/devices")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
                                    .with(user(userService.getUser("user"))))
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateDevice() throws Exception {
        Device device = deviceService.findOne(2L);
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(device.getId());
        deviceDTO.setVersion(device.getVersion());

        deviceDTO.setDescription("Modified device");

        mockMvc.perform(post("/app/rest/devices")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
                                    .with(user(userService.getUser("user"))))
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateADeviceTheUserDoesntOwn() throws Exception {
        Device device = deviceService.findOne(1L);
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(device.getId());
        deviceDTO.setVersion(device.getVersion());

        deviceDTO.setDescription("Modified device");

        mockMvc.perform(post("/app/rest/devices")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(deviceDTO))
                                    .with(user(userService.getUser("user"))));
    }

    @Test
    public void testDeleteADevice() throws Exception {
        mockMvc.perform(delete("/app/rest/devices/2")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(user(userService.getUser("user"))))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteADeviceTheUserDoesntOwn() throws Exception{
        mockMvc.perform(delete("/app/rest/devices/1")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(user(userService.getUser("user"))))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetADevice() throws Exception {
        mockMvc.perform(get("/app/rest/devices/2")
                            .accept(MediaType.APPLICATION_JSON)
                            .with(user(userService.getUser("user"))))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetADeviceTheUserDoesntOwn() throws Exception {
        mockMvc.perform(get("/app/rest/devices/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .with(user(userService.getUser("user"))))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllDevices() throws Exception {
        mockMvc.perform(get("/app/rest/devices")
                            .accept(MediaType.APPLICATION_JSON)
                            .with(user(userService.getUser("user"))))
            .andExpect(status().isOk());
    }
}
