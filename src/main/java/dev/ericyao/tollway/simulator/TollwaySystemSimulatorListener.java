package dev.ericyao.tollway.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import dev.ericyao.tollway.simulator.runnable.Simulator;

@Component
public class TollwaySystemSimulatorListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	Simulator sim;
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent evt) {
//		System.out.println("run after spring boot startup");
		
		new Thread(sim).start();
	}
}
