import SwiftUI

// MARK: - Funções Wrapper (Para replicar o estilo Kotlin/Compose)

public extension View {
    // Simula a função wrapper 'dynamicDp'
    func dynamicDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.dynamic().dimension(content: content)
    }

    // Simula a função wrapper 'fixedDp'
    func fixedDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.fixed().dimension(content: content)
    }
}

// MARK: - Exemplo de Uso de Todos os Recursos

struct DemoView: View {
    @State private var itemCount: Int = 0 
    
    var body: some View {
        ScrollView {
            // Usa o 'fixed' para espaçamento (padding/spacing)
            VStack(spacing: 20.fixed().dimension) {
                
                Text("Demonstração do Sistema de Dimensões (SwiftUI)")
                    .font(.title3)
                    .bold()
                    .padding(.bottom, 10.fixed().dimension)

                // --- 1. Sintaxe de Unidades Físicas (mm, cm, inch) ---
                Group {
                    Text("1. Unidades Físicas: 2cm, 5mm e 1 polegada")
                        .font(.caption)
                    HStack(spacing: 10.fixed().dimension) {
                        Rectangle()
                            .fill(Color.blue)
                            .frame(width: 2.cm, height: 2.cm)
                        Rectangle()
                            .fill(Color.red)
                            .frame(width: 5.mm, height: 5.mm)
                        Rectangle()
                            .fill(Color.green)
                            .frame(width: 1.inch, height: 1.inch)
                    }
                }
                .padding(.horizontal, 16.fixed().dimension)
                
                Divider()

                // --- 2. Ajuste Fixo (Fixed) com Qualificadores e AR ---
                VStack(alignment: .leading, spacing: 10.fixed().dimension) {
                    Text("2. Ajuste Fixo (Fixed): Com AR, Custom Qualifiers")
                        .font(.headline)

                    // Usa fixed() para um tamanho que deve ser escalado
                    Text("Tamanho Base: 100.fixed()")
                        .frame(width: 100.fixed().dimension, height: 50.fixed().dimension)
                        .background(Color.yellow.opacity(0.3))
                        .border(Color.yellow, width: 1)
                        
                    // Usa fixed() com um qualificador (se a largura for maior que 600, usa 200dp)
                    Text("Tamanho com Qualificador SW>=600: 200")
                        .frame(width: 100.fixed().add(type: .smallWidth, value: 600, customValue: 200).dimension, height: 50.fixed().dimension)
                        .background(Color.orange.opacity(0.3))
                        .border(Color.orange, width: 1)
                    
                    // Exemplo de uso de ScreenType.highest
                    Text("Base 50.fixed().screen(.highest)")
                        .frame(width: 50.fixed().screen(type: .highest).dimension, height: 25.fixed().dimension)
                        .background(Color.purple.opacity(0.3))
                        .border(Color.purple, width: 1)

                }
                .padding(.horizontal, 16.fixed().dimension)
                
                Divider()

                // --- 3. Ajuste Dinâmico (Dynamic) com Porcentagem ---
                VStack(alignment: .leading, spacing: 10.fixed().dimension) {
                    Text("3. Ajuste Dinâmico (Dynamic): Porcentagem da Tela")
                        .font(.headline)

                    // 50% da menor dimensão da tela
                    Text("Largura 50% da menor dimensão")
                        .frame(width: 0.5.dynamicPercentage().dimension, height: 50.fixed().dimension)
                        .background(Color.teal.opacity(0.3))
                        .border(Color.teal, width: 1)

                    // Usa dynamic() com um valor base (100dp)
                    Text("Largura Base: 100.dynamic()")
                        .frame(width: 100.dynamic().dimension, height: 50.fixed().dimension)
                        .background(Color.cyan.opacity(0.3))
                        .border(Color.cyan, width: 1)
                }
                .padding(.horizontal, 16.fixed().dimension)


                Divider()

                // --- 4. CalculateAvailableItemCount (Contagem de Itens) ---
                VStack {
                    Text("4. Contagem de Itens: \(itemCount) itens disponíveis")
                        .font(.headline)
                    
                    // Contêiner que será medido
                    Rectangle()
                        .fill(Color.gray.opacity(0.1))
                        .frame(height: 100)
                        
                        // Lógica de cálculo aplicada na largura do contêiner
                        .calculateAvailableItemCount(
                            itemSize: 50.fixed().dimension,       // Tamanho do item (ajustado)
                            itemPadding: 8.fixed().dimension,   // Padding do item (ajustado)
                            direction: .width,                    // Medir na LARGURA
                            count: $itemCount                     // Variável de destino
                        )
                }
                .padding(.horizontal, 16.fixed().dimension)
                
            }
            .padding(.top, 30.fixed().dimension)
        }
    }
}

// MARK: - App Principal

@main
struct MainApp: App {
    var body: some Scene {
        WindowGroup {
            // O DimensProvider é ESSENCIAL para injetar as dimensões e fatores no Environment
            DimensProvider { 
                DemoView()
            }
        }
    }
}
