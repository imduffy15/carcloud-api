package ie.ianduffy.carcloud.web.rest.dto;

public class MetadataDTO {

    private String account;
    private String event;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    MetadataDTO() {

    }

    MetadataDTO(String account, String event) {
        this.account = account;
        this.event = event;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MetadataDTO{");
        sb.append(", account='").append(account).append('\'');
        sb.append(", event='").append(event).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
