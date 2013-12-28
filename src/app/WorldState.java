package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import app.messages.Message;
import app.messages.RendererInitialization;
import app.messages.RendererInitialized;
import app.nodes.Node;
import app.nodes.camera.Camera;
import app.shader.Shader;
import app.toolkit.StopWatch;

public class WorldState extends UntypedActor {
    public static ActorSystem system;

    private StopWatch time = new StopWatch();
    private Map<ActorRef, Boolean> unitState = new HashMap<ActorRef, Boolean>();
    private ActorRef renderer;
    private ActorRef simulator;
    private ActorRef input;

    protected Node startNode;
    protected Camera camera;
    protected Shader shader;

    private void loop() {

        System.out.println("\nStarting new loop");

        simulator.tell(Message.LOOP, self());
        input.tell(Message.LOOP, self());
        renderer.tell(Message.DISPLAY, self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.DONE) {
            unitState.put(getSender(), true);

            System.out.println(time.elapsed() + " " + unitState);

            if (!unitState.containsValue(false)) {
                for (Map.Entry<ActorRef, Boolean> entry : unitState.entrySet()) {
                    entry.setValue(false);
                }
                loop();
            }
        } else if (message == Message.INITIALIZED) {
            
            System.out.println("Initialized " + getSender());
            
            unitState.put(getSender(), true);

            if (!unitState.containsValue(false)) {
                for (Map.Entry<ActorRef, Boolean> entry : unitState.entrySet()) {
                    entry.setValue(false);
                }
                
                System.out.printf("Initialization finished in %.3fs", time.elapsed());
                
                loop();
            }
        } else if (message == Message.INIT) {
            System.out.println("Starting initialization");
            time.elapsed();
            
            System.out.println("Creating Entities");
            
            renderer = getContext().actorOf(Props.create(Renderer.class).withDispatcher("akka.actor.fixed-thread-dispatcher"), "Renderer");
            unitState.put(renderer, false);

            simulator = getContext().actorOf(Props.create(Simulator.class), "Simulator");
            unitState.put(simulator, false);
            
            input = getContext().actorOf(Props.create(Input.class), "Input");
            unitState.put(input, false);
            
            System.out.println("Initializing App");

            initialize();
            
            System.out.println("App initialized");
            
            System.out.println("Initializing Entities");
            
            renderer.tell(new RendererInitialization(0), self());
            simulator.tell(Message.INIT, self());
        } else if (message instanceof RendererInitialized) {
        	shader = ((RendererInitialized) message).shader;
            input.tell(Message.INIT, self());
        }
    }

    protected void initialize() {
    }
}
