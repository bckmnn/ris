package app.messages;

import java.util.HashMap;
import java.util.Map;

public class SendSignal {
	public Map<Integer, Boolean> sendsignals = new HashMap<Integer, Boolean>();
	
	public SendSignal(Map<Integer, Boolean> signals) {
		sendsignals = signals;
	}
}