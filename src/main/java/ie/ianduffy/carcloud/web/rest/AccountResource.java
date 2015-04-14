package ie.ianduffy.carcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.service.UserService;
import ie.ianduffy.carcloud.web.assembler.UserDTOAssembler;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

@Api(
    value = "account",
    description = "Account API"
)
@RestController
@RequestMapping("/app")
public class AccountResource {

    private UserDTOAssembler userDTOAssembler;

    private UserService userService;

    @Inject
    public AccountResource(UserDTOAssembler userDTOAssembler, UserService userService) {
        this.userDTOAssembler = userDTOAssembler;
        this.userService = userService;
    }

    @Timed
    @ApiOperation(
        value = "Create account",
        notes = "Creates a new account"
    )
    @RequestMapping(
        value = "/rest/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
        @ApiParam(value = "new user object", required = true) @Valid @RequestBody UserDTO userDTO
    ) {
        User user = userService.create(userDTO);
        return new ResponseEntity<>(userDTOAssembler.toResource(user), HttpStatus.CREATED);
    }

    @Timed
    @ApiOperation(
        value = "Get account",
        notes = "Gets the current account",
        response = UserDTO.class
    )
    @RequestMapping(
        value = "/rest/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> get() {
        User user = userService.findOne(SecurityUtils.getCurrentLogin());
        return new ResponseEntity<>(userDTOAssembler.toResource(user), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(
        value = "Update account",
        notes = "Updates the current account",
        response = UserDTO.class
    )
    @RequestMapping(
        value = "/rest/account",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
        @ApiParam(value = "updated user object") @Valid @RequestBody UserDTO userDTO
    ) {
        return new ResponseEntity<>(userDTOAssembler.toResource(userService.update(userDTO)),
            HttpStatus.OK);
    }
}
