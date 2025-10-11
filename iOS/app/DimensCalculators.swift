import Foundation
import SwiftUI

// MARK: - Protocolo de Cálculo

/// Protocolo que define a capacidade de calcular uma dimensão final. (Kotlin: DimensionCalculator)
public protocol DimensionCalculator {
    func withAspectRation(_ apply: Bool) -> Self
    func ignoreMultiViewAdjustment(_ ignore: Bool) -> Self
    func screen(type: ScreenType) -> Self
    func calculate(screenWidth: DimensPoint, screenHeight: DimensPoint, factors: ScreenAdjustmentFactors, uiMode: UiModeType) -> DimensPoint
}

// MARK: - Lógica Auxiliar de Qualificadores

fileprivate func resolveQualifierValue(
    customDpMap: [DpQualifierEntry: DimensPoint],
    customUiModeMap: [UiModeQualifierEntry: DimensPoint],
    uiMode: UiModeType,
    smallestWidthDp: DimensPoint,
    currentScreenWidthDp: DimensPoint,
    currentScreenHeightDp: DimensPoint,
    initialBaseValue: DimensPoint
) -> DimensPoint {
    var valueToAdjust = initialBaseValue
    let BASE_WIDTH_DP = AppDimensConstants.BASE_WIDTH_DP

    // 1. Resolve UiMode Qualifier (Prioridade Máxima)
    // Ordenação decrescente para garantir que o valor MAIS ALTO e específico seja usado
    let sortedUiModeQualifiers = customUiModeMap.keys.sorted { $0.dpQualifierEntry.value > $1.dpQualifierEntry.value }

    if let foundValue = sortedUiModeQualifiers.first(where: { entry in
        guard entry.uiModeType == uiMode else { return false }
        
        let qualifier = entry.dpQualifierEntry
        
        switch qualifier.type {
        case .smallWidth:
            return smallestWidthDp >= qualifier.value
        case .height:
            return currentScreenHeightDp >= qualifier.value
        case .width:
            return currentScreenWidthDp >= qualifier.value
        }
    }).flatMap({ customUiModeMap[$0] }) {
        valueToAdjust = foundValue
    }
    
    // 2. Resolve Dp Qualifier (Se UiMode não substituiu)
    if valueToAdjust == initialBaseValue {
        // Ordenação decrescente para garantir que o qualificador MAIS ALTO e específico seja usado
        let sortedQualifiers = customDpMap.keys.sorted { $0.value > $1.value }

        // Prioridade: SMALL_WIDTH > HEIGHT > WIDTH
        if let foundValue = sortedQualifiers.first(where: {
            $0.type == .smallWidth && smallestWidthDp >= $0.value
        }).flatMap({ customDpMap[$0] }) {
            valueToAdjust = foundValue
        } else if let foundValue = sortedQualifiers.first(where: {
            $0.type == .height && currentScreenHeightDp >= $0.value
        }).flatMap({ customDpMap[$0] }) {
            valueToAdjust = foundValue
        } else if let foundValue = sortedQualifiers.first(where: {
            $0.type == .width && currentScreenWidthDp >= $0.value
        }).flatMap({ customDpMap[$0] }) {
            valueToAdjust = foundValue
        }
    }

    // Garante que o valor ajustado não seja menor que o BASE_WIDTH_DP, se o valor inicial era maior.
    // Isso evita downscaling agressivo em qualificadores mais baixos.
    return valueToAdjust // Retorna o valor base ajustado (em Dp)
}


// MARK: - Fixed Calculator (Ajuste Fixo por Fator de Escala)

/// Calcula a dimensão aplicando um fator de escala (AR, sem AR) ao valor base. (Kotlin: AppDimensFixed)
public struct Fixed: DimensionCalculator {
    private let initialBaseValue: DimensPoint
    private var applyAspectRation: Bool = true
    private var ignoreMultiViewAdjustment: Bool = false
    private var screenType: ScreenType = .lowest
    private var customMap: [DpQualifierEntry: DimensPoint] = [:]
    private var customUiModeMap: [UiModeQualifierEntry: DimensPoint] = [:]
    private var customSensitivityK: DimensPoint? = nil

