package model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import model.VisitorAuthentication;

/**
 * Created by margus on 30.09.17.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EngagementRequestResponse {

    @JsonProperty("visitor_authentication")
    private VisitorAuthentication visitorAuthentication;
}
