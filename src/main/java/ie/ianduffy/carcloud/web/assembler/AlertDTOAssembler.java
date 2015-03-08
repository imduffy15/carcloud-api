package ie.ianduffy.carcloud.web.assembler;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import ie.ianduffy.carcloud.web.rest.DeviceResource;
import ie.ianduffy.carcloud.web.rest.TrackResource;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class AlertDTOAssembler {

    @Inject
    Mapper mapper;

    public AlertDTO toResource(Alert alert) {
        AlertDTO resource = new AlertDTO();

        resource.add(linkTo(TrackResource.class).slash(alert.getId()).withSelfRel());
        resource.add(linkTo(DeviceResource.class).slash(alert.getDevice().getId()).withRel("device"));

        mapper.map(alert, resource);
        return resource;
    }

}

