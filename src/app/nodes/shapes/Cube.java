package app.nodes.shapes;

import java.io.File;
import java.nio.FloatBuffer;

import app.nodes.shapes.Texture;
import org.lwjgl.BufferUtils;
import app.shader.Shader;
import app.vecmath.Color;
import app.vecmath.Vector;
import static app.nodes.shapes.Vertex.*;
import static app.vecmathimp.FactoryDefault.vecmath;

public class Cube extends Shape {
	// Width, depth and height of the cube divided by 2.
	private float w2 = 0.5f;
	private float h2 = 0.5f;
	private float d2 = 0.5f;

	//
	// 6 ------- 7
	// / | / |
	// 3 ------- 2 |
	// | | | |
	// | 5 -----|- 4
	// | / | /
	// 0 ------- 1
	//

	// The positions of the cube vertices.
	private Vector[] p = { vec(-w2, -h2, -d2), vec(w2, -h2, -d2),
			vec(w2, h2, -d2), vec(-w2, h2, -d2), vec(w2, -h2, d2),
			vec(-w2, -h2, d2), vec(-w2, h2, d2), vec(w2, h2, d2) };

	// The colors of the cube vertices.
	private Color[] c = { col(0, 0, 0), col(1, 0, 0), col(1, 1, 0),
			col(0, 1, 0), col(1, 0, 1), col(0, 0, 1), col(0, 1, 1),
			col(1, 1, 1) };

	private Vector[] n;

	private Vector[] t = { vec(0.0f, 0.0f, 0.0f), vec(1.0f, 0.0f, 0.0f),
			vec(1.0f, 1.0f, 0.0f), vec(0.0f, 1.0f, 0.0f) };

	// Vertices combine position and color information. Every four vertices
	// define
	// one side of the cube.
	Vertex[] vertices = {
	// front
			v(p[0], c[0]), v(p[1], c[1]), v(p[2], c[2]), v(p[3], c[3]),
			// back
			v(p[4], c[4]), v(p[5], c[5]), v(p[6], c[6]), v(p[7], c[7]),
			// right
			v(p[1], c[1]), v(p[4], c[4]), v(p[7], c[7]), v(p[2], c[2]),
			// top
			v(p[3], c[3]), v(p[2], c[2]), v(p[7], c[7]), v(p[6], c[6]),
			// left
			v(p[5], c[5]), v(p[0], c[0]), v(p[3], c[3]), v(p[6], c[6]),
			// bottom
			v(p[5], c[5]), v(p[4], c[4]), v(p[1], c[1]), v(p[0], c[0]) };

	public Cube(String id, Shader shader) {
		super(id, shader);
		buffBasic();
	}

	public Cube(String id, Shader shader, float w, float h, float d) {
		super(id, shader);
		w2 = w;
		h2 = h;
		d2 = d;
		defPointNew();
		buffBasic();
		buffNormal();
	}
	
	public Cube(String id, Shader shader, float w, float h, float d, String sourceTex) {
		super(id, shader);
		w2 = w;
		h2 = h;
		d2 = d;
		defPointNew();
		tex = new Texture(new File(sourceTex));
		buffBasic();
		buffNormal();
		buffTex();
	}

