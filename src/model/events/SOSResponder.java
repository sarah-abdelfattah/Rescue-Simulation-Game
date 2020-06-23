package model.events;

import exceptions.CannotTreatException;
import exceptions.SimulationException;
import simulation.Rescuable;

public interface SOSResponder {
public void respond(Rescuable r) throws SimulationException ;
}
