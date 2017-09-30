package salemove.alexa.rest;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import salemove.alexa.model.Engagement;
import salemove.alexa.model.Question;
import salemove.alexa.model.response.EngagementListResponse;
import salemove.alexa.model.response.EngagementRequestResponse;
import salemove.alexa.model.MediaOptions;
import salemove.alexa.model.Operator;
import salemove.alexa.model.response.OperatorListResponse;
import salemove.alexa.model.request.StartEngagementRequest;
import salemove.alexa.model.Visitor;
import salemove.alexa.model.response.QuestionListResponse;

/**
 * Created by margus on 30.09.17.
 */
public class SaleMoveClient {

    private final static String BASE_URL = "https://api.beta.salemove.com/";

    public static List<Operator> getOperators() {
        String url = BASE_URL + "/operators?page=1";

        HttpEntity<?> entity = new HttpEntity(getHeaders());

        ResponseEntity<OperatorListResponse> response = new RestTemplate().exchange(url, HttpMethod.GET, entity, OperatorListResponse.class);
        return response.getBody().getOperators();
    }

    public static void startEngagementWithOperator(String operatorId) {
        String url = BASE_URL + "/engagement_requests";

        Visitor visitor = new Visitor();
        visitor.setName("Ostap");
        visitor.setSiteId("ea0cab17-301c-4c3b-b6eb-6cef6dd93b5c");

        MediaOptions options = new MediaOptions();
        options.setPhoneNumber("+37258578461");

        StartEngagementRequest request = new StartEngagementRequest();
        request.setOperatorId(operatorId);
        request.setNewSiteVisitor(visitor);
        request.setMedia("phone");
        request.setMediaOptions(options);

        HttpEntity<StartEngagementRequest> entity = new HttpEntity(request, getHeaders());

        ResponseEntity<EngagementRequestResponse> response = new RestTemplate().postForEntity(url, entity, EngagementRequestResponse.class);


        DatabaseClient.saveVisitorAuthentication(response.getBody().getVisitorAuthentication());
    }

    public static Engagement getLastEngagement() {
        String url = BASE_URL + "/engagements?operator_ids=739e44a5-a0b6-453b-98d1-e962c3784dfc&site_ids=ea0cab17-301c-4c3b-b6eb-6cef6dd93b5c&order=desc&per_page=1";

        HttpEntity<?> entity = new HttpEntity(getHeaders());

        ResponseEntity<EngagementListResponse> resp = new RestTemplate().exchange(url, HttpMethod.GET, entity, EngagementListResponse.class);
        return resp.getBody().getEngagements().get(0);
    }

    public static List<Question> getEngagementQuestions(String engagementId) {
        String url = BASE_URL + "engagements/" + engagementId +"/visitor_survey";
        HttpEntity<?> entity = new HttpEntity(getHeaders());

        ResponseEntity<QuestionListResponse> resp = new RestTemplate().exchange(url, HttpMethod.GET, entity, QuestionListResponse.class);
        return resp.getBody().getQuestions();
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token gUYitdQwpRSwCb6oEwmlgQ");
        headers.set("Accept", "application/vnd.salemove.v1+json");
        headers.set("Content-Type", "application/json");
        return headers;
    }

}
