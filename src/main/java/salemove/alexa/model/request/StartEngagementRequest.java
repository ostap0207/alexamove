package salemove.alexa.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import salemove.alexa.model.MediaOptions;
import salemove.alexa.model.Visitor;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class StartEngagementRequest {

    @JsonProperty("operator_id")
    private String operatorId;

    @JsonProperty("new_site_visitor")
    private Visitor newSiteVisitor;

    private String media;

    @JsonProperty("media_options")
    private MediaOptions mediaOptions;
}