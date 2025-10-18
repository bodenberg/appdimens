/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - OpenGL Utils Implementation
 *
 * Description:
 * Implementation of OpenGL ES utilities and helpers for game development.
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

#include "OpenGLUtils.h"
#include <fstream>
#include <sstream>

OpenGLUtils::OpenGLUtils() {
    LOGI("OpenGLUtils constructor called");
    
    openGLVersion = OpenGLVersion::ES_2_0;
    totalTextureMemory = 0;
    totalBufferMemory = 0;
}

OpenGLUtils::~OpenGLUtils() {
    LOGI("OpenGLUtils destructor called");
    shutdown();
}

bool OpenGLUtils::initialize() {
    LOGI("OpenGLUtils initializing...");
    
    // Get OpenGL version
    const char* version = reinterpret_cast<const char*>(glGetString(GL_VERSION));
    if (version) {
        LOGI("OpenGL Version: %s", version);
        
        // Determine OpenGL ES version
        if (strstr(version, "OpenGL ES 3.2")) {
            openGLVersion = OpenGLVersion::ES_3_2;
        } else if (strstr(version, "OpenGL ES 3.1")) {
            openGLVersion = OpenGLVersion::ES_3_1;
        } else if (strstr(version, "OpenGL ES 3.0")) {
            openGLVersion = OpenGLVersion::ES_3_0;
        } else {
            openGLVersion = OpenGLVersion::ES_2_0;
        }
    }
    
    // Initialize render state
    currentRenderState.depthTest = true;
    currentRenderState.blending = false;
    currentRenderState.culling = true;
    currentRenderState.cullFace = GL_BACK;
    currentRenderState.blendSrc = GL_SRC_ALPHA;
    currentRenderState.blendDst = GL_ONE_MINUS_SRC_ALPHA;
    currentRenderState.depthFunc = GL_LESS;
    
    // Set initial viewport
    currentViewport = Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
    
    LOGI("OpenGLUtils initialized successfully");
    return true;
}

void OpenGLUtils::shutdown() {
    LOGI("OpenGLUtils shutting down...");
    
    // Clean up resources
    for (auto& pair : shaderPrograms) {
        delete pair.second;
    }
    shaderPrograms.clear();
    
    for (auto& pair : textures) {
        delete pair.second;
    }
    textures.clear();
    
    for (auto& pair : vertexBuffers) {
        delete pair.second;
    }
    vertexBuffers.clear();
    
    LOGI("OpenGLUtils shutdown complete");
}

OpenGLVersion OpenGLUtils::getOpenGLVersion() const {
    return openGLVersion;
}

bool OpenGLUtils::isExtensionSupported(const std::string& extension) const {
    // This is a simplified implementation
    // In a real implementation, you would query the actual supported extensions
    return true;
}

std::vector<std::string> OpenGLUtils::getSupportedExtensions() const {
    std::vector<std::string> extensions;
    // This is a simplified implementation
    // In a real implementation, you would query the actual supported extensions
    return extensions;
}

GLuint OpenGLUtils::createShader(ShaderType type, const std::string& source) {
    GLenum glType = (type == ShaderType::VERTEX) ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER;
    GLuint shader = glCreateShader(glType);
    
    const char* sourceCStr = source.c_str();
    glShaderSource(shader, 1, &sourceCStr, nullptr);
    
    return shader;
}

bool OpenGLUtils::compileShader(GLuint shaderId) {
    glCompileShader(shaderId);
    
    GLint success;
    glGetShaderiv(shaderId, GL_COMPILE_STATUS, &success);
    
    if (!success) {
        GLchar infoLog[512];
        glGetShaderInfoLog(shaderId, 512, nullptr, infoLog);
        LOGE("Shader compilation failed: %s", infoLog);
        return false;
    }
    
    return true;
}

void OpenGLUtils::deleteShader(GLuint shaderId) {
    glDeleteShader(shaderId);
}

