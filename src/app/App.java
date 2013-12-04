package app;

import static app.vecmathimp.FactoryDefault.vecmath;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import app.messages.Message;
import app.nodes.GroupNode;
import app.nodes.camera.Camera;
import app.nodes.shapes.Cube;
import app.vecmathimp.FactoryDefault;

public class App extends WorldState {

    /**
     * 1. Create a camera
     * 2. Create nodes
     * 3. Add your cam and nodes to 'updateNodes'
     * 4. Assign a starting node
     * 5. Launch super.initialize();
     * 6. ???
     * 7. Profit!
     */
    @Override
    protected void initialize() {
        camera = new Camera();
        camera.setLocalTransform(FactoryDefault.vecmath.translationMatrix(0, 0, 3));
        updateNodes.add(camera);
        
        GroupNode head = new GroupNode();
        updateNodes.add(head);
        
        Cube cube = new Cube(shader);
        cube.appendTo(head);
        updateNodes.add(cube);
        
        Cube c2 = new Cube(shader);
        c2.setLocalTransform(vecmath.translationMatrix(1, 1, 0));
        c2.appendTo(head);
        updateNodes.add(c2);
        
        
        startNode = head;
        
        super.initialize();
    }

    public static void main(String[] args) {
        system = ActorSystem.create();
        system.actorOf(Props.create(App.class), "App").tell(Message.INIT, ActorRef.noSender());
    }
}