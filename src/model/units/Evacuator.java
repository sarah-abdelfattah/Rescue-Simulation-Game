package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	@Override
	public void treat() {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0
				|| target.getOccupants().size() == 0) {
			jobsDone();
			return;
		}

		for (int i = 0; getPassengers().size() != getMaxCapacity()
				&& i < target.getOccupants().size(); i++) {
			getPassengers().add(target.getOccupants().remove(i));
			i--;
		}

		setDistanceToBase(target.getLocation().getX()
				+ target.getLocation().getY());

	}
	
	public String target() {
		if(getTarget() !=null) {
			Rescuable t= getTarget();
			if(t instanceof Citizen) {
				Citizen c = (Citizen)t;
				System.out.println("Citizen with ID: " + c.getNationalID());
				return "Citizen with ID: " + c.getNationalID();
			}
			else {
				ResidentialBuilding b = (ResidentialBuilding)t;
				return "bulding at "+ b.getLocation();
			}
		}
		else
			return null;
	}
public String unitInfo(){
		
		String s="Unit Type: Evacuator"+"\n"+ "Unit: "+getUnitID()+"\n"+"State: "+getState()+"\n"+"StepsPerCycle: "+getStepsPerCycle()+"\n"+"Target: "+ target()
				+"\n"+"Location: "+getLocation()+"\n"+"DistanceToTarget: "+getLocation().getX() + getLocation().getY()+"\n"
				+"Maximum Capacity: "+getMaxCapacity()+"\n"+"Number of passengers: "+getPassengers().size()+"\n";
		
		for(int i=0;i<getPassengers().size();i++){
			Citizen c=getPassengers().get(i);
			s+=c.citizenInfo();
		}
		
		
		return s;
	}

}
