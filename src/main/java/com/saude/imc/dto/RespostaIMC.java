package com.saude.imc.dto;

import com.saude.imc.domain.CategoriaIMC;
import lombok.Builder;
import lombok.Value;

/**
 * DTO (Data Transfer Object) para resposta do cálculo de IMC.
 *
 * <p>Classe imutável que encapsula os dados de resposta da API,
 * incluindo os valores de entrada (peso e altura) e os resultados
 * calculados (IMC e categoria).</p>
 *
 * <p>Utiliza Lombok para gerar automaticamente:</p>
 * <ul>
 *   <li>Getters para todos os campos</li>
 *   <li>equals(), hashCode() e toString()</li>
 *   <li>Padrão Builder para construção fluente</li>
 * </ul>
 *
 * <p><strong>Exemplo de uso:</strong></p>
 * <pre>{@code
 * RespostaIMC resposta = RespostaIMC.builder()
 *     .peso(80.0)
 *     .altura(1.80)
 *     .imc(24.69)
 *     .categoria(CategoriaIMC.NORMAL)
 *     .build();
 * }</pre>
 *
 * @see CategoriaIMC
 */
@Value
@Builder
public class RespostaIMC {

    /**
     * Peso em quilogramas.
     */
    double peso;

    /**
     * Altura em metros.
     */
    double altura;

    /**
     * Índice de Massa Corporal calculado e arredondado (2 casas decimais).
     */
    double imc;

    /**
     * Categoria de classificação do IMC segundo a OMS.
     */
    CategoriaIMC categoria;
}
