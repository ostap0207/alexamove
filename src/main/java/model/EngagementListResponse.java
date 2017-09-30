package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class EngagementListResponse {

    @JsonProperty("engagements")
    private List<Engagement> engagements;

}