package app;

import akka.actor.UntypedActor;
import app.messages.Message;

public class Input extends UntypedActor {

private void initialize() {
        
        
        getSender().tell(Message.DONE, self());
    }

    private void display() {

        
        getSender().tell(Message.DONE, self());
    }
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.DISPLAY) {
            display();
        } else if (message == Message.INIT) {
            initialize();
        }
    }

}