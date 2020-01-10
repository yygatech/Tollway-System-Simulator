package dev.ericyao.tollway.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import dev.ericyao.tollway.simulator.api.controller.ApiController;
import dev.ericyao.tollway.simulator.runnable.Simulator;

@Component
public class TollwaySystemSimulatorListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private ApiController controller;
	
	@Value("${timeout}")
	private int timeout; // in milliseconds
	
	@Value("${session}")
	private int session;	// session length in milliseconds
	
	@Value("${max.number.flow.per.session}")
	private int maxNumberFlowPerSession;
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent evt) {
//		System.out.println("run after spring boot startup");

		Simulator sim = new Simulator(controller, timeout, session, maxNumberFlowPerSession);
		
		sim.start();
	}
}