    public init(_ initialBaseValue: DimensPoint) {
        self.initialBaseValue = initialBaseValue
    }

    // MARK: Funções de Modificação

    public func withAspectRation(_ apply: Bool) -> Fixed {
        var copy = self
        copy.applyAspectRation = apply
        return copy
    }

    public func ignoreMultiViewAdjustment(_ ignore: Bool) -> Fixed {
        var copy = self
        copy.ignoreMultiViewAdjustment = ignore
        return copy
    }
    
    public func screen(type: ScreenType) -> Fixed {
        var copy = self
        copy.screenType = type
        return copy
    }
    
    /// Adiciona um qualificador customizado de tela (SW, H, W).
    public func add(type: DpQualifier, value: DimensPoint, customValue: DimensPoint) -> Fixed {
        var copy = self
        copy.customMap[DpQualifierEntry(type: type, value: value)] = customValue
        return copy
    }
    
    /// Adiciona um qualificador customizado de UI Mode e Tela.
    public func add(uiMode: UiModeType, type: DpQualifier, value: DimensPoint, customValue: DimensPoint) -> Fixed {
        var copy = self
        let entry = UiModeQualifierEntry(uiModeType: uiMode, dpQualifierEntry: DpQualifierEntry(type: type, value: value))
        copy.customUiModeMap[entry] = customValue
        return copy
    }
    
    /// Recalcula o fator AR com sensibilidade customizada (k).
    public func customSensitivity(k: DimensPoint) -> Fixed {
        var copy = self
        copy.customSensitivityK = k
        return copy
    }

    // MARK: - Lógica de Cálculo

    public func calculate(screenWidth: DimensPoint, screenHeight: DimensPoint, factors: ScreenAdjustmentFactors, uiMode: UiModeType) -> DimensPoint {
        let smallestWidthDp = min(screenWidth, screenHeight)
        let BASE_WIDTH_DP = AppDimensConstants.BASE_WIDTH_DP
        let BASE_DP_FACTOR = AppDimensConstants.BASE_DP_FACTOR
        let BASE_INCREMENT = AppDimensConstants.BASE_INCREMENT
        let REFERENCE_AR = AppDimensConstants.REFERENCE_AR

        let dpToAdjust = resolveQualifierValue(
            customDpMap: customMap,
            customUiModeMap: customUiModeMap,
            uiMode: uiMode,
            smallestWidthDp: smallestWidthDp,
            currentScreenWidthDp: screenWidth,
            currentScreenHeightDp: screenHeight,
            initialBaseValue: initialBaseValue
        )

        // Verificação de Multi-View (simulação de "janela pequena" no iOS)
        let isSmallWindow = screenWidth < BASE_WIDTH_DP || screenHeight < BASE_WIDTH_DP
        let shouldIgnoreAdjustment = ignoreMultiViewAdjustment && isSmallWindow
        
        if shouldIgnoreAdjustment {
             // Se ignorar, retorna o valor Dp base sem escalonamento.
             return dpToAdjust
        }

        let finalAdjustmentFactor: DimensPoint
        
        if applyAspectRation {
            // 1. Seleciona o fator AR pré-calculado
            var selectedFactor: DimensPoint = (screenType == .highest) ? factors.withArFactorHighest : factors.withArFactorLowest
            
            if let customSensitivityK = customSensitivityK {
                // 2. Se houver sensibilidade customizada, recalcula o fator AR
                let adjustmentFactorBase = (screenType == .highest) ? factors.adjustmentFactorHighest : factors.adjustmentFactorLowest
                let currentAr = max(screenWidth, screenHeight) / min(screenWidth, screenHeight)
                
                // Cálculo logarítmico para recálculo de sensibilidade
                let continuousAdjustment = (customSensitivityK * log(currentAr / REFERENCE_AR))
                let finalIncrementValue = BASE_INCREMENT + continuousAdjustment
                
                selectedFactor = BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue
            }
            
            finalAdjustmentFactor = selectedFactor
            
        } else {
            // Sem AR, usa o fator sem AR pré-calculado
            finalAdjustmentFactor = factors.withoutArFactor
        }

        return dpToAdjust * finalAdjustmentFactor
    }
}


