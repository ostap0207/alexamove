package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 30.09.17.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitorAuthentication {

    @JsonProperty("Authorization")
    private String authorization;

    @JsonProperty("X-Salemove-Visit-Session-Id")
    private String saleMoveVisitSessionId;
}
