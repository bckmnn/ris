package app.messages;

import app.shader.Shader;

public class RendererInitialized {

	public Shader shader;
	
	public RendererInitialized(Shader shader) {
		this.shader = shader;
	}
}
