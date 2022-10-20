package net.mat4f.aspect.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Mesh {

    private int vaoID, vboID, iboID, cboID;

    private float[] vertices;
    private int[]   indices;
    private float[] colours;
    public Mesh(float[] vertices, float[] colours, int[] indices) {

        this.vertices = vertices;
        this.indices = indices;
        this.colours = colours;

    }

    public void create() {
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        iboID = glGenBuffers();
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);


        cboID = glGenBuffers();
        FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(colours.length);
        colourBuffer.put(colours).flip();
        glBindBuffer(GL_ARRAY_BUFFER, cboID);
        glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    public void render() {

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the vertices using Indices
        glDrawElements(GL_TRIANGLES, vertices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboID);
        glDeleteBuffers(iboID);
        glDeleteBuffers(cboID);

        glDeleteVertexArrays(vaoID);
    }
}
