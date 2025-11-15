package com.saude.imc.domain;

/**
 * Enum que representa as categorias de IMC conforme classificação utilizada
 * no código original do repositório Wolfterro/Projetos-em-Java.
 *
 * Esta classificação possui 8 categorias, diferente da classificação simplificada
 * da OMS que possui 4 categorias.
 *
 * @author André Luis Becker
 * @see <a href="https://github.com/Wolfterro/Projetos-em-Java/tree/master/CalculoIMC">Código Original</a>
 */
public enum CategoriaIMC {

    /**
     * IMC menor que 16.0 kg/m²
     */
    MAGREZA_GRAVE("Magreza grave", 0.0, 16.0),

    /**
     * IMC entre 16.0 (inclusive) e 17.0 kg/m²
     */
    MAGREZA_MODERADA("Magreza moderada", 16.0, 17.0),

    /**
     * IMC entre 17.0 (inclusive) e 18.5 kg/m²
     */
    MAGREZA_LEVE("Magreza leve", 17.0, 18.5),

    /**
     * IMC entre 18.5 (inclusive) e 25.0 kg/m²
     */
    SAUDAVEL("Saudável", 18.5, 25.0),

    /**
     * IMC entre 25.0 (inclusive) e 30.0 kg/m²
     */
    SOBREPESO("Sobrepeso", 25.0, 30.0),

    /**
     * IMC entre 30.0 (inclusive) e 35.0 kg/m²
     */
    OBESIDADE_GRAU_I("Obesidade Grau I", 30.0, 35.0),

    /**
     * IMC entre 35.0 (inclusive) e 40.0 kg/m²
     */
    OBESIDADE_GRAU_II("Obesidade Grau II", 35.0, 40.0),

    /**
     * IMC maior ou igual a 40.0 kg/m²
     */
    OBESIDADE_GRAU_III("Obesidade Grau III", 40.0, Double.MAX_VALUE);

    private final String descricao;
    private final double limiteInferior;
    private final double limiteSuperior;

    CategoriaIMC(String descricao, double limiteInferior, double limiteSuperior) {
        this.descricao = descricao;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    /**
     * Retorna a descrição legível da categoria.
     *
     * @return descrição da categoria
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o limite inferior da categoria (inclusive).
     *
     * @return limite inferior em kg/m²
     */
    public double getLimiteInferior() {
        return limiteInferior;
    }

    /**
     * Retorna o limite superior da categoria (exclusive, exceto para OBESIDADE_GRAU_III).
     *
     * @return limite superior em kg/m²
     */
    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    /**
     * Classifica um valor de IMC na categoria apropriada.
     * Replica a lógica do método classificarIMC() do código original.
     *
     * @param imc valor do IMC em kg/m²
     * @return categoria correspondente
     */
    public static CategoriaIMC classificar(double imc) {
        if (imc < 16.0) {
            return MAGREZA_GRAVE;
        }
        else if (imc >= 16.0 && imc < 17.0) {
            return MAGREZA_MODERADA;
        }
        else if (imc >= 17.0 && imc < 18.5) {
            return MAGREZA_LEVE;
        }
        else if (imc >= 18.5 && imc < 25.0) {
            return SAUDAVEL;
        }
        else if (imc >= 25.0 && imc < 30.0) {
            return SOBREPESO;
        }
        else if (imc >= 30.0 && imc < 35.0) {
            return OBESIDADE_GRAU_I;
        }
        else if (imc >= 35.0 && imc < 40.0) {
            return OBESIDADE_GRAU_II;
        }
        else {
            return OBESIDADE_GRAU_III;
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
}
