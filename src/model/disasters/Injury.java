package model.disasters;

import exceptions.CitizenAlreadyDeadException;
import exceptions.SimulationException;
import model.people.Citizen;
import model.people.CitizenState;


public class Injury extends Disaster {

	public Injury(int startCycle, Citizen target) {
		super(startCycle, target);
	}
	@Override
	public void strike() throws SimulationException
	{
		Citizen target = (Citizen)getTarget();
		if(target.getState()==CitizenState.DECEASED)
			throw new CitizenAlreadyDeadException(this, "This Citizen has died :(");
		
		target.setBloodLoss(target.getBloodLoss()+30);
		super.strike();
	}
	@Override
	public void cycleStep() {
		Citizen target = (Citizen)getTarget();
		target.setBloodLoss(target.getBloodLoss()+10);
		
	}

}
