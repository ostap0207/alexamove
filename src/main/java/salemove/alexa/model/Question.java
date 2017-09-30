package salemove.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by margus on 30.09.17.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    private String id;

    private String title;
}
