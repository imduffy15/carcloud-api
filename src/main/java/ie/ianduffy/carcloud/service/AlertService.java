package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.Alert;
import ie.ianduffy.carcloud.repository.AlertRepository;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.web.dto.AlertDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing tracks.
 */
@Service
@Transactional
public class AlertService extends AbstractRestrictedService<Alert, Long, AlertDTO> {

    @Inject
    private AlertRepository alertRepository;

    @Override
    protected RestrictedRepository<Alert, Long> getRepository() {
        return alertRepository;
    }
}
