package model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import model.Answer;
import model.MediaOptions;
import model.Visitor;

/**
 * Created by margus on 29.09.17.
 */
@Getter
@Setter
public class SurveyAnswerRequest {

    @JsonProperty("answers")
    private List<Answer> answers;

}