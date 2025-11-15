package com.saude.imc.util;

import com.saude.imc.domain.CategoriaIMC;

/**
 * Calculadora de Índice de Massa Corporal (IMC).
 *
 * <p>Responsável por calcular o IMC e categorizá-lo segundo a classificação
 * do código original (Wolfterro/Projetos-em-Java).</p>
 *
 * <p>Aplica o princípio SRP (Single Responsibility Principle) mantendo foco exclusivo
 * no cálculo e categorização do IMC.</p>
 *
 * <p><strong>Fórmula:</strong> IMC = peso(kg) / altura(m)²</p>
 * <p><strong>Arredondamento:</strong> 2 casas decimais com HALF_UP</p>
 *
 * <p>Categorias (8 categorias conforme código original):</p>
 * <ul>
 *   <li>IMC {@literal <} 16.0 → Magreza grave</li>
 *   <li>16.0 ≤ IMC {@literal <} 17.0 → Magreza moderada</li>
 *   <li>17.0 ≤ IMC {@literal <} 18.5 → Magreza leve</li>
 *   <li>18.5 ≤ IMC {@literal <} 25.0 → Saudável</li>
 *   <li>25.0 ≤ IMC {@literal <} 30.0 → Sobrepeso</li>
 *   <li>30.0 ≤ IMC {@literal <} 35.0 → Obesidade Grau I</li>
 *   <li>35.0 ≤ IMC {@literal <} 40.0 → Obesidade Grau II</li>
 *   <li>IMC ≥ 40.0 → Obesidade Grau III</li>
 * </ul>
 *
 * @see CategoriaIMC
 * @see ValidadorEntrada
 * @see Arredondador
 */
public final class CalculadoraIMC {

    /**
     * Construtor privado para prevenir instanciação.
     * Esta é uma classe utilitária com apenas métodos estáticos.
     */
    private CalculadoraIMC() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada");
    }

    /**
     * Calcula o Índice de Massa Corporal (IMC).
     *
     * @param pesoKg peso em quilogramas (deve estar entre 0 e 500)
     * @param alturaM altura em metros (deve estar entre 0 e 3.5)
     * @return IMC arredondado para 2 casas decimais
     * @throws IllegalArgumentException se peso ou altura forem inválidos
     */
    public static double calcular(double pesoKg, double alturaM) {
        ValidadorEntrada.validarPeso(pesoKg);
        ValidadorEntrada.validarAltura(alturaM);

        double imc = pesoKg / (alturaM * alturaM);
        return Arredondador.duasCasas(imc);
    }

    /**
     * Categoriza o IMC segundo a classificação do código original (8 categorias).
     * Replica a lógica do método classificarIMC() do CalculoIMC.java original.
     *
     * @param imc valor do Índice de Massa Corporal
     * @return categoria correspondente ao valor do IMC
     * @see CategoriaIMC
     */
    public static CategoriaIMC categorizar(double imc) {
        return CategoriaIMC.classificar(imc);
    }
}
