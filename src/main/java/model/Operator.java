package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class Operator {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
}
