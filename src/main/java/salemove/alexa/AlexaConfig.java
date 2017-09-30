package salemove.alexa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;

import salemove.alexa.helloworld.HelloWorldSpeechlet;

/**
 * Created by margus on 30.09.17.
 */
@Configuration
public class AlexaConfig {


    @Autowired
    private HelloWorldSpeechlet helloWorldSpeechlet;

    @Bean
    public ServletRegistrationBean registerServlet() {

        SpeechletServlet speechletServlet = new SpeechletServlet();
        speechletServlet.setSpeechlet(helloWorldSpeechlet);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(speechletServlet, "/hello-world");
        return servletRegistrationBean;
    }
}
