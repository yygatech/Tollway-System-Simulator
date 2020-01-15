package dev.ericyao.tollway.simulator.runnable;

import javax.validation.constraints.NotNull;

import dev.ericyao.tollway.simulator.api.controller.ClientApiController;
import dev.ericyao.tollway.simulator.object.Vehicle;

public class VehicleScheduler implements Runnable {
	
	@NotNull
	private Vehicle vehicle;
	private int lag;
	
	private ClientApiController controller;
	
	public VehicleScheduler(@NotNull Vehicle vehicle) {
		super();
		this.vehicle = vehicle;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(lag);		// schedule vehicle after lag
		} catch (InterruptedException e) {
			System.out.println("<<<< cancel (interrupt) scheduled vehicle <<<<");
		}
		
		// send vehicle via controller
		controller.sendVehicle(vehicle);
		
		System.out.printf("\t%s sent on %.2f sec\n", 
				vehicle.getVehicleId(),
				((float) lag) / 1000);
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public long getLag() {
		return lag;
	}

	public void setLag(int lag) {
		this.lag = lag;
	}

	public ClientApiController getController() {
		return controller;
	}

	public void setController(ClientApiController controller) {
		this.controller = controller;
	}
	
}
