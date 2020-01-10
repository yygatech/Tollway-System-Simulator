package dev.ericyao.tollway.simulator.api.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.ericyao.tollway.simulator.object.Vehicle;

@RestController
public class ApiController {
	
	@Value("${tollway.client.url}")
	private String tollwayClientUrl;
	
	// TODO: consider using other method
	@Value("${tollway.gate.id}")
	private List<Integer> gateIds;
	
	// TODO: consider using other method
	@Value("${tollway.lane.id}")
	private List<Integer> laneIds;
	
	private Random gateRand = new Random();
	private Random laneRand = new Random();
	
	@Autowired
	RestTemplate restTemplate;
	
	public void sendVehicle(Vehicle vehicle) {
		
		HttpEntity<Vehicle> entity = new HttpEntity<>(
				vehicle, new HttpHeaders());
		
		int gateId = gateIds.get(gateRand.nextInt(gateIds.size()));
		int laneId = laneIds.get(laneRand.nextInt(laneIds.size()));
		
		String response = restTemplate.exchange(
				tollwayClientUrl + "/" + laneId,
				HttpMethod.POST,
				entity,
				String.class).getBody();
		
//		System.out.println("Response=" + response);
	}
	
}
