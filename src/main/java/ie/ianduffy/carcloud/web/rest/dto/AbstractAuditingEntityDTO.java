package ie.ianduffy.carcloud.web.rest.dto;

public abstract class AbstractAuditingEntityDTO {
    protected int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
