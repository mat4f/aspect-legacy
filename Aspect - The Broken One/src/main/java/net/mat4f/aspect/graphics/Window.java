package net.mat4f.aspect.graphics;

import net.mat4f.aspect.objects.GameObject;
import net.mat4f.aspect.objects.Transformation;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static net.mat4f.aspect.core.Assertions.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

// Every Unused Method is public and can be accessed
// Outside the Internal Aspect Classes and Packages.
@SuppressWarnings("unused")
public class Window {

    private long              aspect_window_id              ;
    private final int         aspect_window_width           ;
    private final int         aspect_window_height          ;
    private final String      aspect_window_title           ;

    private boolean     aspect_window_use_vsync             = true        ;
    private boolean     aspect_window_allow_fullscreen      = true        ;
    private boolean     aspect_window_fullscreen_enabled    = false       ;
    private boolean     aspect_window_resizable             = true        ;

    private double      aspect_input_mouse_x                ;
    private double      aspect_input_mouse_y                ;
    private double      aspect_input_scroll_x               ;
    private double      aspect_input_scroll_y               ;

    private final boolean[]   aspect_input_keyboard_keys    =   new boolean[GLFW_KEY_LAST]          ;
    private final boolean[]   aspect_input_mouse_buttons    =   new boolean[GLFW_MOUSE_BUTTON_LAST] ;

    private boolean     aspect_window_should_close          = false ;

    public Window(String title, int width, int height) {
        this.aspect_window_id = 0L;
        this.aspect_window_width = width;
        this.aspect_window_height = height;
        this.aspect_window_title = title;
    }

    // This Method allows Custom Configurations for GLFW's
    // Window Hints, etc.
    public void asProperty(String property, boolean value) {
        switch (property) {
            case "vsync"            -> aspect_window_use_vsync          = value;
            case "fullscreen"       -> aspect_window_allow_fullscreen   = value;
            case "cur_fullscreen"   -> aspect_window_fullscreen_enabled = value;
            case "resizable"        -> aspect_window_resizable          = value;
        }
    }

    public void asInitialize() {

        // In case of Internal Errors, LWJGL will print to
        // The preset PrintStream. In this case, System.err
        // This is exactly the same as System.err.println();
        GLFWErrorCallback.createPrint(System.err).set();

        // Because Aspect and LWJGL require GLFW to be initialized,
        // this will Terminate the current Process whenever the
        // GLFW.glfwInit() method Fails. This ensures that GLFW is
        // Initialized at all Points beyond here.
        asAssertFatal(glfwInit(), "Unable to Initialize GLFW!");

        // The Following Few Steps will set the GLFW Window Properties
        // such as Resizability, Visibility and the Major and Minor
        // Versions of OpenGL ( Primarily used in Shaders ( '#version' ) ).
        // This already requires GLFW being Initialized.
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, (aspect_window_resizable) ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); //
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3); //  In GLSL This will be #version 330 core

        // The next Steps will actually create the Window.
        // Note that the window will not be visible by default.
        // The window will first be created and checked for errors
        // before being shown. Otherwise, the window could start
        // and disappear immediately after whenever there is an Error
        // creating the Window or Initializing OpenGL.

        aspect_window_id = glfwCreateWindow(aspect_window_width, aspect_window_height, aspect_window_title, NULL, NULL);

        // This line ensures that there were no Errors creating or Initializing the GLFW window.
        asAssertFatal(aspect_window_id != NULL, "Unable to create GLFW Window!");

        // The next Following Steps will set up some basic GLFW Callbacks. For Example,
        // Keyboard Callbacks, Mouse Callbacks and Window Callbacks.

        // Setting up GLFW Key Callbacks, this will trigger when a key is Pressed.
        glfwSetKeyCallback(aspect_window_id, (window, key, scancode, action, mods) -> this.aspect_input_keyboard_keys[key] = (action == GLFW_PRESS));

        // Setting up GLFW Mouse Button Callbacks, this will trigger when a mouse button is Pressed.
        glfwSetMouseButtonCallback(aspect_window_id, (window, button, action, mods) -> this.aspect_input_mouse_buttons[button] = (action == GLFW_PRESS));

        // Setting up GLFW Scroll Callbacks, this will trigger when you use the scroll wheel on your mouse.
        glfwSetScrollCallback(aspect_window_id, (window, x, y) -> {
            this.aspect_input_scroll_x += x;
            this.aspect_input_scroll_y += y;
        });

        // Setting up GLFW Mouse Movement Callbacks, this will trigger when the Cursor is moved.
        glfwSetCursorPosCallback(aspect_window_id, (window, x, y) -> {
            this.aspect_input_mouse_x = x;
            this.aspect_input_mouse_y = y;
        });

        // Set GLFW's current Context to OpenGL, this allows future Rendering and calls to OpenGL.
        glfwMakeContextCurrent(aspect_window_id);

        // Enable VSync ( if needed )
        if (aspect_window_use_vsync) glfwSwapInterval(1);

        // This line is critical for OpenGL to function properly.
        GL.createCapabilities();

        // Make the Window Visible to the User to allow Interactions.
        glfwShowWindow(aspect_window_id);

        glClearColor(0, 0, 0, 0);
        glEnable(GL_DEPTH_TEST);
    }

    public void asCleanup() {

        // This Terminates GLFW, Frees Memory and allows Demons ( e.g. Gradle )
        // to Terminate Successfully. This is not required but highly Recommended.
        glfwTerminate();

    }

    public void asTerminate() {

        this.aspect_window_should_close = true;

    }

    public boolean asShouldClose() {
        return this.aspect_window_should_close | glfwWindowShouldClose(aspect_window_id);
    }

    public void asUpdate() {
        glfwPollEvents();
    }

    public void asRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void asBufferSwap() {
        glfwSwapBuffers(aspect_window_id);
    }

    public boolean isKeyPressed(int key) {
        return aspect_input_keyboard_keys[key];
    }

    public boolean isMousePressed(int key) {
        return aspect_input_mouse_buttons[key];
    }

    public long getAspect_window_id() {
        return aspect_window_id;
    }

    public int getAspect_window_width() {
        return aspect_window_width;
    }

    public int getAspect_window_height() {
        return aspect_window_height;
    }

    public String getAspect_window_title() {
        return aspect_window_title;
    }

    public boolean isAspect_window_use_vsync() {
        return aspect_window_use_vsync;
    }

    public boolean isAspect_window_allow_fullscreen() {
        return aspect_window_allow_fullscreen;
    }

    public boolean isAspect_window_fullscreen_enabled() {
        return aspect_window_fullscreen_enabled;
    }

    public boolean isAspect_window_resizable() {
        return aspect_window_resizable;
    }

    public double getAspect_input_mouse_x() {
        return aspect_input_mouse_x;
    }

    public double getAspect_input_mouse_y() {
        return aspect_input_mouse_y;
    }

    public double getAspect_input_scroll_x() {
        return aspect_input_scroll_x;
    }

    public double getAspect_input_scroll_y() {
        return aspect_input_scroll_y;
    }

    public boolean[] getAspect_input_keyboard_keys() {
        return aspect_input_keyboard_keys;
    }

    public boolean[] getAspect_input_mouse_buttons() {
        return aspect_input_mouse_buttons;
    }

    public void render(Shader shader, GameObject object, Transformation transformation) {
        Matrix4f worldMatrix =
                transformation.getWorldMatrix(
                        object.getPosition(),
                        object.getRotation(),
                        object.getScale());
        shader.setUniform("worldMatrix", worldMatrix);
        // Render the mesh for this game item
        object.getMesh().render();
    }
}
