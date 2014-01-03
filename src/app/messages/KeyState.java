package app.messages;

import java.util.HashSet;
import java.util.Set;

/**
 * State passed from target to observer containing all relevant released and pressed keys.
 * 
 * @author Constantin, Benjamin Reemts
 * 
 */
public class KeyState implements State {
	private Set<Integer> pressedKeys;
	
	public KeyState() {
		pressedKeys = new HashSet<Integer>();
	}
	public KeyState(Set<Integer> pressedKeys) {
		this.pressedKeys = pressedKeys;
	}

	public Set<Integer> getPressedKeys() {
		return pressedKeys;
	}
}