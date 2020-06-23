package model.units;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import exceptions.SimulationException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws SimulationException{
		
		if(r instanceof Citizen)
			throw new IncompatibleTargetException(this,r);
		
			
			ResidentialBuilding b=(ResidentialBuilding)r;
			if(b.getDisaster()==null)
				throw new CannotTreatException(this, r);
			if(b.getStructuralIntegrity()==0){
				throw new BuildingAlreadyCollapsedException(b.getDisaster());
			}
			else
			if(this instanceof Ambulance||this instanceof DiseaseControlUnit)
				throw new IncompatibleTargetException(this, r);
			
			

			else
			if(this instanceof FireTruck){
				if(!(r.getDisaster() instanceof Fire))
					throw new CannotTreatException(this, r, "This unit is not the right unit for the disaster");
			}
			else
			if(this instanceof GasControlUnit){
				if(!(r.getDisaster() instanceof GasLeak))
					throw new CannotTreatException(this, r, "this unit is not the right unit for the disaster");
			}
			else
			if(this instanceof Evacuator){
				if(!(r.getDisaster() instanceof Collapse))
					throw new CannotTreatException(this, r, "this unit is not the right unit for the disaster");
			}


		


		
		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);


	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public String target() {
		if(target !=null) {
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

	
	public String unitType() {
		
		if(Integer.parseInt(unitID) == 1)
			return "Ambulance";
		if(Integer.parseInt(unitID) == 2)
			return "Disease Control Unit";
		if(Integer.parseInt(unitID) == 3)
			return "Evacuator";
		if(Integer.parseInt(unitID) == 4)
			return "Fire Truck";
		if(Integer.parseInt(unitID) == 5)
			return "Gas Control Unit";
		else
			return"null";
	}


	public String unitInfo(){


		String s="Unit Type: " + unitType() +"\n"+"Unit ID: "+unitID+"\n"+"State: "+state+"\n"+"StepsPerCycle: "+stepsPerCycle+"\n"+"Target at: "+target()
		+"\n"+"Location: "+location+"\n"+"DistanceToTarget: "+distanceToTarget+"\n";


		return s;
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
}
