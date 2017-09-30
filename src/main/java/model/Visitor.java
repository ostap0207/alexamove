package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class Visitor {

    @JsonProperty("site_id")
    private String siteId;

    @JsonProperty("name")
    private String name;
}
