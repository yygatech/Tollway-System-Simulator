package dev.ericyao.tollway.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import dev.ericyao.tollway.simulator.api.controller.ClientApiController;
import dev.ericyao.tollway.simulator.runnable.Simulator;

@SpringBootApplication
public class TollwaySystemSimulatorApplication {

	@Autowired
	private ClientApiController controller;
	
	@Value("${timeout}")
	private int timeout; // in milliseconds
	
	@Value("${session}")
	private int session;	// session length in milliseconds
	
	@Value("${max.number.flow.per.session}")
	private int maxNumberFlowPerSession;
	
	public static void main(String[] args) {
		SpringApplication.run(TollwaySystemSimulatorApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Simulator getSimulator() {
		return new Simulator(controller, timeout, session, maxNumberFlowPerSession);
	}

}