GLuint OpenGLUtils::createShaderProgram(const std::string& vertexSource, const std::string& fragmentSource) {
    GLuint vertexShader = createShader(ShaderType::VERTEX, vertexSource);
    GLuint fragmentShader = createShader(ShaderType::FRAGMENT, fragmentSource);
    
    if (!compileShader(vertexShader) || !compileShader(fragmentShader)) {
        deleteShader(vertexShader);
        deleteShader(fragmentShader);
        return 0;
    }
    
    GLuint program = glCreateProgram();
    glAttachShader(program, vertexShader);
    glAttachShader(program, fragmentShader);
    glLinkProgram(program);
    
    GLint success;
    glGetProgramiv(program, GL_LINK_STATUS, &success);
    
    if (!success) {
        GLchar infoLog[512];
        glGetProgramInfoLog(program, 512, nullptr, infoLog);
        LOGE("Shader program linking failed: %s", infoLog);
        
        glDeleteProgram(program);
        deleteShader(vertexShader);
        deleteShader(fragmentShader);
        return 0;
    }
    
    deleteShader(vertexShader);
    deleteShader(fragmentShader);
    
    return program;
}

bool OpenGLUtils::linkShaderProgram(GLuint programId) {
    glLinkProgram(programId);
    
    GLint success;
    glGetProgramiv(programId, GL_LINK_STATUS, &success);
    
    if (!success) {
        GLchar infoLog[512];
        glGetProgramInfoLog(programId, 512, nullptr, infoLog);
        LOGE("Shader program linking failed: %s", infoLog);
        return false;
    }
    
    return true;
}

void OpenGLUtils::deleteShaderProgram(GLuint programId) {
    glDeleteProgram(programId);
}

ShaderProgram* OpenGLUtils::createShaderProgram(const std::string& name, 
                                               const std::string& vertexSource, 
                                               const std::string& fragmentSource) {
    ShaderProgram* program = new ShaderProgram();
    program->vertexSource = vertexSource;
    program->fragmentSource = fragmentSource;
    
    program->vertexShaderId = createShader(ShaderType::VERTEX, vertexSource);
    program->fragmentShaderId = createShader(ShaderType::FRAGMENT, fragmentSource);
    
    if (!compileShader(program->vertexShaderId) || !compileShader(program->fragmentShaderId)) {
        delete program;
        return nullptr;
    }
    
    program->programId = glCreateProgram();
    glAttachShader(program->programId, program->vertexShaderId);
    glAttachShader(program->programId, program->fragmentShaderId);
    
    if (linkShaderProgram(program->programId)) {
        program->isCompiled = true;
        program->isLinked = true;
        shaderPrograms[name] = program;
        return program;
    } else {
        delete program;
        return nullptr;
    }
}

ShaderProgram* OpenGLUtils::getShaderProgram(const std::string& name) {
    auto it = shaderPrograms.find(name);
    return (it != shaderPrograms.end()) ? it->second : nullptr;
}

void OpenGLUtils::deleteShaderProgram(const std::string& name) {
    auto it = shaderPrograms.find(name);
    if (it != shaderPrograms.end()) {
        delete it->second;
        shaderPrograms.erase(it);
    }
}

void OpenGLUtils::useShaderProgram(const std::string& name) {
    ShaderProgram* program = getShaderProgram(name);
    if (program) {
        glUseProgram(program->programId);
    }
}

void OpenGLUtils::useShaderProgram(GLuint programId) {
    glUseProgram(programId);
}

void OpenGLUtils::setUniform1f(GLuint programId, const std::string& name, float value) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniform1f(location, value);
    }
}

void OpenGLUtils::setUniform2f(GLuint programId, const std::string& name, float x, float y) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniform2f(location, x, y);
    }
}

void OpenGLUtils::setUniform3f(GLuint programId, const std::string& name, float x, float y, float z) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniform3f(location, x, y, z);
    }
}

void OpenGLUtils::setUniform4f(GLuint programId, const std::string& name, float x, float y, float z, float w) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniform4f(location, x, y, z, w);
    }
}

void OpenGLUtils::setUniform1i(GLuint programId, const std::string& name, int value) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniform1i(location, value);
    }
}

void OpenGLUtils::setUniformMatrix4fv(GLuint programId, const std::string& name, const float* matrix) {
    GLint location = glGetUniformLocation(programId, name.c_str());
    if (location != -1) {
        glUniformMatrix4fv(location, 1, GL_FALSE, matrix);
    }
}

