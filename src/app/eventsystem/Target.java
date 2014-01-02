package app.eventsystem;

import akka.actor.ActorRef;

public interface Target {

	public <T> void registerObserver(ActorRef observer, T events);

	public void removeObserver(ActorRef observer);

	public <T> void removeObserver(ActorRef observer, T events);

	public <T> void notifyAllObservers(T events);
	
	public void sendSignal(ActorRef recipient);
}