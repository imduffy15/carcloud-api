package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Api(
    value = "user",
    description = "User API"
)
@RestController
@RequestMapping("/app/rest/users")
public class UserResource {

    private UserDTOAssembler userDTOAssembler;

    private UserService userService;

    @Inject
    public UserResource(UserDTOAssembler userDTOAssembler, UserService userService) {
        this.userDTOAssembler = userDTOAssembler;
        this.userService = userService;
    }

    @Timed
    @ApiOperation(
        value = "Get User",
        notes = "Gets the specified user",
        response = UserDTO.class
    )
    @RequestMapping(
        value = "/{username}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> get(
        @ApiParam(value = "username of the user", required = true) @PathVariable("username") String login
    ) {
        User user = userService.findOne(login);
        return new ResponseEntity<>(userDTOAssembler.toResource(user), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Search users",
        notes = "Search users by username",
        response = UserDTO.class,
        responseContainer = "List"
    )
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> search(
        @ApiParam(value = "username to search for", required = true) @RequestParam("username") String username
    ) {
        List<UserDTO> users = new ArrayList<>();
        for (User user : userService.findLike(username)) {
            users.add(userDTOAssembler.toResource(user));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
