package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.repository.AlertRepository;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class AlertService extends AbstractRestrictedService<Alert, Long, AlertDTO> {

    @Inject
    private AlertRepository alertRepository;

    public Alert update(AlertDTO alertDTO) {
        Alert alert = findOneForCurrentUser(alertDTO.getId());
        alert.getFields().clear();
        alert.getFields().addAll(alertDTO.getFields());
        alert.setAfter(alertDTO.getAfter());
        alert.setBefore(alertDTO.getBefore());
        Hibernate.initialize(alert.getDevice());
        return update(alertDTO, alert);
    }

    @Override
    protected RestrictedRepository<Alert, Long> getRepository() {
        return alertRepository;
    }
}
