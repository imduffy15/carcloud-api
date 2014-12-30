package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;

import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

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
        if (!SecurityUtils.getCurrentLogin().equals(login) && !SecurityUtils.getCurrentAuthorities()
            .contains(
                AuthoritiesConstants.ADMIN)) {
            throw new EntityNotFoundException();
        }
        User user = userService.findOne(login);
        return new ResponseEntity<>(userDTOAssembler.toResource(user), HttpStatus.OK);
    }

    @Timed
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestParam("username") String username) {
        return new ResponseEntity<>(userService.findLike(username), HttpStatus.OK);
    }
}
