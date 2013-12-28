package app;

import static app.vecmathimp.FactoryDefault.vecmath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import app.messages.Message;
import app.nodes.GroupNode;
import app.nodes.camera.Camera;
import app.nodes.shapes.Cube;
import app.shader.Shader;
import app.vecmathimp.FactoryDefault;

public class App extends WorldState {

    /**
     * 0. Pick shader of choice // TODO
     * 1. Create a camera
     * 2. Create nodes
     * 3. Assign a starting node
     * 4. Launch super.initialize();
     * 5. ???
     * 6. Profit!
     */
    @Override
    protected void initialize() {
    	    	
        camera = new Camera();
        camera.setLocalTransform(FactoryDefault.vecmath.translationMatrix(0, 0, 3));
        camera.name = "Cam";
        
        GroupNode head = new GroupNode();
        head.name = "Head";
        
        Cube cube = new Cube(shader);
        cube.appendTo(head);
        cube.name = "Cube 1";
        
        Cube c2 = new Cube(shader);
        c2.setLocalTransform(vecmath.translationMatrix(1, 1, 0));
        c2.appendTo(head);
        c2.name = "c2";
        
        startNode = head;
    }

    public static void main(String[] args) {
        system = ActorSystem.create();
        system.actorOf(Props.create(App.class), "App").tell(Message.INIT, ActorRef.noSender());
    }
}