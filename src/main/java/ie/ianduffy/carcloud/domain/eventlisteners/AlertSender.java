package ie.ianduffy.carcloud.domain.eventlisteners;

import ie.ianduffy.carcloud.config.AutowireHelper;
import ie.ianduffy.carcloud.domain.*;
import ie.ianduffy.carcloud.service.SMSService;
import org.joda.time.LocalTime;

import javax.inject.Inject;
import javax.persistence.PostPersist;
import java.util.List;

public class AlertSender {

    @Inject
    private SMSService smsService;

    private void checkFields(Track track, Alert alert) {
        for (AlertFieldWrapper alertField : alert.getFields()) {
            for (Field field : track.getFields()) {
                if (shouldTakeAction(field, alertField.getField(), alertField.getOperation())) {
                    smsService.sendSMS("00353861770680", "alert");
                }

            }
        }
    }

    private boolean checkTimeTrigger(Track track, Alert alert) {
        LocalTime recordedAt = track.getRecordedAt().toLocalTime();
        LocalTime after = alert.getAfter();
        LocalTime before = alert.getBefore();
        return after == null || before == null || (recordedAt.isAfter(after) && recordedAt.isBefore(before));
    }

    @PostPersist
    public void onPostPersist(Track track) {
        AutowireHelper.autowire(this, this.smsService);
        sendAlerts(track);
    }

    private void sendAlerts(Track track) {
        List<Alert> alerts = track.getDevice().getAlerts();
        if (track.getFields() != null) {
            for (Alert alert : alerts) {
                if (checkTimeTrigger(track, alert)) {
                    checkFields(track, alert);
                }
            }
        }
    }

    private boolean shouldTakeAction(Field input, Field criteria, Operation operation) {
        if (input.getName().equals(criteria.getName()) && input.getClass().equals(criteria.getClass())) {
            if (operation == Operation.EQUALSTO || operation == null) {
                if (input.equals(criteria)) return true;
            } else if (operation == Operation.LESSTHAN) {
                if (input.compareTo(criteria) < 0) return true;
            } else if (operation == Operation.GREATERTHAN) {
                if (input.compareTo(criteria) > 0) return true;
            } else if (operation == operation.LESSTHANANDEQUALSTO) {
                if (input.compareTo(criteria) <= 0) return true;
            } else if (operation == Operation.GREATERTHANANDEQUALSTO) {
                if (input.compareTo(criteria) >= 0) return true;
            }
        }
        return false;
    }
}
