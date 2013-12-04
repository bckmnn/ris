package app.messages;

import app.nodes.Node;
import app.nodes.camera.Camera;

public class SceneMessage {
    public Node start;
    public Camera cam;
    
    public SceneMessage() {}
    
    public SceneMessage(Node start, Camera cam) {
        this.start = start;
        this.cam = cam;
    }
}