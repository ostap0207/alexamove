package salemove.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operator {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("available")
    private Boolean available;

    public String getFirstName() {
        return name != null ? name.split(" ")[0] : "Unknown name";
    }
}
