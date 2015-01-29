package ie.ianduffy.carcloud.web.assembler;

import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.web.dto.TrackDTO;
import ie.ianduffy.carcloud.web.rest.DeviceResource;
import ie.ianduffy.carcloud.web.rest.TrackResource;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TrackDTOAssembler {

    @Inject
    Mapper mapper;

    public TrackDTO toResource(Track track) {
        TrackDTO resource = new TrackDTO();


        resource.setFields(track.getFields());

        track.setFields(null);

        resource.add(linkTo(TrackResource.class).slash(track.getId()).withSelfRel());
        resource.add(linkTo(DeviceResource.class).slash(track.getDevice().getId()).withRel("device"));

        mapper.map(track, resource);
        return resource;
    }

}

