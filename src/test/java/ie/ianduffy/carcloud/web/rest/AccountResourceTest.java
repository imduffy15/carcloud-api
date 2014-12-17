package ie.ianduffy.carcloud.web.rest;

import ie.ianduffy.carcloud.Application;
import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.repository.UserRepository;
import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
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

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
* Test class for the AccountResource REST controller.
*
* @see UserService
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class AccountResourceTest {


    @Inject
    private UserDTOAssembler userDTOAssembler;

    @Inject
    private UserService userService;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountResource accountResource = new AccountResource();
        ReflectionTestUtils.setField(accountResource, "userDTOAssembler", userDTOAssembler);
        ReflectionTestUtils.setField(accountResource, "userService", userService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/app/rest/authenticate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/app/rest/authenticate")
                .with(new RequestPostProcessor() {
                    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        request.setRemoteUser("test");
                        return request;
                    }
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        User user = new User();
        user.setLogin("jsmith");
        user.setFirstName("john");
        user.setLastName("smith");
        user.setEmail("jsmith@carcloud.ianduffy.ie");
        user.setPhone("0861770680");
        user.setAuthorities(authorities);
        when(userService.getUser()).thenReturn(user);

        restUserMockMvc.perform(get("/app/rest/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login").value("jsmith"))
                .andExpect(jsonPath("$.firstName").value("john"))
                .andExpect(jsonPath("$.lastName").value("smith"))
                .andExpect(jsonPath("$.email").value("jsmith@carcloud.ianduffy.ie"))
                .andExpect(jsonPath("$.links.authorities").exists())
                .andExpect(jsonPath("$.links.self").exists());
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(userService.getUser()).thenReturn(null);

        restUserMockMvc.perform(get("/app/rest/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterAccount() throws Exception {
        UserDTO userDTO = new UserDTO();

        userDTO.setLogin("bjones");
        userDTO.setFirstName("bill");
        userDTO.setLastName("jones");
        userDTO.setEmail("bjones@carcloud.ianduffy.ie");
        userDTO.setPhone("0861110680");
        userDTO.setPassword("password");



        restUserMockMvc.perform(post("/app/rest/register")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andDo(print())
            .andExpect(status().isCreated());


    }
}
