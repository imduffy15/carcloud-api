package ie.ianduffy.carcloud;

import ie.ianduffy.carcloud.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.dto.UserDTO;
import ie.ianduffy.carcloud.repository.AuthorityRepository;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.rest.AccountResource;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see UserService
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class AccountResourceTest {

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private Mapper mapper;

    @Inject
    private PasswordEncoder passwordEncoder;

    private MockMvc restUserMockMvc;

    @Inject
    private UserDTOAssembler userDTOAssembler;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountResource accountResource = new AccountResource();
        ReflectionTestUtils.setField(accountResource, "userDTOAssembler", userDTOAssembler);
        ReflectionTestUtils.setField(accountResource, "userService", userService);

        ReflectionTestUtils.setField(userService, "authorityRepository", authorityRepository);
        ReflectionTestUtils.setField(userService, "mapper", mapper);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/app/rest/authenticate")
                                    .with(new RequestPostProcessor() {
                                        public MockHttpServletRequest postProcessRequest(
                                            MockHttpServletRequest request) {
                                            request.setRemoteUser("test");
                                            return request;
                                        }
                                    })
                                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("test"));
    }

    @Test
    public void testChangePassword() throws Exception {
        restUserMockMvc.perform(post("/app/rest/account/change_password")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes("newPassword"))
                                    .with(user(userService.getUser("user"))))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        User user = userService.getUser("user");

        restUserMockMvc.perform(get("/app/rest/account").with(
            user(user))
                                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.phone").value(user.getPhone()))
            .andExpect(jsonPath("$.links.authorities").exists())
            .andExpect(jsonPath("$.links.self").exists());
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/app/rest/authenticate")
                                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));

    }

    @Test
    public void testRegisterAccount() throws Exception {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername("mburns");
        userDTO.setFirstName("montgomery");
        userDTO.setLastName("burns");
        userDTO.setEmail("mburns@carcloud.ianduffy.ie");
        userDTO.setPhone("0861110680");
        userDTO.setPassword("password");

        restUserMockMvc.perform(post("/app/rest/register")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isCreated());

        restUserMockMvc.perform(post("/app/rest/register")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAnAccount() throws Exception {
        UserDTO userDTO = new UserDTO();

        userDTO.setLastName("newLastName");
        userDTO.setVersion(userService.getUser("user").getVersion());

        restUserMockMvc.perform(post("/app/rest/account")
                                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                                    .content(TestUtil.convertObjectToJsonBytes(userDTO))
                                    .with(user(userService.getUser("user"))))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
