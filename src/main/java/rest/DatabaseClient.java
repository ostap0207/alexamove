package rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.VisitorAuthentication;

/**
 * Created by margus on 30.09.17.
 */

public class DatabaseClient {

    public static void saveVisitorAuthentication(VisitorAuthentication authentication) {
        String dbUrl = "https://alexamove-c2f5.restdb.io/rest/visitors";
        HttpEntity<VisitorAuthentication> dbEntity = new HttpEntity(authentication, getDbHeaders());

        ResponseEntity<String> resp = new RestTemplate().postForEntity(dbUrl, dbEntity, String.class);
    }

    public static VisitorAuthentication getVisitorAuthentication() {
        String dbUrl = "https://alexamove-c2f5.restdb.io/rest/visitors";
        HttpEntity<VisitorAuthentication> dbEntity = new HttpEntity(getDbHeaders());

        ResponseEntity<VisitorAuthentication[]> resp = new RestTemplate().exchange(dbUrl, HttpMethod.GET, dbEntity, VisitorAuthentication[].class);
        VisitorAuthentication[] body = resp.getBody();
        return body[body.length-1];
    }

    private static HttpHeaders getDbHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apikey", "414b89374a1f5817a4cde7f8bd35c7d88ebf4");
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
