package com.saude.imc.util;

/**
 * Validador de entradas para cálculo de IMC.
 *
 * <p>Responsável por validar os parâmetros de entrada (peso e altura)
 * segundo as regras de negócio estabelecidas. Aplica o princípio SRP
 * centralizando toda a lógica de validação.</p>
 *
 * <p><strong>Regras de Validação:</strong></p>
 * <ul>
 *   <li>Peso: 0 {@literal <} peso ≤ 500 kg</li>
 *   <li>Altura: 0 {@literal <} altura ≤ 3.5 m</li>
 * </ul>
 */
public final class ValidadorEntrada {

    private static final double PESO_MINIMO = 0.0;
    private static final double PESO_MAXIMO = 500.0;
    private static final double ALTURA_MINIMA = 0.0;
    private static final double ALTURA_MAXIMA = 3.5;

    /**
     * Construtor privado para prevenir instanciação.
     * Esta é uma classe utilitária com apenas métodos estáticos.
     */
    private ValidadorEntrada() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada");
    }

    /**
     * Valida se o peso está dentro dos limites aceitáveis.
     *
     * @param pesoKg peso em quilogramas
     * @throws IllegalArgumentException se o peso for inválido (≤ 0 ou {@literal >} 500)
     */
    public static void validarPeso(double pesoKg) {
        if (pesoKg <= PESO_MINIMO || pesoKg > PESO_MAXIMO) {
            throw new IllegalArgumentException(
                String.format("Peso inválido: %.2f kg. Informe um valor > %.1f e ≤ %.1f kg.",
                    pesoKg, PESO_MINIMO, PESO_MAXIMO)
            );
        }
    }

    /**
     * Valida se a altura está dentro dos limites aceitáveis.
     *
     * @param alturaM altura em metros
     * @throws IllegalArgumentException se a altura for inválida (≤ 0 ou {@literal >} 3.5)
     */
    public static void validarAltura(double alturaM) {
        if (alturaM <= ALTURA_MINIMA || alturaM > ALTURA_MAXIMA) {
            throw new IllegalArgumentException(
                String.format("Altura inválida: %.2f m. Informe um valor > %.1f e ≤ %.1f m.",
                    alturaM, ALTURA_MINIMA, ALTURA_MAXIMA)
            );
        }
    }
}
