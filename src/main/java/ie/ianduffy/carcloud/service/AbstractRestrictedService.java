package ie.ianduffy.carcloud.service;

import ie.ianduffy.carcloud.domain.AbstractAuditingEntity;
import ie.ianduffy.carcloud.domain.Device;
import ie.ianduffy.carcloud.repository.RestrictedRepository;
import ie.ianduffy.carcloud.security.SecurityUtils;
import ie.ianduffy.carcloud.web.dto.AbstractAuditingEntityDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Transactional
public abstract class AbstractRestrictedService<T extends AbstractAuditingEntity, ID extends Serializable, DTO extends AbstractAuditingEntityDTO>  extends AbstractService<T, ID, DTO> {

    @Transactional(readOnly = true)
    public List<T> findAllForCurrentUser() {
        return getRepository().findAllForUser(SecurityUtils.getCurrentLogin());
    }

    @Transactional(readOnly = true)
    public T findOneForCurrentUser(ID id) {
        T entity = getRepository().findOneForUser(SecurityUtils.getCurrentLogin(), id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    abstract protected RestrictedRepository<T, ID> getRepository();

}
