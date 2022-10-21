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

}
