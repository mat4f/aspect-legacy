package net.mat4f.aspect;

/*  Planned Features
 *
 *  Modules:
 *
 *  Done module 'core'
 *  ToDo module 'graphics'
 *
 *  Classes:
 *
 *  Done class 'Logger'
 *  Done class 'Assertions'
 *
 *  ToDo class 'Mesh'
 *  ToDo class 'TexturedMesh'
 *
 *  ToDo class 'Shader'
 *  ToDo class 'Aspect'
 *  ToDo class 'Window'
 *  ToDo class 'WindowManager'
 *
 *  ToDo class 'Renderer'
 *
 *
 */

import net.mat4f.aspect.graphics.Mesh;
import net.mat4f.aspect.graphics.Shader;
import net.mat4f.aspect.graphics.Window;
import net.mat4f.aspect.objects.GameObject;
import net.mat4f.aspect.objects.Transformation;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Aspect {
    public static final int ASPECT_TRUE = 1, ASPECT_FALSE = 1, ASPECT_UNDEFINED = -1;
    public static final String ASPECT_NONE = "@{{aspect-preset-value-none}}";

    public static String loadResource(String name) throws IOException {
        return Files.readString(Paths.get(new File("C:\\Users\\carl-\\Documents\\engine\\Aspect\\src\\main\\resources", name).toURI()));
    }

    public static Matrix4f getProjectionMatrix(int width, int height, float fov, float z_near, float z_far) {
        float aspectRatio = (float) width / height;
        return new Matrix4f().perspective(fov, aspectRatio, z_near, z_far);
    }

    public static Transformation transformation = new Transformation();
    public static void main(String[] args) throws Exception {

        Matrix4f projectionMatrix = getProjectionMatrix(1080, 720, (float) Math.toRadians(60.0f), 0.01f, 1000.0f);

        float z_off = -2;
        float[] positions = new float[] {
                // VO
                -0.5f,  0.5f,  0.5f + z_off,
                // V1
                -0.5f, -0.5f,  0.5f + z_off,
                // V2
                0.5f, -0.5f,  0.5f + z_off,
                // V3
                0.5f,  0.5f,  0.5f + z_off,
                // V4
                -0.5f,  0.5f, -0.5f + z_off,
                // V5
                0.5f,  0.5f, -0.5f + z_off,
                // V6
                -0.5f, -0.5f, -0.5f + z_off,
                // V7
                0.5f, -0.5f, -0.5f + z_off,
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

        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        Mesh mesh = new Mesh(positions, colours, indices);
        GameObject dummy = new GameObject(mesh);

        Window window = new Window("Aspect Engine v2", 1080, 720);
        window.asInitialize();
        mesh.create();

        Shader shader = new Shader();
        shader.createVertexShader(Aspect.loadResource("shaders/vertex.glsl"));
        shader.createFragmentShader(Aspect.loadResource("shaders/fragment.glsl"));
        shader.link();

        shader.createUniform("projection");
        shader.createUniform("worldMatrix");

        while ( !window.asShouldClose() ) {
            window.asRender();

            shader.bind();
            shader.setUniform("projection", projectionMatrix);

            window.render(shader, dummy, transformation);
            window.asBufferSwap();

            shader.unbind();

            window.asUpdate();
        }
        window.asTerminate  () ;
        window.asCleanup    () ;
        mesh.cleanup();
        shader.cleanup();
    }

}
