/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - OpenGL Utilities
 *
 * Description:
 * OpenGL ES utilities and helpers for game development.
 * Provides common OpenGL operations, shader management, and rendering optimizations.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef OPENGL_UTILS_H
#define OPENGL_UTILS_H

#include "AppDimensGames.h"
#include <string>
#include <vector>
#include <unordered_map>

// OpenGL ES versions
enum class OpenGLVersion {
    ES_2_0,
    ES_3_0,
    ES_3_1,
    ES_3_2
};

// Shader types
enum class ShaderType {
    VERTEX,
    FRAGMENT,
    COMPUTE
};

// Texture formats
enum class TextureFormat {
    RGB,
    RGBA,
    ALPHA,
    LUMINANCE,
    LUMINANCE_ALPHA,
    DEPTH_COMPONENT,
    DEPTH_STENCIL
};

// Primitive types
enum class PrimitiveType {
    POINTS,
    LINES,
    LINE_STRIP,
    LINE_LOOP,
    TRIANGLES,
    TRIANGLE_STRIP,
    TRIANGLE_FAN
};

// Buffer types
enum class BufferType {
    VERTEX,
    INDEX,
    UNIFORM,
    TEXTURE
};

// Shader program
struct ShaderProgram {
    GLuint programId;
    GLuint vertexShaderId;
    GLuint fragmentShaderId;
    std::string vertexSource;
    std::string fragmentSource;
    bool isCompiled;
    bool isLinked;
    
    ShaderProgram() : programId(0), vertexShaderId(0), fragmentShaderId(0),
                     isCompiled(false), isLinked(false) {}
};

// Texture information
struct TextureInfo {
    GLuint textureId;
    int width;
    int height;
    TextureFormat format;
    bool hasMipmaps;
    bool isCompressed;
    
    TextureInfo() : textureId(0), width(0), height(0), format(TextureFormat::RGBA),
                   hasMipmaps(false), isCompressed(false) {}
};

// Vertex buffer
struct VertexBuffer {
    GLuint bufferId;
    size_t size;
    size_t stride;
    BufferType type;
    bool isDynamic;
    
    VertexBuffer() : bufferId(0), size(0), stride(0), type(BufferType::VERTEX),
                    isDynamic(false) {}
};

// Render state
struct RenderState {
    bool depthTest;
    bool blending;
    bool culling;
    GLenum cullFace;
    GLenum blendSrc;
    GLenum blendDst;
    GLenum depthFunc;
    
    RenderState() : depthTest(true), blending(false), culling(true),
                   cullFace(GL_BACK), blendSrc(GL_SRC_ALPHA), blendDst(GL_ONE_MINUS_SRC_ALPHA),
                   depthFunc(GL_LESS) {}
};

class OpenGLUtils {
public:
    OpenGLUtils();
    ~OpenGLUtils();
    
    // Initialize OpenGL context
    bool initialize();
    void shutdown();
    
    // OpenGL version and capabilities
    OpenGLVersion getOpenGLVersion() const;
    bool isExtensionSupported(const std::string& extension) const;
    std::vector<std::string> getSupportedExtensions() const;
    
    // Shader management
    GLuint createShader(ShaderType type, const std::string& source);
    bool compileShader(GLuint shaderId);
    void deleteShader(GLuint shaderId);
    
    GLuint createShaderProgram(const std::string& vertexSource, const std::string& fragmentSource);
    bool linkShaderProgram(GLuint programId);
    void deleteShaderProgram(GLuint programId);
    
    // Shader program management
    ShaderProgram* createShaderProgram(const std::string& name, 
                                      const std::string& vertexSource, 
                                      const std::string& fragmentSource);
    ShaderProgram* getShaderProgram(const std::string& name);
    void deleteShaderProgram(const std::string& name);
    void useShaderProgram(const std::string& name);
    void useShaderProgram(GLuint programId);
    
    // Uniform management
    void setUniform1f(GLuint programId, const std::string& name, float value);
    void setUniform2f(GLuint programId, const std::string& name, float x, float y);
    void setUniform3f(GLuint programId, const std::string& name, float x, float y, float z);
    void setUniform4f(GLuint programId, const std::string& name, float x, float y, float z, float w);
    void setUniform1i(GLuint programId, const std::string& name, int value);
    void setUniformMatrix4fv(GLuint programId, const std::string& name, const float* matrix);
    
