package app;

import akka.actor.UntypedActor;
import app.messages.Message;

/**
 * TODO
 * @author Constantin
 *
 */
public class Input extends UntypedActor {

    private void initialize() {

        getSender().tell(Message.INITIALIZED, self());
    }

    private void run() {

        getSender().tell(Message.DONE, self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
            run();
        } else if (message == Message.INIT) {
            initialize();
        }
    }

}