package app;


import static app.nodes.NodeFactory.nodeFactory;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;
import app.eventsystem.CameraCreation;
import app.eventsystem.NodeCreation;
import app.eventsystem.NodeModification;
import app.eventsystem.StartNodeModification;
import app.eventsystem.Types;
import app.messages.Message;
import app.nodes.GroupNode;
import app.nodes.Node;
import app.nodes.camera.Camera;

public class Simulator extends UntypedActor {
    
    private Map<String, Node> nodes = new HashMap<String, Node>();
    
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
        } else if (message instanceof NodeCreation) {
        	System.out.println("NodeCreation");
        	
        	if (((NodeCreation) message).type == Types.GROUP) {
        		Node newNode = nodeFactory.groupNode(((NodeCreation) message).id);
        		nodes.put(newNode.id, newNode);
        	} else if (((NodeCreation) message).type == Types.CUBE) {
        		
        		System.out.println("Shadering cube with " + ((NodeCreation) message).shader);
        		
        		
        		Node newNode = nodeFactory.cube(((NodeCreation) message).id, ((NodeCreation) message).shader);
        		nodes.put(newNode.id, newNode);
        	}
        	
        	
        } else if (message instanceof CameraCreation) {
        	System.out.println("CameraCreation");
        	
        	Camera camera = nodeFactory.camera(((CameraCreation) message).id);
        	nodes.put(((CameraCreation) message).id, camera);
        	
        } else if (message instanceof NodeModification) {
        	System.out.println("NodeModification");
        	
        	System.out.println("Nodes " + nodes);
        	System.out.println("Accesing " + ((NodeModification) message).id);
        	
        	Node modify = nodes.get(((NodeModification) message).id);
        	
        	if (((NodeModification) message).localMod != null) {
        		modify.setLocalTransform(((NodeModification) message).localMod);
        	}
        	if (((NodeModification) message).appendTo != null) {
        		
        		System.out.println("Appending " + ((NodeModification) message).id + " to " + ((NodeModification) message).appendTo);
        		
        		
        		modify.appendTo(nodes.get(((NodeModification) message).appendTo));
        	}
        	
        	
        } else if (message instanceof StartNodeModification) {
        	System.out.println("StartNodeModification");
        	
        	Node start = nodes.get(((StartNodeModification) message).id);
        	
        }
    }
}