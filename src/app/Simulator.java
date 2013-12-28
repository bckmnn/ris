package app;


import akka.actor.UntypedActor;
import app.messages.Message;
import app.nodes.Node;

public class Simulator extends UntypedActor {
    private Node startNode;
    
    private void initialize() {
        getSender().tell(Message.INITIALIZED, self());
    }

    private void simulate() {
                
        getSender().tell(Message.DONE, self());
    }
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
            simulate();
        } else if (message == Message.INIT) {
            initialize();
        }
    }
}