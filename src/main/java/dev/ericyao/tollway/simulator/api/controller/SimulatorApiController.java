package dev.ericyao.tollway.simulator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ericyao.tollway.simulator.runnable.Simulator;

@RestController
@RequestMapping("/")
public class SimulatorApiController {
	
	@Autowired
	Simulator sim;
	
	@GetMapping
	public String startSimulator() {
		return startSimulatorWithTimeout(sim.getDefaultTimeout());
	}
	
	@GetMapping("/{timeout}")
	public String startSimulatorWithTimeout(@PathVariable("timeout") int timeoutInSeconds) {
		if (!Simulator.running) {
			sim.setTimeout(timeoutInSeconds * 1000);
			new Thread(sim).start();
			return "success: simulator starts..";
		} else {
			return "fail: simulator has already been started.."; 
		}
	}
}
