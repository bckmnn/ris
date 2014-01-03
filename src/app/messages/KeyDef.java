package app.messages;

import java.util.Set;

import app.vecmath.Vector;

/**
 * @author Benjamin Reemts
 *
 */

public class KeyDef {
	private SimulateType type;
	private Set<Integer> keys;
	private Mode mode;
	private Vector vector;
	

	public KeyDef(SimulateType type, Set<Integer> keys, Mode mode){
		this.type=type;
		this.keys=keys;
		this.mode=mode;
	}
	
	public KeyDef(SimulateType type, Set<Integer> keys, Mode mode, Vector vec){
		this.type=type;
		this.keys=keys;
		this.mode=mode;
		this.vector=vec;
	}


	public SimulateType getType() {
		return type;
	}


	public Set<Integer> getKeys() {
		return keys;
	}


	public Mode getMode() {
		return mode;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vec) {
		this.vector = vec;
	}
	
}
