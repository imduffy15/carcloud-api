package ie.ianduffy.carcloud.domain.eventlisteners;

import ie.ianduffy.carcloud.config.AutowireHelper;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import lombok.extern.java.Log;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.PostPersist;

@Log
@Component
public class PublishTrack {

    private final static String TRACK_TOPIC = "/topic/device/";
    @Inject
    private SimpMessageSendingOperations messagingTemplate;
    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    @PostPersist
    public void onPostPersist(Track track) {
        AutowireHelper.autowire(this, this.messagingTemplate);
        AutowireHelper.autowire(this, this.trackDTOAssembler);
        messagingTemplate.convertAndSend(TRACK_TOPIC + track.getDevice().getId(), trackDTOAssembler.toResource(track));
    }

}
