package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.SimulationException;
import model.infrastructure.ResidentialBuilding;


public class GasLeak extends Disaster {

	public GasLeak(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}
	
	@Override
	public void strike() throws SimulationException
	{
		
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		
		if(target.getStructuralIntegrity()==0)
			throw new BuildingAlreadyCollapsedException(this,"This citizen has died :(");
		
		
		target.setGasLevel(target.getGasLevel()+10);
		super.strike();
	}
	@Override
	public void cycleStep() {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setGasLevel(target.getGasLevel()+15);
		
	}

}
