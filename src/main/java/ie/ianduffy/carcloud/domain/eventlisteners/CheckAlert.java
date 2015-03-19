package ie.ianduffy.carcloud.domain.eventlisteners;

import ie.ianduffy.carcloud.domain.*;
import lombok.extern.java.Log;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import java.util.List;

@Log
@Component
public class CheckAlert {

    private void checkFields(Track track, Alert alert) {
        for (AlertFieldWrapper alertField : alert.getFields()) {
            for (Field field : track.getFields()) {

                Operation operation = alertField.getOperation();

                boolean doSomething = false;

                if (operation == Operation.EQUALSTO || operation == null) {
                    if (field.equals(alertField.getField())) doSomething = true;
                } else if (operation == Operation.LESSTHAN) {
                    if (field.compareTo(alertField.getField()) < 0) doSomething = true;
                } else if (operation == Operation.GREATERTHAN) {
                    if (field.compareTo(alertField.getField()) > 0) doSomething = true;
                } else if (operation == operation.LESSTHANANDEQUALSTO) {
                    if (field.compareTo(alertField.getField()) <= 0) doSomething = true;
                } else if (operation == Operation.GREATERTHANANDEQUALSTO) {
                    if (field.compareTo(alertField.getField()) >= 0) doSomething = true;
                }

                if (doSomething) {
                    System.out.println("Something matched some criteria...");
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
        List<Alert> alerts = track.getDevice().getAlerts();
        if (track.getFields() != null) {
            for (Alert alert : alerts) {
                if (checkTimeTrigger(track, alert)) {
                    checkFields(track, alert);
                }
            }
        }
    }
}
