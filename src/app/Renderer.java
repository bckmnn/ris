package app;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import ogl.nodes.Node;
import ogl.nodes.camera.Camera;
import ogl.shader.Shader;
import ogl.vecmath.Matrix;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import akka.actor.UntypedActor;
import app.messages.Message;
import app.messages.SceneMessage;

public class Renderer extends UntypedActor {
    private static final int width = 640;
    private static final int height = 480;

    private Shader shader;
    private Node start;
    private Camera camera;

    private void initialize() {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();

            // Limit to 60 FPS
            Display.setSwapInterval(1);
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        // Set background color to black.
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Enable depth testing.
        glEnable(GL11.GL_DEPTH_TEST);

        // Create shader and cube.
        shader = new Shader();
        
        getSender().tell(shader, self());
        getSender().tell(Message.RENDERER_INITIALIZED, self());
        getSender().tell(Message.INITIALIZED, self());
    }

    private void display() {
        // Adjust the the viewport to the actual window size. This makes the
        // rendered image fill the entire window.
        glViewport(0, 0, width, height);

        // Clear all buffers.
        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        shader.activate();

        // Assemble the transformation matrix that will be applied to all
        // vertices in the vertex shader.
        float aspect = (float) width / (float) height;

        // The perspective projection. Camera space to NDC.
        Matrix projectionMatrix = vecmath.perspectiveMatrix(60f, aspect, 0.1f, 100f);
        Shader.setProjectionMatrix(projectionMatrix);
        
        camera.activate();
        start.display();

        Display.setTitle("App");
        Display.update();
        
        getSender().tell(Message.DONE, self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Message.LOOP) {
            display();
        } else if (message instanceof SceneMessage) {
            start = ((SceneMessage) message).start;
            camera = ((SceneMessage) message).cam;
        } else if (message == Message.INIT) {
            initialize();
        }
    }
}