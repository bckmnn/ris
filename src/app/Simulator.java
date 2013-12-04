package app;

import java.util.Set;

import akka.actor.UntypedActor;
import app.messages.Message;
import app.messages.UpdateNodesMessage;
import app.nodes.Node;

public class Simulator extends UntypedActor {
    private Set<Node> updateNodes;
    
    private void initialize() {
        
        
        getSender().tell(Message.INITIALIZED, self());
    }

    private void simulate() {

        if (!updateNodes.isEmpty()) {
            for (Node n : updateNodes) {
                n.updateWorldTransform();
            }
        }
        
        getSender().tell(Message.DONE, self());
    }
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
            simulate();
        } else if (message == Message.INIT) {
            initialize();
        } else if (message instanceof UpdateNodesMessage) {
            updateNodes = ((UpdateNodesMessage) message).updateNodes;
        }
    }
}