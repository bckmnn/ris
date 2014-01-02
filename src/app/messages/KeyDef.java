package app.messages;

import java.util.Set;


public class KeyDef {
	public SimulateType type;
	public Set<Integer> keys;
	public Mode mode;
	
	public KeyDef(SimulateType type, Set<Integer> keys, Mode mode){
		this.type=type;
		this.keys=keys;
		this.mode=mode;
	}
}
