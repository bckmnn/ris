package app.messages;

import java.util.Set;

import app.vecmath.Matrix;

/**
 * @author Benjamin Reemts
 *
 */

public class KeyDef {
	private SimulateType type;
	private Set<Integer> keys;
	private Mode mode;
	private Matrix modelMatrix;
	

	public KeyDef(SimulateType type, Set<Integer> keys, Mode mode){
		this.type=type;
		this.keys=keys;
		this.mode=mode;
	}
	
	public KeyDef(SimulateType type, Set<Integer> keys, Mode mode, Matrix modelMatrix){
		this.type=type;
		this.keys=keys;
		this.mode=mode;
		this.modelMatrix=modelMatrix;
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

	public Matrix getModelMatrix() {
		return modelMatrix;
	}

	public void setModelMatrix(Matrix modelMatrix) {
		this.modelMatrix = modelMatrix;
	}
	
}
