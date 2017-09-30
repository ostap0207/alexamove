package rest;

import java.util.Arrays;
import java.util.List;

import model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.Answer;
import model.Engagement;
import model.Question;
import model.VisitorAuthentication;
import model.request.SurveyAnswerRequest;
import model.response.EngagementListResponse;
import model.response.EngagementRequestResponse;
import model.response.OperatorListResponse;
import model.request.StartEngagementRequest;
import model.response.QuestionListResponse;

/**
 * Created by margus on 30.09.17.
 */
public class SaleMoveClient {

    private final static String BASE_URL = "https://api.beta.salemove.com/";
    private final static String SMS_HOOK = "SMS_HOOK";
    private final static String SALEMOVE_DEV_TOKEN = "SALEMOVE_DEV_TOKEN";

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

        request.getWebhooks().add(new Webhook(System.getenv(SMS_HOOK), "POST", Arrays.asList("engagement.chat.message")));

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

    public static void saveSurveyAnswer(String lastQuestionId, String answer, String lastEngagementId) {
        String url = BASE_URL + "/engagements/" + lastEngagementId + "/visitor_survey/answers";

        SurveyAnswerRequest request = new SurveyAnswerRequest();
        request.setAnswers(Arrays.asList(new Answer(lastQuestionId, answer)));

        VisitorAuthentication authentication = DatabaseClient.getVisitorAuthentication();

        HttpEntity<SurveyAnswerRequest> entity = new HttpEntity<>(request, getVisitorHeaders(authentication));

        ResponseEntity<String> resp = new RestTemplate().exchange(url, HttpMethod.PUT, entity, String.class);
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + System.getenv(SALEMOVE_DEV_TOKEN));
        headers.set("Accept", "application/vnd.salemove.v1+json");
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private static HttpHeaders getVisitorHeaders(VisitorAuthentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authentication.getAuthorization());
        headers.set("X-Salemove-Visit-Session-Id", authentication.getSaleMoveVisitSessionId());
        headers.set("Accept", "application/vnd.salemove.v1+json");
        headers.set("Content-Type", "application/json");
        return headers;
    }

}