// MARK: - Dynamic Calculator (Ajuste Dinâmico por Porcentagem)

/// Calcula a dimensão como uma porcentagem da dimensão da tela atual. (Kotlin: AppDimensDynamic)
public struct Dynamic: DimensionCalculator {
    private let initialBaseValue: DimensPoint
    private var ignoreMultiViewAdjustment: Bool = false
    private var screenType: ScreenType = .lowest
    private var customMap: [DpQualifierEntry: DimensPoint] = [:]
    private var customUiModeMap: [UiModeQualifierEntry: DimensPoint] = [:]
    
    // O Dynamic ignora o ajuste de Aspect Ratio, mas implementa o protocolo
    public func withAspectRation(_ apply: Bool) -> Dynamic { self }

    public init(_ initialBaseValue: DimensPoint) {
        self.initialBaseValue = initialBaseValue
    }

    // MARK: Funções de Modificação

    public func ignoreMultiViewAdjustment(_ ignore: Bool) -> Dynamic {
        var copy = self
        copy.ignoreMultiViewAdjustment = ignore
        return copy
    }

    public func screen(type: ScreenType) -> Dynamic {
        var copy = self
        copy.screenType = type
        return copy
    }

    /// Adiciona um qualificador customizado de tela (SW, H, W).
    public func add(type: DpQualifier, value: DimensPoint, customValue: DimensPoint) -> Dynamic {
        var copy = self
        copy.customMap[DpQualifierEntry(type: type, value: value)] = customValue
        return copy
    }
    
    /// Adiciona um qualificador customizado de UI Mode e Tela.
    public func add(uiMode: UiModeType, type: DpQualifier, value: DimensPoint, customValue: DimensPoint) -> Dynamic {
        var copy = self
        let entry = UiModeQualifierEntry(uiModeType: uiMode, dpQualifierEntry: DpQualifierEntry(type: type, value: value))
        copy.customUiModeMap[entry] = customValue
        return copy
    }

    // MARK: - Lógica de Cálculo

    public func calculate(screenWidth: DimensPoint, screenHeight: DimensPoint, factors: ScreenAdjustmentFactors, uiMode: UiModeType) -> DimensPoint {
        let smallestWidthDp = min(screenWidth, screenHeight)
        let highestDimensionDp = max(screenWidth, screenHeight)
        let BASE_WIDTH_DP = AppDimensConstants.BASE_WIDTH_DP

        let dpToAdjust = resolveQualifierValue(
            customDpMap: customMap,
            customUiModeMap: customUiModeMap,
            uiMode: uiMode,
            smallestWidthDp: smallestWidthDp,
            currentScreenWidthDp: screenWidth,
            currentScreenHeightDp: screenHeight,
            initialBaseValue: initialBaseValue
        )

        // Verificação de Multi-View
        let isSmallWindow = screenWidth < BASE_WIDTH_DP || screenHeight < BASE_WIDTH_DP
        let shouldIgnoreAdjustment = ignoreMultiViewAdjustment && isSmallWindow
        
        if shouldIgnoreAdjustment {
             // Se ignorar, retorna o valor Dp base sem escalonamento.
             return dpToAdjust
        }

        // Porcentagem: (DP Base Ajustado / DP de Referência)
        let percentage = dpToAdjust / BASE_WIDTH_DP

        // Dimensão da tela a ser usada (LOWEST ou HIGHEST)
        let dimensionToUse = (screenType == .highest) ? highestDimensionDp : smallestWidthDp

        // O valor final é a porcentagem aplicada à dimensão da tela
        return dimensionToUse * percentage
    }
}
