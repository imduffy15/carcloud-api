package ie.ianduffy.carcloud.domain.eventlisteners;

import ie.ianduffy.carcloud.config.AutowireHelper;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import javax.inject.Inject;
import javax.persistence.PostPersist;

public class TrackPublisher {

    private final static String TRACK_TOPIC = "/topic/device/";
    @Inject
    private SimpMessageSendingOperations messagingTemplate;
    @Inject
    private TrackDTOAssembler trackDTOAssembler;

    private void broadcast(Track track) {
        messagingTemplate.convertAndSend(TRACK_TOPIC + track.getDevice().getId(), trackDTOAssembler.toResource(track));
    }

    @PostPersist
    public void onPostPersist(Track track) {
        AutowireHelper.autowire(this, this.messagingTemplate);
        AutowireHelper.autowire(this, this.trackDTOAssembler);
        broadcast(track);
    }

}
