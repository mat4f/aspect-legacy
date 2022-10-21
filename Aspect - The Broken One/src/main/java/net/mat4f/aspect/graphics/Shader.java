package net.mat4f.aspect.graphics;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static net.mat4f.aspect.core.Assertions.asAssert;
import static net.mat4f.aspect.core.Logger.asInfo;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private         int     vertexID       ;
    private         int     fragmentID     ;
    private final   int     programID      ;

    private final Map<String, Integer> uniforms = new HashMap<>();

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programID,
                uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" +
                    uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public Shader() {
        programID = glCreateProgram();
        asAssert(programID != 0, "Unable to create Shader Program!");
    }

    public void createVertexShader(String shaderCode) {
        vertexID = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) {
        fragmentID = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int type) {
        asInfo("Loading Shader:\n" + shaderCode);

        int shaderID = glCreateShader(type);
        asAssert(shaderID != 0, "Unable to Create " + ((type == GL_VERTEX_SHADER) ? "Vertex" : ((type == GL_FRAGMENT_SHADER) ? "Fragment" : "Unknown") ) + " Shader!");

        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        asAssert(glGetShaderi(shaderID, GL_COMPILE_STATUS) != 0, "Error during Shader Compilation: " + glGetShaderInfoLog(shaderID));

        glAttachShader(programID, shaderID);

        return shaderID;

    }

    public void link() {
        glLinkProgram(programID);
        asAssert(glGetProgrami(programID, GL_LINK_STATUS) != 0, "Error during Program Linking: " + glGetProgramInfoLog(programID));

        if (vertexID != 0)   glDetachShader(programID, vertexID   );
        if (fragmentID != 0) glDetachShader(programID, fragmentID );

        glValidateProgram(programID);
        asAssert(glGetProgrami(programID, GL_VALIDATE_STATUS) != 0, "Error during Program Validation: " + glGetProgramInfoLog(programID));
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programID != 0) glDeleteProgram(programID);
    }

}
