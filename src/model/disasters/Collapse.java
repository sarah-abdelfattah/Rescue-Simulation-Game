package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.SimulationException;
import model.infrastructure.ResidentialBuilding;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike() throws SimulationException
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		
		if(target.getStructuralIntegrity()==0)
			throw new BuildingAlreadyCollapsedException(this,"this building has collapsed :(");
		
		
		target.setFoundationDamage(target.getFoundationDamage()+10);
		super.strike();
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
