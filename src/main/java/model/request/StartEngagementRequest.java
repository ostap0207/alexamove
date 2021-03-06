package model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import model.MediaOptions;
import model.Visitor;
import model.Webhook;

import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty("webhooks")
    private List<Webhook> webhooks = new ArrayList<>();
}