package app;


import akka.actor.UntypedActor;
import app.eventsystem.NodeModification;
import app.eventsystem.PhysicModification;
import app.eventsystem.SimulateCreation;
import app.messages.Message;

public class ArtificalIntelligence extends UntypedActor {
    
    //private Map<String, Node> nodes = new HashMap<String, Node>();
    
    private void initialize() {
        getSender().tell(Message.INITIALIZED, self());
    }

    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
        	System.out.println("simulation loop");
        } else if (message == Message.INIT) {
            initialize();
        } else if (message instanceof NodeModification) {

        } else if(message instanceof SimulateCreation){
        	
        }else if (message instanceof PhysicModification) {
        	
        }
        
    }

	
}