/**
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
 * <p>
 * http://aws.amazon.com/apache2.0/
 * <p>
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package helloworld;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import model.Operator;
import rest.SaleMoveClient;

/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class HelloWorldSpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory.getLogger(HelloWorldSpeechlet.class);

	private static final String OPERATOR_SLOT = "Operator";
	private static final String SURVEY_QUESTIONS = "SURVEY_QUESTIONS";

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session)
			throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
			throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session)
			throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("StartEngagementIntent".equals(intentName)) {
			return startSimpleEngagement(intent);
		} else if ("FeedbackIntent".equals(intentName)) {
			return getFeedbackEngagement(intent, session);
		} else if ("FeedbackAnswerIntent".equals(intentName)) {
			return getFeedbackAnswerIntent(intent, session);
		} else if ("GetOperatorsIntent".equals(intentName)) {
			return getOperatorsEngagement();
		} else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("WarmUpIntent".equals(intentName)) {
			return warmUp();
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session)
			throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
				session.getSessionId());
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}




    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
	 * @param intent
     */
    private SpeechletResponse startSimpleEngagement(Intent intent) {
		Map<String, Slot> slots = intent.getSlots();

		Slot operatorSlot = slots.get(OPERATOR_SLOT);
		String speechText = "";

		if (operatorSlot != null) {
			String operatorName = operatorSlot.getValue();
			speechText = "Connecting you with " + operatorName;

			List<Operator> operators = SaleMoveClient.getOperators();
			Optional<Operator> operator = operators.stream().filter(o -> operatorName.equalsIgnoreCase(o.getFirstName())).findFirst();

			if(operator.isPresent()) {
				SaleMoveClient.startEngagementWithOperator(operator.get().getId());
			} else {
				speechText = "Operator was not found: " + operatorName;
			}
		}

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse warmUp() {
		String speechText = "SaleMove warmed up";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "You can say hello to me!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	public SpeechletResponse getOperatorsEngagement() {
		List<Operator> operators = SaleMoveClient.getOperators();

		String operatorsString = operators.stream()
		    .filter(o -> o.getAvailable())
		    .map(o -> o.getFirstName())
		    .collect(Collectors.joining(","));
		String speechText = "Operators are: " + operatorsString;

		String repromptText = "Which operator do you want to engage?";

		return newAskResponse(speechText, repromptText);
	}

	private SpeechletResponse newAskResponse(String stringOutput, String repromptText) {
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(stringOutput);

		PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
		repromptOutputSpeech.setText(repromptText);
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(repromptOutputSpeech);

		return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
	}

	public SpeechletResponse getFeedbackEngagement(Intent intent, Session session) {
		// TODO: get last engagement ID and fetch survey and move to answers, store questions in sessions

//		List<Operator> operators = SaleMoveClient.

//		String operatorsString = operators.stream()
//				.filter(o -> o.getAvailable())
//				.map(o -> o.getFirstName())
//				.collect(Collectors.joining(","));
		String speechText = "Operators are: ";
//
		String repromptText = "";
//
//		session.setAttribute("SURVEY_QUESTIONS", myObject);

		return newAskResponse(speechText, repromptText);
	}

	public SpeechletResponse getFeedbackAnswerIntent(Intent intent, Session session) {
		// TODO: ask questions one by one, store count in sessions
		return null;
	}
}
