package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * REST controller for managing a user's authorities.
 */
@RestController
@RequestMapping("/app/rest/users/{username}/authorities")
public class UserAuthorityResource {

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@PathVariable("username") String login,
                       @Valid @RequestBody String authority) {
        userService.addAuthority(login, authority);
    }

    @RequestMapping(value = "/{index}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable("username") String login, @PathVariable("index") int index) {
        userService.removeAuthority(login, index);
    }

    @RequestMapping(value = "/{index}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("username") String login,
                                 @PathVariable("index") int index) {
        User user = userService.findOne(login);
        if (user.getAuthorities() != null) {
            return new ResponseEntity<>(user.getAuthorities().get(index).getName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> getAll(@PathVariable("username") String login) {
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
