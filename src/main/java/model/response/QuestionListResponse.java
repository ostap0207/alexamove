package model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import model.Engagement;
import model.Question;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class QuestionListResponse {

    @JsonProperty("questions")
    private List<Question> questions;

}