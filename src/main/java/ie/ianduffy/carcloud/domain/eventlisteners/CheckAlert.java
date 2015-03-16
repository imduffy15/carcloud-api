package ie.ianduffy.carcloud.domain.eventlisteners;

import ie.ianduffy.carcloud.config.AutowireHelper;
import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.domain.Field;
import ie.ianduffy.carcloud.domain.Track;
import ie.ianduffy.carcloud.web.assembler.TrackDTOAssembler;
import lombok.extern.java.Log;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.PostPersist;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Log
@Component
public class CheckAlert {

    @PostPersist
    public void onPostPersist(Track track) {
        List<Alert> alerts = track.getDevice().getAlerts();
        if(track.getFields() != null) {
            for (Alert alert : alerts) {
                for (Map.Entry<String, String> entry : alert.getFields().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    for (Field field : track.getFields()) {
                        if (key.equals(field.getName())) {
                            if (value.equals(field.getValue().toString())) {
                                log.info("#########");
                                log.info("Match for " + alert.getId() + " on field " + key + " with value " + value);
                            }
                        }
                    }
                }
            }
        }
    }

}
