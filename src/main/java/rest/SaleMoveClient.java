package rest;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.EngagementResponse;
import model.MediaOptions;
import model.Operator;
import model.OperatorListResponse;
import model.SaleMoveRequest;
import model.Visitor;

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

        SaleMoveRequest request = new SaleMoveRequest();
        request.setOperatorId(operatorId);
        request.setNewSiteVisitor(visitor);
        request.setMedia("phone");
        request.setMediaOptions(options);

        HttpEntity<SaleMoveRequest> entity = new HttpEntity(request, getHeaders());

        ResponseEntity<EngagementResponse> response = new RestTemplate().postForEntity(url, entity, EngagementResponse.class);


        DatabaseClient.saveVisitorAuthentication(response.getBody().getVisitorAuthentication());
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token gUYitdQwpRSwCb6oEwmlgQ");
        headers.set("Accept", "application/vnd.salemove.v1+json");
        headers.set("Content-Type", "application/json");
        return headers;
    }

}