GLuint OpenGLUtils::createTexture(int width, int height, TextureFormat format, const void* data) {
    GLuint textureId;
    glGenTextures(1, &textureId);
    glBindTexture(GL_TEXTURE_2D, textureId);
    
    GLenum internalFormat, pixelFormat, pixelType;
    switch (format) {
        case TextureFormat::RGB:
            internalFormat = GL_RGB;
            pixelFormat = GL_RGB;
            pixelType = GL_UNSIGNED_BYTE;
            break;
        case TextureFormat::RGBA:
            internalFormat = GL_RGBA;
            pixelFormat = GL_RGBA;
            pixelType = GL_UNSIGNED_BYTE;
            break;
        case TextureFormat::ALPHA:
            internalFormat = GL_ALPHA;
            pixelFormat = GL_ALPHA;
            pixelType = GL_UNSIGNED_BYTE;
            break;
        default:
            internalFormat = GL_RGBA;
            pixelFormat = GL_RGBA;
            pixelType = GL_UNSIGNED_BYTE;
            break;
    }
    
    glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, pixelFormat, pixelType, data);
    
    // Set default texture parameters
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    
    // Calculate memory usage
    int bytesPerPixel = (format == TextureFormat::RGB) ? 3 : 4;
    totalTextureMemory += width * height * bytesPerPixel;
    
    return textureId;
}

GLuint OpenGLUtils::createTextureFromFile(const std::string& filename) {
    // This is a simplified implementation
    // In a real implementation, you would load the texture from file
    LOGI("Creating texture from file: %s", filename.c_str());
    return 0;
}

void OpenGLUtils::deleteTexture(GLuint textureId) {
    glDeleteTextures(1, &textureId);
}

void OpenGLUtils::bindTexture(GLuint textureId, int unit) {
    glActiveTexture(GL_TEXTURE0 + unit);
    glBindTexture(GL_TEXTURE_2D, textureId);
}

void OpenGLUtils::unbindTexture(int unit) {
    glActiveTexture(GL_TEXTURE0 + unit);
    glBindTexture(GL_TEXTURE_2D, 0);
}

TextureInfo* OpenGLUtils::createTextureInfo(const std::string& name, int width, int height, 
                                           TextureFormat format, const void* data) {
    GLuint textureId = createTexture(width, height, format, data);
    if (textureId == 0) {
        return nullptr;
    }
    
    TextureInfo* info = new TextureInfo();
    info->textureId = textureId;
    info->width = width;
    info->height = height;
    info->format = format;
    info->hasMipmaps = false;
    info->isCompressed = false;
    
    textures[name] = info;
    return info;
}

TextureInfo* OpenGLUtils::getTextureInfo(const std::string& name) {
    auto it = textures.find(name);
    return (it != textures.end()) ? it->second : nullptr;
}

void OpenGLUtils::deleteTextureInfo(const std::string& name) {
    auto it = textures.find(name);
    if (it != textures.end()) {
        deleteTexture(it->second->textureId);
        delete it->second;
        textures.erase(it);
    }
}

GLuint OpenGLUtils::createBuffer(BufferType type, size_t size, const void* data, bool dynamic) {
    GLuint bufferId;
    glGenBuffers(1, &bufferId);
    
    GLenum glType = getGLBufferType(type);
    glBindBuffer(glType, bufferId);
    
    GLenum usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
    glBufferData(glType, size, data, usage);
    
    totalBufferMemory += size;
    
    return bufferId;
}

void OpenGLUtils::deleteBuffer(GLuint bufferId) {
    glDeleteBuffers(1, &bufferId);
}

void OpenGLUtils::bindBuffer(BufferType type, GLuint bufferId) {
    GLenum glType = getGLBufferType(type);
    glBindBuffer(glType, bufferId);
}

void OpenGLUtils::unbindBuffer(BufferType type) {
    GLenum glType = getGLBufferType(type);
    glBindBuffer(glType, 0);
}

void OpenGLUtils::updateBuffer(GLuint bufferId, size_t offset, size_t size, const void* data) {
    glBufferSubData(GL_ARRAY_BUFFER, offset, size, data);
}

VertexBuffer* OpenGLUtils::createVertexBuffer(const std::string& name, size_t size, 
                                            const void* data, bool dynamic) {
    GLuint bufferId = createBuffer(BufferType::VERTEX, size, data, dynamic);
    if (bufferId == 0) {
        return nullptr;
    }
    
    VertexBuffer* buffer = new VertexBuffer();
    buffer->bufferId = bufferId;
    buffer->size = size;
    buffer->stride = 0; // Will be set when used
    buffer->type = BufferType::VERTEX;
    buffer->isDynamic = dynamic;
    
    vertexBuffers[name] = buffer;
    return buffer;
}

