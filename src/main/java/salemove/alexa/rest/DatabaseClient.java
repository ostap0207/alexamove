package salemove.alexa.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import salemove.alexa.model.VisitorAuthentication;

/**
 * Created by margus on 30.09.17.
 */

public class DatabaseClient {

    public static void saveVisitorAuthentication(VisitorAuthentication authentication) {
        String dbUrl = "https://alexamove-c2f5.restdb.io/com.salemove.alexa.rest/visitors";
        HttpEntity<VisitorAuthentication> dbEntity = new HttpEntity(authentication, getDbHeaders());

        ResponseEntity<String> resp = new RestTemplate().postForEntity(dbUrl, dbEntity, String.class);
    }

    private static HttpHeaders getDbHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apikey", "414b89374a1f5817a4cde7f8bd35c7d88ebf4");
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
