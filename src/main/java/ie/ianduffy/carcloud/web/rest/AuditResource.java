package ie.ianduffy.carcloud.web.rest;

import ie.ianduffy.carcloud.security.AuthoritiesConstants;
import ie.ianduffy.carcloud.service.AuditEventService;
import ie.ianduffy.carcloud.web.propertyeditors.LocaleDateTimeEditor;

import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

/**
 * REST controller for getting the audit events.
 */
@RestController
@RequestMapping("/app")
public class AuditResource {

    @Inject
    private AuditEventService auditEventService;

    @RequestMapping(value = "/rest/audits/all",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll() {
        return auditEventService.findAll();
    }

    @RequestMapping(value = "/rest/audits/byDates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") LocalDateTime fromDate,
                                        @RequestParam(value = "toDate") LocalDateTime toDate) {
        return auditEventService.findByDates(fromDate, toDate);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class,
                                    new LocaleDateTimeEditor("yyyy-MM-dd", false));
    }
}
