package dev.ericyao.tollway.simulator.runnable;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.ericyao.tollway.simulator.api.controller.ClientApiController;
import dev.ericyao.tollway.simulator.object.Vehicle;

public class Simulator implements Runnable {
	
	static final private Random RAND = new Random();
	static public boolean running = false;
	
	private ClientApiController controller;
	
	private int defaultTimeout;
	private int timeout;
	private int session;
	private int maxNumberFlowPerSession;
	
	public Simulator(ClientApiController controller, int defaultTimeout, int session, int maxNumberFlowPerSession) {
		this.controller = controller;
		this.defaultTimeout = defaultTimeout;
		this.timeout = defaultTimeout;
		this.session = session;
		this.maxNumberFlowPerSession = maxNumberFlowPerSession;
	}

	@Override
	public void run() {
		running = true;
		
		try {
			System.out.println("\n>>>> SIMULATOR STARTUP>>>>");
			
			ExecutorService es = Executors.newFixedThreadPool(maxNumberFlowPerSession);
			
			long start = System.currentTimeMillis();
			long end = start + timeout;
			
			int sessionId = 1;
			
			do {
				System.out.println("\n>>>> start session [" + sessionId + "] >>>>");
				
				int nFlow = 1 + RAND.nextInt(maxNumberFlowPerSession);
				System.out.printf("\t%d vehicles\n", nFlow);
				
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
				
				System.out.println("<<<< finish session [" + sessionId + "] <<<<\n");
				
				sessionId++;
				
			} while (System.currentTimeMillis() < end);
			
			System.out.println("\n>>>> cooling down for 3 sec >>>>");
			Thread.sleep(3000);
			
			System.out.println("<<<< SIMULATOR SHUTDOWN <<<<\n");
			
		} catch (InterruptedException e) {
			System.out.println("<<<< stop (interrupt) simulation <<<<");
		} finally {
			running = false;
		}
	}
	
	// helper methods
	private String generateVehicleId() {
		String ret = "";
		for (int i = 0; i < 3; i++) {
			char ch = (char) ('A' + RAND.nextInt(26));
			ret += ch;
		}
		for (int i = 0; i < 4; i++) {
			ret += RAND.nextInt(10);
		}
		return ret;
	}
	
	private int generateLagInMilliseconds(int maxLag) {
		return RAND.nextInt(maxLag);
	}

	public ClientApiController getController() {
		return controller;
	}

	public void setController(ClientApiController controller) {
		this.controller = controller;
	}

	public int getDefaultTimeout() {
		return defaultTimeout;
	}

	public void setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public int getMaxNumberFlowPerSession() {
		return maxNumberFlowPerSession;
	}

	public void setMaxNumberFlowPerSession(int maxNumberFlowPerSession) {
		this.maxNumberFlowPerSession = maxNumberFlowPerSession;
	}
	
}