    // Texture management
    GLuint createTexture(int width, int height, TextureFormat format, const void* data = nullptr);
    GLuint createTextureFromFile(const std::string& filename);
    void deleteTexture(GLuint textureId);
    void bindTexture(GLuint textureId, int unit = 0);
    void unbindTexture(int unit = 0);
    
    // Texture utilities
    TextureInfo* createTextureInfo(const std::string& name, int width, int height, 
                                  TextureFormat format, const void* data = nullptr);
    TextureInfo* getTextureInfo(const std::string& name);
    void deleteTextureInfo(const std::string& name);
    
    // Buffer management
    GLuint createBuffer(BufferType type, size_t size, const void* data = nullptr, bool dynamic = false);
    void deleteBuffer(GLuint bufferId);
    void bindBuffer(BufferType type, GLuint bufferId);
    void unbindBuffer(BufferType type);
    void updateBuffer(GLuint bufferId, size_t offset, size_t size, const void* data);
    
    // Vertex buffer utilities
    VertexBuffer* createVertexBuffer(const std::string& name, size_t size, 
                                   const void* data = nullptr, bool dynamic = false);
    VertexBuffer* getVertexBuffer(const std::string& name);
    void deleteVertexBuffer(const std::string& name);
    void bindVertexBuffer(const std::string& name);
    
    // Rendering functions
    void clear(float r = 0.0f, float g = 0.0f, float b = 0.0f, float a = 1.0f);
    void setViewport(int x, int y, int width, int height);
    void setViewport(const Rectangle& viewport);
    Rectangle getViewport() const;
    
    void drawArrays(PrimitiveType type, int first, int count);
    void drawElements(PrimitiveType type, int count, GLenum indexType, const void* indices);
    
    // Render state management
    void setRenderState(const RenderState& state);
    RenderState getRenderState() const;
    void enableDepthTest(bool enable);
    void enableBlending(bool enable);
    void enableCulling(bool enable);
    void setBlendFunc(GLenum src, GLenum dst);
    void setCullFace(GLenum face);
    void setDepthFunc(GLenum func);
    
    // Matrix operations
    void setProjectionMatrix(const float* matrix);
    void setViewMatrix(const float* matrix);
    void setModelMatrix(const float* matrix);
    void setMVP(const float* model, const float* view, const float* projection);
    
    // Utility matrices
    void createOrthographicMatrix(float* matrix, float left, float right, 
                                 float bottom, float top, float near, float far);
    void createPerspectiveMatrix(float* matrix, float fov, float aspect, 
                                float near, float far);
    void createLookAtMatrix(float* matrix, const Vector2D& eye, const Vector2D& center, 
                           const Vector2D& up);
    
    // Error checking
    bool checkGLError(const std::string& operation = "");
    std::string getGLErrorString(GLenum error);
    void logGLInfo();
    
    // Performance monitoring
    void beginPerformanceQuery(const std::string& name);
    void endPerformanceQuery(const std::string& name);
    float getPerformanceQueryResult(const std::string& name);
    
    // Memory management
    size_t getTotalTextureMemory() const;
    size_t getTotalBufferMemory() const;
    void logMemoryUsage() const;
    
    // Debug utilities
    void drawDebugGrid(float spacing = 1.0f, int count = 10);
    void drawDebugAxis(float length = 1.0f);
    void drawDebugRectangle(const Rectangle& rect, float r = 1.0f, float g = 1.0f, float b = 1.0f);
    
private:
    OpenGLVersion openGLVersion;
    RenderState currentRenderState;
    Rectangle currentViewport;
    
    // Resource management
    std::unordered_map<std::string, ShaderProgram*> shaderPrograms;
    std::unordered_map<std::string, TextureInfo*> textures;
    std::unordered_map<std::string, VertexBuffer*> vertexBuffers;
    
    // Performance tracking
    std::unordered_map<std::string, GLuint> performanceQueries;
    std::unordered_map<std::string, float> performanceResults;
    
    // Memory tracking
    size_t totalTextureMemory;
    size_t totalBufferMemory;
    
    // Internal utilities
    std::string loadShaderSource(const std::string& filename);
    bool validateShader(GLuint shaderId);
    bool validateProgram(GLuint programId);
    GLenum getGLPrimitiveType(PrimitiveType type) const;
    GLenum getGLTextureFormat(TextureFormat format) const;
    GLenum getGLBufferType(BufferType type) const;
    
    // Default shaders
    std::string getDefaultVertexShader() const;
    std::string getDefaultFragmentShader() const;
    std::string getDefaultTexturedVertexShader() const;
    std::string getDefaultTexturedFragmentShader() const;
};

#endif // OPENGL_UTILS_H
