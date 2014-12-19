package ie.ianduffy.carcloud.assembler;

import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.dto.DeviceDTO;
import ie.ianduffy.carcloud.web.rest.DeviceOwnersResource;
import ie.ianduffy.carcloud.web.rest.DeviceResource;
import ie.ianduffy.carcloud.web.rest.DeviceTracksResource;

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
        resource.add(linkTo(DeviceTracksResource.class, device.getId()).withRel("tracks"));

        mapper.map(device, resource);
        return resource;
    }

}