VertexBuffer* OpenGLUtils::getVertexBuffer(const std::string& name) {
    auto it = vertexBuffers.find(name);
    return (it != vertexBuffers.end()) ? it->second : nullptr;
}

void OpenGLUtils::deleteVertexBuffer(const std::string& name) {
    auto it = vertexBuffers.find(name);
    if (it != vertexBuffers.end()) {
        deleteBuffer(it->second->bufferId);
        delete it->second;
        vertexBuffers.erase(it);
    }
}

void OpenGLUtils::bindVertexBuffer(const std::string& name) {
    VertexBuffer* buffer = getVertexBuffer(name);
    if (buffer) {
        bindBuffer(buffer->type, buffer->bufferId);
    }
}

void OpenGLUtils::clear(float r, float g, float b, float a) {
    glClearColor(r, g, b, a);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

void OpenGLUtils::setViewport(int x, int y, int width, int height) {
    glViewport(x, y, width, height);
    currentViewport = Rectangle(static_cast<float>(x), static_cast<float>(y), 
                              static_cast<float>(width), static_cast<float>(height));
}

void OpenGLUtils::setViewport(const Rectangle& viewport) {
    setViewport(static_cast<int>(viewport.x), static_cast<int>(viewport.y),
                static_cast<int>(viewport.width), static_cast<int>(viewport.height));
}

Rectangle OpenGLUtils::getViewport() const {
    return currentViewport;
}

void OpenGLUtils::drawArrays(PrimitiveType type, int first, int count) {
    GLenum glType = getGLPrimitiveType(type);
    glDrawArrays(glType, first, count);
}

void OpenGLUtils::drawElements(PrimitiveType type, int count, GLenum indexType, const void* indices) {
    GLenum glType = getGLPrimitiveType(type);
    glDrawElements(glType, count, indexType, indices);
}

void OpenGLUtils::setRenderState(const RenderState& state) {
    currentRenderState = state;
    
    if (state.depthTest) {
        glEnable(GL_DEPTH_TEST);
    } else {
        glDisable(GL_DEPTH_TEST);
    }
    
    if (state.blending) {
        glEnable(GL_BLEND);
        glBlendFunc(state.blendSrc, state.blendDst);
    } else {
        glDisable(GL_BLEND);
    }
    
    if (state.culling) {
        glEnable(GL_CULL_FACE);
        glCullFace(state.cullFace);
    } else {
        glDisable(GL_CULL_FACE);
    }
    
    glDepthFunc(state.depthFunc);
}

RenderState OpenGLUtils::getRenderState() const {
    return currentRenderState;
}

void OpenGLUtils::enableDepthTest(bool enable) {
    currentRenderState.depthTest = enable;
    if (enable) {
        glEnable(GL_DEPTH_TEST);
    } else {
        glDisable(GL_DEPTH_TEST);
    }
}

void OpenGLUtils::enableBlending(bool enable) {
    currentRenderState.blending = enable;
    if (enable) {
        glEnable(GL_BLEND);
    } else {
        glDisable(GL_BLEND);
    }
}

void OpenGLUtils::enableCulling(bool enable) {
    currentRenderState.culling = enable;
    if (enable) {
        glEnable(GL_CULL_FACE);
    } else {
        glDisable(GL_CULL_FACE);
    }
}

void OpenGLUtils::setBlendFunc(GLenum src, GLenum dst) {
    currentRenderState.blendSrc = src;
    currentRenderState.blendDst = dst;
    glBlendFunc(src, dst);
}

void OpenGLUtils::setCullFace(GLenum face) {
    currentRenderState.cullFace = face;
    glCullFace(face);
}

void OpenGLUtils::setDepthFunc(GLenum func) {
    currentRenderState.depthFunc = func;
    glDepthFunc(func);
}

void OpenGLUtils::setProjectionMatrix(const float* matrix) {
    // This would typically be handled by a matrix stack or uniform
    // Implementation depends on the specific rendering setup
}

void OpenGLUtils::setViewMatrix(const float* matrix) {
    // This would typically be handled by a matrix stack or uniform
    // Implementation depends on the specific rendering setup
}

void OpenGLUtils::setModelMatrix(const float* matrix) {
    // This would typically be handled by a matrix stack or uniform
    // Implementation depends on the specific rendering setup
}

void OpenGLUtils::setMVP(const float* model, const float* view, const float* projection) {
    // This would typically be handled by a matrix stack or uniform
    // Implementation depends on the specific rendering setup
}

void OpenGLUtils::createOrthographicMatrix(float* matrix, float left, float right, 
                                          float bottom, float top, float near, float far) {
    // Create orthographic projection matrix
    // This is a simplified implementation
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = 2.0f / (right - left);
    matrix[5] = 2.0f / (top - bottom);
    matrix[10] = -2.0f / (far - near);
    matrix[12] = -(right + left) / (right - left);
    matrix[13] = -(top + bottom) / (top - bottom);
    matrix[14] = -(far + near) / (far - near);
    matrix[15] = 1.0f;
}

void OpenGLUtils::createPerspectiveMatrix(float* matrix, float fov, float aspect, 
                                         float near, float far) {
    // Create perspective projection matrix
    // This is a simplified implementation
    float f = 1.0f / tanf(fov * 0.5f);
    
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = f / aspect;
    matrix[5] = f;
    matrix[10] = (far + near) / (near - far);
    matrix[11] = -1.0f;
    matrix[14] = (2.0f * far * near) / (near - far);
}

void OpenGLUtils::createLookAtMatrix(float* matrix, const Vector2D& eye, const Vector2D& center, 
                                    const Vector2D& up) {
    // Create look-at matrix (simplified for 2D)
    // This is a simplified implementation
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = 1.0f;
    matrix[5] = 1.0f;
    matrix[10] = 1.0f;
    matrix[12] = -eye.x;
    matrix[13] = -eye.y;
    matrix[15] = 1.0f;
}

bool OpenGLUtils::checkGLError(const std::string& operation) {
    GLenum error = glGetError();
    if (error != GL_NO_ERROR) {
        LOGE("OpenGL error in %s: %d", operation.c_str(), error);
        return false;
    }
    return true;
}

std::string OpenGLUtils::getGLErrorString(GLenum error) {
    switch (error) {
        case GL_NO_ERROR: return "No error";
        case GL_INVALID_ENUM: return "Invalid enum";
        case GL_INVALID_VALUE: return "Invalid value";
        case GL_INVALID_OPERATION: return "Invalid operation";
        case GL_OUT_OF_MEMORY: return "Out of memory";
        default: return "Unknown error";
    }
}

void OpenGLUtils::logGLInfo() {
    const char* vendor = reinterpret_cast<const char*>(glGetString(GL_VENDOR));
    const char* renderer = reinterpret_cast<const char*>(glGetString(GL_RENDERER));
    const char* version = reinterpret_cast<const char*>(glGetString(GL_VERSION));
    
    LOGI("OpenGL Info:");
    LOGI("  Vendor: %s", vendor ? vendor : "Unknown");
    LOGI("  Renderer: %s", renderer ? renderer : "Unknown");
    LOGI("  Version: %s", version ? version : "Unknown");
}

void OpenGLUtils::beginPerformanceQuery(const std::string& name) {
    // This is a simplified implementation
    // In a real implementation, you would use GL_EXT_disjoint_timer_query or similar
    LOGI("Begin performance query: %s", name.c_str());
}

void OpenGLUtils::endPerformanceQuery(const std::string& name) {
    // This is a simplified implementation
    LOGI("End performance query: %s", name.c_str());
}

float OpenGLUtils::getPerformanceQueryResult(const std::string& name) {
    // This is a simplified implementation
    return 0.0f;
}

size_t OpenGLUtils::getTotalTextureMemory() const {
    return totalTextureMemory;
}

size_t OpenGLUtils::getTotalBufferMemory() const {
    return totalBufferMemory;
}

void OpenGLUtils::logMemoryUsage() const {
    LOGI("OpenGL Memory Usage:");
    LOGI("  Texture Memory: %zu bytes", totalTextureMemory);
    LOGI("  Buffer Memory: %zu bytes", totalBufferMemory);
    LOGI("  Total Memory: %zu bytes", totalTextureMemory + totalBufferMemory);
}

void OpenGLUtils::drawDebugGrid(float spacing, int count) {
    // This is a simplified implementation
    // In a real implementation, you would draw a grid using OpenGL
    LOGI("Drawing debug grid: spacing=%.2f, count=%d", spacing, count);
}

void OpenGLUtils::drawDebugAxis(float length) {
    // This is a simplified implementation
    LOGI("Drawing debug axis: length=%.2f", length);
}

void OpenGLUtils::drawDebugRectangle(const Rectangle& rect, float r, float g, float b) {
    // This is a simplified implementation
    LOGI("Drawing debug rectangle: (%.2f, %.2f, %.2f, %.2f), color=(%.2f, %.2f, %.2f)",
         rect.x, rect.y, rect.width, rect.height, r, g, b);
}

// Private methods

std::string OpenGLUtils::loadShaderSource(const std::string& filename) {
    // This is a simplified implementation
    // In a real implementation, you would load from file
    return "";
}

bool OpenGLUtils::validateShader(GLuint shaderId) {
    GLint success;
    glGetShaderiv(shaderId, GL_COMPILE_STATUS, &success);
    return success == GL_TRUE;
}

bool OpenGLUtils::validateProgram(GLuint programId) {
    GLint success;
    glGetProgramiv(programId, GL_LINK_STATUS, &success);
    return success == GL_TRUE;
}

GLenum OpenGLUtils::getGLPrimitiveType(PrimitiveType type) const {
    switch (type) {
        case PrimitiveType::POINTS: return GL_POINTS;
        case PrimitiveType::LINES: return GL_LINES;
        case PrimitiveType::LINE_STRIP: return GL_LINE_STRIP;
        case PrimitiveType::LINE_LOOP: return GL_LINE_LOOP;
        case PrimitiveType::TRIANGLES: return GL_TRIANGLES;
        case PrimitiveType::TRIANGLE_STRIP: return GL_TRIANGLE_STRIP;
        case PrimitiveType::TRIANGLE_FAN: return GL_TRIANGLE_FAN;
        default: return GL_TRIANGLES;
    }
}

GLenum OpenGLUtils::getGLTextureFormat(TextureFormat format) const {
    switch (format) {
        case TextureFormat::RGB: return GL_RGB;
        case TextureFormat::RGBA: return GL_RGBA;
        case TextureFormat::ALPHA: return GL_ALPHA;
        case TextureFormat::LUMINANCE: return GL_LUMINANCE;
        case TextureFormat::LUMINANCE_ALPHA: return GL_LUMINANCE_ALPHA;
        case TextureFormat::DEPTH_COMPONENT: return GL_DEPTH_COMPONENT;
        case TextureFormat::DEPTH_STENCIL: return GL_DEPTH_STENCIL;
        default: return GL_RGBA;
    }
}

GLenum OpenGLUtils::getGLBufferType(BufferType type) const {
    switch (type) {
        case BufferType::VERTEX: return GL_ARRAY_BUFFER;
        case BufferType::INDEX: return GL_ELEMENT_ARRAY_BUFFER;
        case BufferType::UNIFORM: return GL_UNIFORM_BUFFER;
        case BufferType::TEXTURE: 
            // GL_TEXTURE_BUFFER is not available in OpenGL ES 2.0
            // Use GL_ARRAY_BUFFER as fallback for compatibility
            return GL_ARRAY_BUFFER;
        default: return GL_ARRAY_BUFFER;
    }
}

std::string OpenGLUtils::getDefaultVertexShader() const {
    return R"(
        attribute vec4 position;
        attribute vec2 texCoord;
        uniform mat4 mvpMatrix;
        varying vec2 vTexCoord;
        
        void main() {
            gl_Position = mvpMatrix * position;
            vTexCoord = texCoord;
        }
    )";
}

std::string OpenGLUtils::getDefaultFragmentShader() const {
    return R"(
        precision mediump float;
        uniform vec4 color;
        
        void main() {
            gl_FragColor = color;
        }
    )";
}

std::string OpenGLUtils::getDefaultTexturedVertexShader() const {
    return R"(
        attribute vec4 position;
        attribute vec2 texCoord;
        uniform mat4 mvpMatrix;
        varying vec2 vTexCoord;
        
        void main() {
            gl_Position = mvpMatrix * position;
            vTexCoord = texCoord;
        }
    )";
}

std::string OpenGLUtils::getDefaultTexturedFragmentShader() const {
    return R"(
        precision mediump float;
        uniform sampler2D texture;
        varying vec2 vTexCoord;
        
        void main() {
            gl_FragColor = texture2D(texture, vTexCoord);
        }
    )";
}
