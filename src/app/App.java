package app;

import ogl.nodes.camera.Camera;
import ogl.nodes.shapes.Cube;
import ogl.vecmathimp.FactoryDefault;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import app.messages.Message;

public class App extends WorldState {
    
    private Cube cube;

    @Override
    protected void initialize() {
        System.out.println("Initializing custom stuff...");
        camera = new Camera();
        updateNodes.add(camera);
        cube = new Cube(shader);
        updateNodes.add(cube);
        
        camera.setLocalTransform(FactoryDefault.vecmath.translationMatrix(0, 0, 3));
        camera.activate();
        
        startNode = cube;
        
        super.initialize();
    }

    public static void main(String[] args) {
        system = ActorSystem.create();
        system.actorOf(Props.create(App.class), "App").tell(Message.INIT, ActorRef.noSender());
    }
}