package app;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import app.messages.Message;

public class WorldState extends UntypedActor {
    public static ActorSystem system;

    private Map<ActorRef, Boolean> unitState = new HashMap<ActorRef, Boolean>();

    private ActorRef renderer;
    private ActorRef simulator;
    private ActorRef input;
    
    long old = System.nanoTime();
    
    protected void initialization() {
        
    }

    private void loop() {
        renderer.tell(Message.DISPLAY, self());
        simulator.tell(Message.DISPLAY, self());
        input.tell(Message.DISPLAY, self());
        long now = System.nanoTime();
        System.out.println("Took " + ((now - old) / 1000000.0) + "ms");
        old = now;
        System.out.println("Relooping");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.DONE) {
            System.out.println("DONE " + System.currentTimeMillis() + " " + getSender());
            
            
            unitState.put(getSender(), true);
            
            System.out.println(unitState);

            
            if (!unitState.containsValue(false)) {
                for (Map.Entry<ActorRef, Boolean> entry : unitState.entrySet())
                    entry.setValue(false);
                loop();
            }
        } else if (message == Message.INIT) {
            initialization();
            
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

    public static void main(String[] args) {
        system = ActorSystem.create();
        system.actorOf(Props.create(WorldState.class), "WorldState").tell(Message.INIT, ActorRef.noSender());
    }
}
