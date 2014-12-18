package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import ie.ianduffy.carcloud.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/app/rest/users")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    @Inject
    private UserDTOAssembler userDTOAssembler;
    @Inject
    private UserService userService;

    @RequestMapping(value = "/{username}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> get(@PathVariable("username") String login) {
        log.debug("REST request to get Track : {}", login);
        User user = userService.getUser(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTOAssembler.toResource(user), HttpStatus.OK);
    }
}
