package dev.ericyao.tollway.simulator.runnable;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.ericyao.tollway.simulator.controller.Controller;
import dev.ericyao.tollway.simulator.object.Vehicle;

public class Simulator extends Thread {
	
	private Random rand = new Random();
	
	private Controller controller;
	
	private int timeout;
	private int session;
	private int maxNumberFlowPerSession;
	
	public Simulator(Controller controller, int timeout, int session, int maxNumberFlowPerSession) {
		this.controller = controller;
		this.timeout = timeout;
		this.session = session;
		this.maxNumberFlowPerSession = maxNumberFlowPerSession;
	}

	@Override
	public void run() {
		try {
			System.out.println(">>>> SIMULATOR STARTUP>>>>");
			
			ExecutorService es = Executors.newFixedThreadPool(maxNumberFlowPerSession);
			
			long start = System.currentTimeMillis();
			long end = start + timeout;
			
			int sessionId = 1;
			
			do {
				System.out.println("\n\n>>>> start session [" + sessionId + "] >>>>");
				
				int nFlow = 1 + rand.nextInt(maxNumberFlowPerSession);
				System.out.printf("%d vehicles to schedule\n", nFlow);
				
				for (int i = 0; i < nFlow; i++) {
					
					Vehicle v = new Vehicle(generateVehicleId());
					VehicleScheduler sv = new VehicleScheduler(v);
					sv.setLag(generateLagInMilliseconds(session));
					sv.setController(controller);
					
					System.out.printf("\t%s scheduled after %.2f sec\n", 
							sv.getVehicle().getVehicleId(), 
							((float) sv.getLag()) / 1000);
					es.execute(sv);
				}
				
				Thread.sleep(session);
				
				System.out.println("<<<< finish session [" + sessionId + "] <<<<\n\n");
				
				sessionId++;
				
			} while (System.currentTimeMillis() < end);
			
			System.out.println(">>>> cooling down for 3 sec >>>>");
			Thread.sleep(3000);
			
			System.out.println("<<<< SIMULATOR SHUTDOWN <<<<");
			
		} catch (InterruptedException e) {
			System.out.println("<<<< stop (interrupt) simulation <<<<");
		}
	}
	
	// helper methods
	private String generateVehicleId() {
		String ret = "";
		for (int i = 0; i < 3; i++) {
			char ch = (char) ('A' + rand.nextInt(26));
			ret += ch;
		}
		for (int i = 0; i < 4; i++) {
			ret += rand.nextInt(10);
		}
		return ret;
	}
	
	private int generateLagInMilliseconds(int maxLag) {
		return rand.nextInt(maxLag);
	}
}
