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
public class Answer {

    @JsonProperty("question_id")
    private String questionId;

    private String response;

    public Answer(String questionId, String response) {
        this.questionId = questionId;
        this.response = mapAnswer(response);
    }

    private String mapAnswer(String response) {
        switch (response) {
            case "one":
                return "1";
            case "two":
                return "2";
            case "three":
                return "3";
            case "four":
                return "4";
            case "five":
                return "5";
            default:
                return response;
        }
    }
}
