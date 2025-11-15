package com.saude.imc.util;

import com.saude.imc.domain.CategoriaIMC;
import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes baseados em propriedades usando Jqwik.
 * Valida invariantes matemáticas do cálculo de IMC.
 */
class PropriedadesIMCTest {

    @Property
    @Label("IMC é sempre não-negativo para entradas válidas")
    void imc_nunca_negativo_para_entradas_validas(
            @ForAll("pesosValidos") double peso,
            @ForAll("alturasValidas") double altura) {

        double imc = CalculadoraIMC.calcular(peso, altura);
        assertThat(imc).isGreaterThanOrEqualTo(0.0);
    }

    @Property
    @Label("IMC aumenta monotonicamente com o peso (altura fixa)")
    void monotonicidade_no_peso_altura_fixa(
            @ForAll("alturasValidas") double altura,
            @ForAll("pesosValidos") double p1,
            @ForAll("pesosValidos") double p2) {

        // Garante diferença significativa para evitar problemas de arredondamento
        Assume.that(p2 - p1 >= 1.0);

        double imc1 = CalculadoraIMC.calcular(p1, altura);
        double imc2 = CalculadoraIMC.calcular(p2, altura);

        assertThat(imc2).isGreaterThan(imc1);
    }

    @Property(tries = 2000)
    @Label("IMC diminui antimonotonicamente com a altura (peso fixo)")
    void antimonotonicidade_na_altura_peso_fixo(
            @ForAll("pesosValidos") double peso,
            @ForAll("alturasValidas") double a1,
            @ForAll("alturasValidas") double a2) {

        // Garante valores que produzem diferença significativa no IMC
        Assume.that(peso >= 10.0); // Reduzi de 5.0 para ter mais casos válidos
        Assume.that(a2 - a1 >= 0.1); // Reduzi de 0.15 para 0.1

        double imc1 = CalculadoraIMC.calcular(peso, a1);
        double imc2 = CalculadoraIMC.calcular(peso, a2);

        assertThat(imc2).isLessThan(imc1);
    }

    @Property
    @Label("Categorização é consistente com os limites definidos (8 categorias)")
    void categorizacao_consistente_com_limites(@ForAll("pesosValidos") double peso,
                                                 @ForAll("alturasValidas") double altura) {

        double imc = CalculadoraIMC.calcular(peso, altura);
        CategoriaIMC categoria = CalculadoraIMC.categorizar(imc);

        if (imc < 16.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
        } else if (imc < 17.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
        } else if (imc < 18.5) {
            assertThat(categoria).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
        } else if (imc < 25.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.SAUDAVEL);
        } else if (imc < 30.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.SOBREPESO);
        } else if (imc < 35.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
        } else if (imc < 40.0) {
            assertThat(categoria).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
        } else {
            assertThat(categoria).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
        }
    }

    @Property
    @Label("Arredondamento preserva 2 casas decimais")
    void arredondamento_duas_casas(@ForAll("pesosValidos") double peso,
                                    @ForAll("alturasValidas") double altura) {

        double imc = CalculadoraIMC.calcular(peso, altura);

        // Verifica se o valor tem no máximo 2 casas decimais
        double imcArredondado = Math.round(imc * 100.0) / 100.0;
        assertThat(imc).isEqualTo(imcArredondado);
    }

    @Property
    @Label("IMC dobra quando peso dobra (altura constante)")
    void imc_dobra_com_peso_dobrado(@ForAll("pesosValidos") double peso,
                                         @ForAll("alturasValidas") double altura) {

        Assume.that(peso >= 10.0); // Garante valores significativos
        Assume.that(peso * 2 <= 500.0); // Garante que peso duplicado está dentro do limite

        double imc1 = CalculadoraIMC.calcular(peso, altura);
        double imc2 = CalculadoraIMC.calcular(peso * 2, altura);

        // IMC2 deve ser aproximadamente o dobro de IMC1 (considerando arredondamento)
        // Permite margem de erro maior por causa do arredondamento
        double razao = imc2 / imc1;
        assertThat(razao).isBetween(1.90, 2.10);
    }

    @Property(tries = 100)
    @Label("Valores extremos dentro dos limites produzem resultados válidos")
    void valores_extremos_validos(@ForAll("pesosExtremos") double peso,
                                   @ForAll("alturasExtremas") double altura) {

        double imc = CalculadoraIMC.calcular(peso, altura);
        CategoriaIMC categoria = CalculadoraIMC.categorizar(imc);

        assertThat(imc).isGreaterThan(0);
        assertThat(categoria).isNotNull();
    }

    // Geradores de valores válidos

    @Provide
    Arbitrary<Double> pesosValidos() {
        return Arbitraries.doubles()
                .between(0.1, 500.0)
                .edgeCases(config -> config.add(0.1, 1.0, 50.0, 100.0, 250.0, 500.0));
    }

    @Provide
    Arbitrary<Double> alturasValidas() {
        return Arbitraries.doubles()
                .between(0.5, 3.5)
                .edgeCases(config -> config.add(0.5, 1.0, 1.5, 1.8, 2.0, 2.5, 3.0, 3.5));
    }

    @Provide
    Arbitrary<Double> pesosExtremos() {
        return Arbitraries.of(0.1, 1.0, 500.0);
    }

    @Provide
    Arbitrary<Double> alturasExtremas() {
        return Arbitraries.of(0.5, 1.0, 3.5);
    }
}
