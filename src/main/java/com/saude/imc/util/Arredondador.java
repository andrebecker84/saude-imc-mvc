package com.saude.imc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilitário para arredondamento de valores decimais.
 *
 * <p>Centraliza a lógica de arredondamento aplicando o princípio DRY (Don't Repeat Yourself).
 * Utiliza {@link BigDecimal} para garantir precisão aritmética e evitar problemas
 * de arredondamento com doubles.</p>
 *
 * <p><strong>Modo de arredondamento:</strong> HALF_UP (arredonda para cima quando
 * o dígito seguinte é ≥ 5)</p>
 *
 * @see BigDecimal
 * @see RoundingMode#HALF_UP
 */
public final class Arredondador {

    private static final int CASAS_DECIMAIS = 2;

    /**
     * Construtor privado para prevenir instanciação.
     * Esta é uma classe utilitária com apenas métodos estáticos.
     */
    private Arredondador() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada");
    }

    /**
     * Arredonda um valor para duas casas decimais usando HALF_UP.
     *
     * <p>Exemplos:</p>
     * <ul>
     *   <li>24.686 → 24.69</li>
     *   <li>24.684 → 24.68</li>
     *   <li>24.685 → 24.69</li>
     * </ul>
     *
     * @param valor valor a ser arredondado
     * @return valor arredondado para 2 casas decimais
     */
    public static double duasCasas(double valor) {
        return new BigDecimal(valor)
                .setScale(CASAS_DECIMAIS, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
