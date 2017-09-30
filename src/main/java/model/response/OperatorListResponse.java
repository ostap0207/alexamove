package model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import model.Operator;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatorListResponse {

    @JsonProperty("next_page")
    private String nextPage;

    @JsonProperty("last_page")
    private String lastPage;

    @JsonProperty("operators")
    private List<Operator> operators;

}
