package app;

import static app.nodes.NodeFactory.nodeFactory;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;
import app.eventsystem.NodeCreation;
import app.eventsystem.NodeModification;
import app.eventsystem.Types;
import app.messages.Message;
import app.messages.RendererInitialization;
import app.nodes.Node;

public class Physic extends UntypedActor {

	private Map<String, Node> nodes = new HashMap<String, Node>();

	private void initialize() {
		getSender().tell(Message.INITIALIZED, self());
		System.out.println("Physic initialised");
	}

	public void physic() {
		
		getSender().tell(Message.DONE, self());
		System.out.println("physic loop");
	}

	public void onReceive(Object message) throws Exception {
		if (message == Message.LOOP) {
			physic();
		} else if (message == Message.INIT) {
			initialize();
		} else if (message instanceof NodeCreation) {
			System.out.println("PHHHHHYYYYYYYYYYYYYYYYYYYSSSSSSSSIIIIIICCC");

			if (((NodeCreation) message).type == Types.GROUP) {
				Node newNode = nodeFactory
						.groupNode(((NodeCreation) message).id);
				nodes.put(newNode.id, newNode);
			} else if (((NodeCreation) message).type == Types.CUBE) {

				System.out.println("Shadering cube with "
						+ ((NodeCreation) message).shader);

				Node newNode = nodeFactory.cube(((NodeCreation) message).id,
						((NodeCreation) message).shader,
						((NodeCreation) message).w, ((NodeCreation) message).h,
						((NodeCreation) message).d);
				nodes.put(newNode.id, newNode);
			} else if (message instanceof NodeModification) {
				if(nodes.containsKey(((NodeModification) message).id)){
				System.out.println("NodeModification");

				System.out.println("Nodes " + nodes);
				System.out.println("Accesing "
						+ ((NodeModification) message).id);
				
				Node modify = nodes.get(((NodeModification) message).id);

				if (((NodeModification) message).localMod != null) {
					// modify.setLocalTransform(((NodeModification)
					// message).localMod);
					modify.updateWorldTransform(((NodeModification) message).localMod);
					// modify.setLocalTransform(modify.getWorldTransform());
				}
				if (((NodeModification) message).appendTo != null) {

					System.out.println("Appending "
							+ ((NodeModification) message).id + " to "
							+ ((NodeModification) message).appendTo);

					modify.appendTo(nodes
							.get(((NodeModification) message).appendTo));
				}
				}
			}
		}
	}
}
