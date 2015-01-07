package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
@ApiModel
public abstract class AbstractAuditingEntityDTO<T> {

    @ApiModelProperty(hidden = true)
    private final Map<String, String> links;

    @ApiModelProperty
    protected int version = -1;

    AbstractAuditingEntityDTO() {
        this.links = new HashMap<>();
    }

    public void add(Link link) {
        this.links.put(link.getRel(), link.getHref());
    }

    public void add(Iterable<Link> links) {
        Assert.notNull(links, "Given links must not be null!");
        for (Link candidate : links) {
            add(candidate);
        }
    }

    public abstract T getId();

    public abstract void setId(T id);

    public Link getLink(String rel) {
        Link link = null;
        if (links.get(rel) != null) {
            link = new Link(links.get(rel), rel);
        }
        return link;
    }
}
