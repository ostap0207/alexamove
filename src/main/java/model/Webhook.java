package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaanus on 30.09.2017.
 */
@Getter
@Setter
public class Webhook {

	@JsonProperty("url")
	private String url;

	@JsonProperty("method")
	private String method;

	@JsonProperty("events")
	private List<String> events = new ArrayList<>();

	public Webhook(String url, String method, List<String> events) {
		this.url = url;
		this.method = method;
		this.events = events;
	}
}
