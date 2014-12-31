package ie.ianduffy.carcloud.web.assembler;

import ie.ianduffy.carcloud.domain.User;
import ie.ianduffy.carcloud.web.dto.UserDTO;
import ie.ianduffy.carcloud.web.rest.UserAuthoritiesResource;
import ie.ianduffy.carcloud.web.rest.UserResource;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserDTOAssembler {

    @Inject
    Mapper mapper;

    public UserDTO toResource(User user) {
        UserDTO resource = new UserDTO();

        resource.add(linkTo(UserResource.class).slash(user.getUsername()).withSelfRel());
        resource
            .add(linkTo(UserAuthoritiesResource.class, user.getUsername()).withRel("authorities"));

        mapper.map(user, resource);
        return resource;
    }

}
