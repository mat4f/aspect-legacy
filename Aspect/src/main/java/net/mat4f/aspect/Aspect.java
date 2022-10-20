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

    public static void main(String[] args) throws IOException {
        float[] vertices = new float[]{
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f,
        };

        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2,
        };

        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        Mesh mesh = new Mesh(vertices, colours, indices);

        Window window = new Window("Aspect Engine v2", 1080, 720);
        window.asInitialize();
        mesh.create();

        Shader shader = new Shader();
        shader.createVertexShader(Aspect.loadResource("shaders/vertex.glsl"));
        shader.createFragmentShader(Aspect.loadResource("shaders/fragment.glsl"));
        shader.link();

        while ( !window.asShouldClose() ) {
            window.asRender();

            shader.bind();

            mesh.render();
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
