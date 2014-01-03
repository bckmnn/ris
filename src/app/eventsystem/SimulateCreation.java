package app.eventsystem;

import java.util.Set;

import app.messages.Mode;
import app.messages.SimulateType;
import app.vecmath.Vector;

public class SimulateCreation extends NodeCreation {
	private Set<Integer> keys;
	private SimulateType simulation;
	private Mode mode;
	private Vector vector;

	// TODO: add modelmatrix as params, can be null

	public SimulateCreation(String objectId, Set<Integer> keys, SimulateType simulation, Mode mode, Vector vec) {
		id = objectId;
		this.keys = keys;
		this.simulation = simulation;
		this.mode = mode;
		this.vector=vec;
	}

	public void setSimulation(SimulateType simulation) {
		this.simulation = simulation;
	}

	public String getObjectId() {
		return id;
	}

	public Set<Integer> getKeys() {
		return keys;
	}

	public SimulateType getSimulation() {
		return simulation;
	}

	public Mode getMode() {
		return mode;
	}

	public Vector getVector() {
		return vector;
	}
	

}
