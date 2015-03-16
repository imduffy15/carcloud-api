package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.repository.AlertRepository;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Map;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class AlertService extends AbstractRestrictedService<Alert, Long, AlertDTO> {

    @Inject
    private AlertRepository alertRepository;

    public Alert addField(Long alertId, String key, String value) {
        Alert alert = findOneForCurrentUser(alertId);
        alert.getFields().put(key, value);
        alertRepository.save(alert);
        return alert;
    }

    @Transactional(readOnly = true)
    public Map<String, String> getFields(Long id) {
        Alert alert = findOneForCurrentUser(id);
        Map<String, String> fields = alert.getFields();
        Hibernate.initialize(fields);
        return fields;
    }

    @Override
    protected RestrictedRepository<Alert, Long> getRepository() {
        return alertRepository;
    }

    public void removeAlert(Long fieldId, Long alertId) {

    }
}
