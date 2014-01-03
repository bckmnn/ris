package app.messages;

import akka.actor.ActorRef;

public class PhysicInitialization {

	public ActorRef simulator;

	public PhysicInitialization(ActorRef simulator) {
		this.simulator = simulator;
	}

}
