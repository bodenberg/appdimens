/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal Game Example
 *
 * Description:
 * Example implementation of AppDimens Games module in a Metal-based game.
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

import UIKit
import Metal
import MetalKit
import simd
import AppDimensGames

/**
 * [EN] Example Metal game view controller demonstrating AppDimens Games usage.
 * [PT] Exemplo de controlador de view de jogo Metal demonstrando o uso do AppDimens Games.
 */
class MetalGameViewController: UIViewController {
    
    // MARK: - Metal Properties
    
    private var metalDevice: MTLDevice!
    private var metalLayer: CAMetalLayer!
    private var commandQueue: MTLCommandQueue!
    private var metalManager: AppDimensMetal!
    
    // MARK: - Game Properties
    
    private var gameObjects: [GameObject] = []
    private var lastFrameTime: CFTimeInterval = 0
    
    // MARK: - UI Properties
    
    private var scoreLabel: UILabel!
    private var pauseButton: UIButton!
    private var gameArea: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupMetal()
        setupUI()
        setupGame()
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        updateViewport()
    }
    
    // MARK: - Metal Setup
    
    private func setupMetal() {
        // Configurar Metal
        guard let device = MTLCreateSystemDefaultDevice() else {
            fatalError("Metal não está disponível neste dispositivo")
        }
        
        metalDevice = device
        
        // Configurar CAMetalLayer
        metalLayer = CAMetalLayer()
        metalLayer.device = metalDevice
        metalLayer.pixelFormat = .bgra8Unorm
        metalLayer.framebufferOnly = true
        view.layer.addSublayer(metalLayer)
        
        // Configurar command queue
        commandQueue = metalDevice.makeCommandQueue()
        
        // Inicializar AppDimens Games
        let initialViewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(view.bounds.width),
            height: Double(view.bounds.height),
            znear: 0.0, zfar: 1.0
        )
        
        AppDimensGames.shared.initialize(device: metalDevice, viewport: initialViewport)
        metalManager = AppDimensGames.shared.getMetalManager()
    }
    
    private func updateViewport() {
        let newViewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(view.bounds.width),
            height: Double(view.bounds.height),
            znear: 0.0, zfar: 1.0
        )
        
        AppDimensGames.shared.updateViewport(newViewport)
        metalLayer.frame = view.bounds
    }
    
    // MARK: - UI Setup
    
    private func setupUI() {
        view.backgroundColor = .black
        
        // Área do jogo
        gameArea = UIView()
        gameArea.backgroundColor = .clear
        view.addSubview(gameArea)
        
        // Label de pontuação
        scoreLabel = UILabel()
        scoreLabel.text = "Score: 0"
        scoreLabel.textColor = .white
        scoreLabel.font = UIFont.systemFont(ofSize: 24, weight: .bold)
        view.addSubview(scoreLabel)
        
        // Botão de pausa
        pauseButton = UIButton(type: .system)
        pauseButton.setTitle("Pause", for: .normal)
        pauseButton.setTitleColor(.white, for: .normal)
        pauseButton.backgroundColor = UIColor.white.withAlphaComponent(0.2)
        pauseButton.addTarget(self, action: #selector(pauseButtonTapped), for: .touchUpInside)
        view.addSubview(pauseButton)
        
        setupConstraints()
    }
    
    private func setupConstraints() {
        gameArea.translatesAutoresizingMaskIntoConstraints = false
        scoreLabel.translatesAutoresizingMaskIntoConstraints = false
        pauseButton.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Área do jogo
            gameArea.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            gameArea.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            gameArea.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            gameArea.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            
            // Label de pontuação
            scoreLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 20),
            scoreLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 20),
            
            // Botão de pausa
            pauseButton.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 20),
            pauseButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -20),
            pauseButton.widthAnchor.constraint(equalToConstant: 80),
            pauseButton.heightAnchor.constraint(equalToConstant: 40)
        ])
        
        // Aplicar dimensões responsivas
        applyResponsiveDimensions()
    }
    
    private func applyResponsiveDimensions() {
        // Usar AppDimens para dimensionar elementos de UI
        scoreLabel.fxFontSize(24)
        pauseButton.fxCornerRadius(8)
        
        // Atualizar constraints com dimensões responsivas
        DispatchQueue.main.async {
            self.updateUIConstraints()
        }
    }
    
    private func updateUIConstraints() {
        // Usar AppDimens Games para dimensionar elementos de UI do jogo
        let topPadding = AppDimensGames.uniform(20.0)
        let sidePadding = AppDimensGames.horizontal(20.0)
        let buttonWidth = AppDimensGames.uniform(80.0)
        let buttonHeight = AppDimensGames.uniform(40.0)
        
        // Atualizar constraints
        scoreLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: CGFloat(topPadding)).isActive = true
        scoreLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: CGFloat(sidePadding)).isActive = true
        
        pauseButton.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: CGFloat(topPadding)).isActive = true
        pauseButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -CGFloat(sidePadding)).isActive = true
        pauseButton.widthAnchor.constraint(equalToConstant: CGFloat(buttonWidth)).isActive = true
        pauseButton.heightAnchor.constraint(equalToConstant: CGFloat(buttonHeight)).isActive = true
    }
    
    // MARK: - Game Setup
    
    private func setupGame() {
        // Criar objetos do jogo com dimensões responsivas
        createGameObjects()
        startGameLoop()
    }
    
    private func createGameObjects() {
        // Criar player
        let playerSize = AppDimensGames.uniform(50.0)
        let player = GameObject(
            position: simd_float2(0, 0),
            size: simd_float2(playerSize, playerSize),
            color: simd_float4(0, 1, 0, 1)
        )
        gameObjects.append(player)
        
        // Criar inimigos
        for i in 0..<5 {
            let enemySize = AppDimensGames.uniform(30.0)
            let enemy = GameObject(
                position: simd_float2(Float(i * 100), 100),
                size: simd_float2(enemySize, enemySize),
                color: simd_float4(1, 0, 0, 1)
            )
            gameObjects.append(enemy)
        }
        
        // Criar power-ups
        for i in 0..<3 {
            let powerUpSize = AppDimensGames.uniform(20.0)
            let powerUp = GameObject(
                position: simd_float2(Float(i * 150), 200),
                size: simd_float2(powerUpSize, powerUpSize),
                color: simd_float4(0, 0, 1, 1)
            )
            gameObjects.append(powerUp)
        }
    }
    
    // MARK: - Game Loop
    
    private func startGameLoop() {
        let displayLink = CADisplayLink(target: self, selector: #selector(gameLoop))
        displayLink.add(to: .main, forMode: .default)
    }
    
    @objc private func gameLoop() {
        let currentTime = CACurrentMediaTime()
        let deltaTime = currentTime - lastFrameTime
        lastFrameTime = currentTime
        
        updateGame(deltaTime: deltaTime)
        renderGame()
    }
    
    private func updateGame(deltaTime: CFTimeInterval) {
        // Atualizar objetos do jogo
        for gameObject in gameObjects {
            gameObject.update(deltaTime: deltaTime)
        }
        
        // Verificar colisões
        checkCollisions()
    }
    
    private func renderGame() {
        guard let drawable = metalLayer.nextDrawable() else { return }
        
        let commandBuffer = commandQueue.makeCommandBuffer()
        let renderPassDescriptor = MTLRenderPassDescriptor()
        
        renderPassDescriptor.colorAttachments[0].texture = drawable.texture
        renderPassDescriptor.colorAttachments[0].loadAction = .clear
        renderPassDescriptor.colorAttachments[0].clearColor = MTLClearColor(red: 0, green: 0, blue: 0, alpha: 1)
        
        let renderEncoder = commandBuffer?.makeRenderCommandEncoder(descriptor: renderPassDescriptor)
        
        // Renderizar objetos do jogo
        for gameObject in gameObjects {
            renderGameObject(gameObject, encoder: renderEncoder!)
        }
        
        renderEncoder?.endEncoding()
        commandBuffer?.present(drawable)
        commandBuffer?.commit()
    }
    
    private func renderGameObject(_ gameObject: GameObject, encoder: MTLRenderCommandEncoder) {
        // Converter posição para NDC
        let ndcPosition = gameObject.position.toNDC(metalManager)
        let ndcSize = gameObject.size.gameUniform(metalManager)
        
        // Renderizar objeto (implementação simplificada)
        // Em uma implementação real, você usaria shaders Metal
        print("Rendering object at NDC: \(ndcPosition), size: \(ndcSize)")
    }
    
    private func checkCollisions() {
        // Implementar lógica de colisão
        // Usar dimensões responsivas para cálculos de colisão
        let collisionRadius = AppDimensGames.uniform(25.0)
        
        for i in 0..<gameObjects.count {
            for j in (i+1)..<gameObjects.count {
                let obj1 = gameObjects[i]
                let obj2 = gameObjects[j]
                
                let distance = simd_distance(obj1.position, obj2.position)
                if distance < Float(collisionRadius * 2) {
                    // Colisão detectada
                    handleCollision(obj1, obj2)
                }
            }
        }
    }
    
    private func handleCollision(_ obj1: GameObject, _ obj2: GameObject) {
        // Implementar lógica de colisão
        print("Collision detected between objects")
    }
    
    // MARK: - Actions
    
    @objc private func pauseButtonTapped() {
        // Implementar lógica de pausa
        print("Pause button tapped")
    }
}

// MARK: - GameObject

/**
 * [EN] Simple game object class for demonstration.
 * [PT] Classe simples de objeto de jogo para demonstração.
 */
class GameObject {
    var position: simd_float2
    var size: simd_float2
    var color: simd_float4
    var velocity: simd_float2
    
    init(position: simd_float2, size: simd_float2, color: simd_float4) {
        self.position = position
        self.size = size
        self.color = color
        self.velocity = simd_float2(0, 0)
    }
    
    func update(deltaTime: CFTimeInterval) {
        // Atualizar posição baseada na velocidade
        position += velocity * Float(deltaTime)
        
        // Implementar lógica de movimento
        // Usar AppDimens Games para cálculos responsivos
        let moveSpeed = AppDimensGames.uniform(100.0)
        velocity = simd_float2(moveSpeed, 0)
    }
}
