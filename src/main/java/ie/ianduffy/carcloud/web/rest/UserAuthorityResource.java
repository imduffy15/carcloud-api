package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/app/rest/users/{login}/authorities")
public class UserAuthorityResource {

    @Inject
    private UserService userService;

    /**
     * POST  /rest/devices/:id/owners -> Create a new owner.
     */
    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@PathVariable("login") String login, @Valid @RequestBody String authority) {
        userService.addAuthority(login, authority);
    }

    /**
     * DELETE  /rest/devices/:id/owners/:index -> delete the owner.
     */
    @RequestMapping(value = "/{index}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("login") String login, @PathVariable("index") int index) {
        userService.removeAuthority(login, index);
    }

    /**
     * GET  /rest/devices/:id/owner/:index -> get the "id" device.
     */
    @RequestMapping(value = "/{index}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("login") String login, @PathVariable("index") int index) {
        User user = userService.getUser(login);
        if (user.getAuthorities() != null) {
            return new ResponseEntity<>(user.getAuthorities().get(index).getName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET  /rest/devices -> get all the devices.
     */
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll(@PathVariable("login") String login) {
        List<Authority> authorities = userService.getAuthorities(login);
        List<String> authoritiesDTO = new ArrayList<>();
        if (authorities != null) {
            for (Authority authority : authorities) {
                authoritiesDTO.add(authority.getName());
            }
        }
        return new ResponseEntity<>(authoritiesDTO, HttpStatus.OK);
    }
}
