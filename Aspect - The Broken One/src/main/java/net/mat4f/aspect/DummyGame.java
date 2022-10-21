package net.mat4f.aspect;

import net.mat4f.aspect.Aspect;
import net.mat4f.aspect.graphics.Mesh;
import net.mat4f.aspect.graphics.Shader;
import net.mat4f.aspect.graphics.Window;
import net.mat4f.aspect.objects.GameObject;

import java.io.IOException;

public class DummyGame {

    public static void main(String[] args) throws Exception {
        float[] positions = new float[] {
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                0.5f, -0.5f,  0.5f,
                0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
                0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[] {
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };

        Mesh mesh = new Mesh(positions, colors, indices);
        GameObject dummy = new GameObject(mesh);


        Window window = new Window("Aspect / Dummy Game", 1080, 720);
        window.asInitialize();

        Shader shader = new Shader();
        shader.createVertexShader(Aspect.loadResource("/shaders/vertex.glsl"));
        shader.createFragmentShader(Aspect.loadResource("/shaders/fragment.glsl"));
        shader.link();

        shader.createUniform("worldMatrix");

        while ( !window.asShouldClose() ) {

            shader.bind();

            window.asUpdate();
            window.asRender();

            window.render(shader, dummy, Aspect.transformation);

            window.asBufferSwap();

            shader.unbind();

            Thread.sleep(2000);
        }

        window.asTerminate();
        window.asCleanup();

    }

}
