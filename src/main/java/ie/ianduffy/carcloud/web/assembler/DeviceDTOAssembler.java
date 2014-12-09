package ie.ianduffy.carcloud.web.assembler;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.web.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.rest.DeviceOwnersResource;
import ie.ianduffy.carcloud.web.rest.DeviceResource;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class DeviceDTOAssembler {

    @Inject
    Mapper mapper;

    @Inject
    UserDTOAssembler userDTOAssembler;

    public DeviceDTO toResource(Device device) {
        DeviceDTO resource = new DeviceDTO();

        resource.add(linkTo(DeviceResource.class).slash(device.getId()).withSelfRel());
        resource.add(linkTo(DeviceOwnersResource.class, device.getId()).withRel("owners"));

        mapper.map(device, resource);
        return resource;
    }

}