	private void buffBasic() {
		// Prepare the vertex data arrays.
		// Compile vertex data into a Java Buffer data structures that can be
		// passed to the OpenGL API efficently.
		positionData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.vectorSize());
		colorData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.colorSize());

		for (Vertex v : vertices) {
			positionData.put(v.position.asArray());
			colorData.put(v.color.asArray());
		}
		positionData.rewind();
		colorData.rewind();
		setSuperVert();
	}

	private void buffNormal() {
		normalData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.vectorSize());
		for (Vertex v : vertices) {
			normalData.put(v.normal.asArray());
		}
		normalData.rewind();
		setSuperVert();
	}

	private void buffTex() {
		textureData = BufferUtils.createFloatBuffer(vertices.length
				* vecmath.vectorSize());
		for (Vertex v : vertices) {
			textureData.put(v.normal.asArray());
		}
		textureData.rewind();
		setSuperVert();
	}
	
	private void setSuperVert(){
		super.vertices=vertices;
	}

	private void defPointNew() {
		Vector[] pn = { vec(-w2, -h2, d2), vec(w2, -h2, d2), vec(w2, h2, d2),
				vec(-w2, h2, d2), vec(w2, -h2, -d2), vec(-w2, -h2, -d2),
				vec(-w2, h2, -d2), vec(w2, h2, -d2) };
		p=pn;

		Vector[] norm = {
				((p[2].sub(p[1]).cross(p[0].sub(p[1]))).add(p[3].sub(p[0])
						.cross(p[5].sub(p[0]))).add(p[1].sub(p[4]).cross(
						p[5].sub(p[4])))),
				(p[2].sub(p[1]).cross(p[0].sub(p[1]))).add(
						p[7].sub(p[4]).cross(p[1].sub(p[4]))).add(
						p[1].sub(p[4]).cross(p[5].sub(p[4]))),
				(p[2].sub(p[1]).cross(p[0].sub(p[1]))).add(
						p[7].sub(p[4]).cross(p[1].sub(p[4]))).add(
						p[7].sub(p[2]).cross(p[3].sub(p[2]))),
				(p[2].sub(p[1]).cross(p[0].sub(p[1]))).add(
						p[7].sub(p[2]).cross(p[3].sub(p[2]))).add(
						p[3].sub(p[0]).cross(p[5].sub(p[0]))),
				(p[7].sub(p[4]).cross(p[1].sub(p[4]))).add(
						p[1].sub(p[4]).cross(p[5].sub(p[4]))).add(
						p[6].sub(p[5]).cross(p[4].sub(p[5]))),
				(p[1].sub(p[4]).cross(p[5].sub(p[4]))).add(
						p[3].sub(p[0]).cross(p[5].sub(p[0]))).add(
						p[6].sub(p[5]).cross(p[4].sub(p[5]))),
				(p[3].sub(p[0]).cross(p[5].sub(p[0]))).add(
						p[6].sub(p[5]).cross(p[4].sub(p[5]))).add(
						p[7].sub(p[2]).cross(p[3].sub(p[2]))),
				(p[7].sub(p[2]).cross(p[3].sub(p[2]))).add(
						p[6].sub(p[5]).cross(p[4].sub(p[5]))).add(
						p[7].sub(p[4]).cross(p[1].sub(p[4]))), };
		n=norm;

		Vertex[] vert = {
				// front
				new Vertex(p[0], c[1], n[0], t[3]),
				new Vertex(p[1], c[1], n[1], t[2]),
				new Vertex(p[2], c[1], n[2], t[1]),
				new Vertex(p[3], c[1], n[3], t[0]),
				// back
				new Vertex(p[4], c[1], n[4], t[2]),
				new Vertex(p[5], c[1], n[5], t[3]),
				new Vertex(p[6], c[1], n[6], t[0]),
				new Vertex(p[7], c[1], n[7], t[1]),
				// right
				new Vertex(p[1], c[1], n[1], t[3]),
				new Vertex(p[4], c[1], n[4], t[2]),
				new Vertex(p[7], c[1], n[7], t[1]),
				new Vertex(p[2], c[1], n[2], t[0]),
				// top
				new Vertex(p[3], c[1], n[3], t[3]),
				new Vertex(p[2], c[1], n[3], t[2]),
				new Vertex(p[7], c[1], n[7], t[1]),
				new Vertex(p[6], c[1], n[6], t[0]),
				// left
				new Vertex(p[5], c[1], n[5], t[3]),
				new Vertex(p[0], c[1], n[0], t[2]),
				new Vertex(p[3], c[1], n[3], t[1]),
				new Vertex(p[6], c[1], n[6], t[0]),
				// bottom
				new Vertex(p[5], c[1], n[5], t[0]),
				new Vertex(p[4], c[1], n[4], t[1]),
				new Vertex(p[1], c[1], n[1], t[3]),
				new Vertex(p[0], c[1], n[0], t[2]) };
		vertices = vert;
	}
}
