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
    protected Set<Node> updateNodes = new HashSet<Node>();

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
                
                System.out.println("Initialization finished " + time.elapsed());
                
                loop();
            }
        } else if (message == Message.INIT) {
            System.out.println("Starting initialization");
            time.elapsed();
            
            initialize();

            renderer = getContext().actorOf(Props.create(Renderer.class).withDispatcher("akka.actor.fixed-thread-dispatcher"), "Renderer");
            unitState.put(renderer, false);
            renderer.tell(Message.INIT, self());

            simulator = getContext().actorOf(Props.create(Simulator.class), "Simulator");
            unitState.put(simulator, false);
            simulator.tell(Message.INIT, self());
        } else if (message == Message.RENDERER_INITIALIZED) {
            input = getContext().actorOf(Props.create(Input.class), "Input");
            unitState.put(input, false);
            input.tell(Message.INIT, self());
        }
    }

    protected void initialize() {
    }
}
