package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import ie.ianduffy.carcloud.domain.Authority;
import ie.ianduffy.carcloud.service.UserService;

import org.apache.http.auth.AUTH;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * REST controller for managing a user's authorities.
 */
@Api(
    value = "user authorities",
    description = "User authorities API"
)
@RestController
@RequestMapping("/app/rest/users/{username}/authorities")
public class UserAuthoritiesResource {

    @Inject
    private UserService userService;

    @Timed
    @ApiOperation(
        value = "Delete authority",
        notes = "Delete an authority from the specified user"
    )
    @RequestMapping(
        value = "/{authorityName}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(
        @ApiParam(value = "the user to remove an authority from", required = true) @PathVariable("username") String login,
        @ApiParam(value = "the authority name to be removed", required = true) @PathVariable("authorityName") String authorityName) {
        userService.removeAuthority(login, authorityName);
    }

    @Timed
    @ApiOperation(
        value = "Get authority",
        notes = "Get the specified authority by name for the specified user",
        response = Authority.class
    )
    @RequestMapping(
        value = "/{authorityName}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> get(
        @ApiParam(value = "the user to get an authority from", required = true) @PathVariable("username") String login,
        @ApiParam(value = "the authority name to get", required = true) @PathVariable("authorityName") String authorityName
    ) {
        Authority authority = userService.getAuthority(login, authorityName);
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Get all authorities",
        notes = "Get all authorities for the specified user",
        response = String.class,
        responseContainer = "List"
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll(
        @ApiParam(value = "the user to get authorities from", required = true) @PathVariable("username") String login
    ) {
        List<Authority> authorities = userService.getAuthorities(login);
        List<String> authoritiesDTO = new ArrayList<>();
        for (Authority authority : authorities) {
            authoritiesDTO.add(authority.getName());
        }
        return new ResponseEntity<>(authoritiesDTO, HttpStatus.OK);
    }
}
