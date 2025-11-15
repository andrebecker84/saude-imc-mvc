package com.saude.imc.util;

import com.saude.imc.domain.CategoriaIMC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Calculadora IMC")
class CalculadoraIMCTest {

    @Nested
    @DisplayName("Cálculo de IMC")
    class CalculoIMC {

        @ParameterizedTest(name = "peso={0}kg, altura={1}m -> IMC={2}")
        @CsvSource({
                "80.0, 1.80, 24.69",
                "70.0, 1.75, 22.86",
                "90.0, 1.65, 33.06",
                "50.0, 1.60, 19.53",
                "100.0, 1.90, 27.70"
        })
        void calcula_imc_corretamente(double peso, double altura, double imcEsperado) {
            double imc = CalculadoraIMC.calcular(peso, altura);
            assertThat(imc).isEqualTo(imcEsperado);
        }

        @Test
        @DisplayName("Arredonda para 2 casas decimais com HALF_UP")
        void arredonda_duas_casas_decimais() {
            double imc = CalculadoraIMC.calcular(80, 1.80);
            assertThat(imc).isEqualTo(24.69);
        }
    }

    @Nested
    @DisplayName("Validação de Peso")
    class ValidacaoPeso {

        @ParameterizedTest(name = "peso inválido: {0}")
        @ValueSource(doubles = {-10, -1, 0, 501, 600})
        void rejeita_peso_fora_do_intervalo(double pesoInvalido) {
            assertThatThrownBy(() -> CalculadoraIMC.calcular(pesoInvalido, 1.7))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Peso inválido");
        }

        @ParameterizedTest(name = "peso válido: {0}kg")
        @ValueSource(doubles = {0.1, 1, 50, 100, 250, 500})
        void aceita_peso_valido(double pesoValido) {
            double imc = CalculadoraIMC.calcular(pesoValido, 1.7);
            assertThat(imc).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Validação de Altura")
    class ValidacaoAltura {

        @ParameterizedTest(name = "altura inválida: {0}")
        @ValueSource(doubles = {-1, 0, 3.6, 4.0})
        void rejeita_altura_fora_do_intervalo(double alturaInvalida) {
            assertThatThrownBy(() -> CalculadoraIMC.calcular(80, alturaInvalida))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Altura inválida");
        }

        @ParameterizedTest(name = "altura válida: {0}m")
        @ValueSource(doubles = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5})
        void aceita_altura_valida(double alturaValida) {
            double imc = CalculadoraIMC.calcular(80, alturaValida);
            assertThat(imc).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Categorização de IMC (8 categorias - código original)")
    class CategorizacaoIMC {

        @Test
        @DisplayName("Categoriza como MAGREZA_GRAVE quando IMC < 16.0")
        void categoriza_magreza_grave() {
            assertThat(CalculadoraIMC.categorizar(10.0)).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
            assertThat(CalculadoraIMC.categorizar(15.0)).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
            assertThat(CalculadoraIMC.categorizar(15.99)).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
        }

        @Test
        @DisplayName("Categoriza como MAGREZA_MODERADA quando 16.0 <= IMC < 17.0")
        void categoriza_magreza_moderada() {
            assertThat(CalculadoraIMC.categorizar(16.0)).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
            assertThat(CalculadoraIMC.categorizar(16.5)).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
            assertThat(CalculadoraIMC.categorizar(16.99)).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
        }

        @Test
        @DisplayName("Categoriza como MAGREZA_LEVE quando 17.0 <= IMC < 18.5")
        void categoriza_magreza_leve() {
            assertThat(CalculadoraIMC.categorizar(17.0)).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
            assertThat(CalculadoraIMC.categorizar(18.0)).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
            assertThat(CalculadoraIMC.categorizar(18.49)).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
        }

        @Test
        @DisplayName("Categoriza como SAUDAVEL quando 18.5 <= IMC < 25.0")
        void categoriza_saudavel() {
            assertThat(CalculadoraIMC.categorizar(18.5)).isEqualTo(CategoriaIMC.SAUDAVEL);
            assertThat(CalculadoraIMC.categorizar(20.0)).isEqualTo(CategoriaIMC.SAUDAVEL);
            assertThat(CalculadoraIMC.categorizar(24.99)).isEqualTo(CategoriaIMC.SAUDAVEL);
        }

        @Test
        @DisplayName("Categoriza como SOBREPESO quando 25.0 <= IMC < 30.0")
        void categoriza_sobrepeso() {
            assertThat(CalculadoraIMC.categorizar(25.0)).isEqualTo(CategoriaIMC.SOBREPESO);
            assertThat(CalculadoraIMC.categorizar(27.5)).isEqualTo(CategoriaIMC.SOBREPESO);
            assertThat(CalculadoraIMC.categorizar(29.99)).isEqualTo(CategoriaIMC.SOBREPESO);
        }

        @Test
        @DisplayName("Categoriza como OBESIDADE_GRAU_I quando 30.0 <= IMC < 35.0")
        void categoriza_obesidade_grau_i() {
            assertThat(CalculadoraIMC.categorizar(30.0)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
            assertThat(CalculadoraIMC.categorizar(32.5)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
            assertThat(CalculadoraIMC.categorizar(34.99)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
        }

        @Test
        @DisplayName("Categoriza como OBESIDADE_GRAU_II quando 35.0 <= IMC < 40.0")
        void categoriza_obesidade_grau_ii() {
            assertThat(CalculadoraIMC.categorizar(35.0)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
            assertThat(CalculadoraIMC.categorizar(37.5)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
            assertThat(CalculadoraIMC.categorizar(39.99)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
        }

        @Test
        @DisplayName("Categoriza como OBESIDADE_GRAU_III quando IMC >= 40.0")
        void categoriza_obesidade_grau_iii() {
            assertThat(CalculadoraIMC.categorizar(40.0)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
            assertThat(CalculadoraIMC.categorizar(45.0)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
            assertThat(CalculadoraIMC.categorizar(50.0)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
        }

        @Nested
        @DisplayName("Fronteiras de Categoria (Análise de Limites - 7 fronteiras)")
        class FronteirasCategoria {

            @Test
            @DisplayName("Fronteira MAGREZA_GRAVE -> MAGREZA_MODERADA (15.99 -> 16.00)")
            void fronteira_grave_moderada() {
                assertThat(CalculadoraIMC.categorizar(15.99)).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
                assertThat(CalculadoraIMC.categorizar(16.00)).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
            }

            @Test
            @DisplayName("Fronteira MAGREZA_MODERADA -> MAGREZA_LEVE (16.99 -> 17.00)")
            void fronteira_moderada_leve() {
                assertThat(CalculadoraIMC.categorizar(16.99)).isEqualTo(CategoriaIMC.MAGREZA_MODERADA);
                assertThat(CalculadoraIMC.categorizar(17.00)).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
            }

            @Test
            @DisplayName("Fronteira MAGREZA_LEVE -> SAUDAVEL (18.49 -> 18.50)")
            void fronteira_leve_saudavel() {
                assertThat(CalculadoraIMC.categorizar(18.49)).isEqualTo(CategoriaIMC.MAGREZA_LEVE);
                assertThat(CalculadoraIMC.categorizar(18.50)).isEqualTo(CategoriaIMC.SAUDAVEL);
            }

            @Test
            @DisplayName("Fronteira SAUDAVEL -> SOBREPESO (24.99 -> 25.00)")
            void fronteira_saudavel_sobrepeso() {
                assertThat(CalculadoraIMC.categorizar(24.99)).isEqualTo(CategoriaIMC.SAUDAVEL);
                assertThat(CalculadoraIMC.categorizar(25.00)).isEqualTo(CategoriaIMC.SOBREPESO);
            }

            @Test
            @DisplayName("Fronteira SOBREPESO -> OBESIDADE_GRAU_I (29.99 -> 30.00)")
            void fronteira_sobrepeso_obesidade_i() {
                assertThat(CalculadoraIMC.categorizar(29.99)).isEqualTo(CategoriaIMC.SOBREPESO);
                assertThat(CalculadoraIMC.categorizar(30.00)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
            }

            @Test
            @DisplayName("Fronteira OBESIDADE_GRAU_I -> OBESIDADE_GRAU_II (34.99 -> 35.00)")
            void fronteira_obesidade_i_ii() {
                assertThat(CalculadoraIMC.categorizar(34.99)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
                assertThat(CalculadoraIMC.categorizar(35.00)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
            }

            @Test
            @DisplayName("Fronteira OBESIDADE_GRAU_II -> OBESIDADE_GRAU_III (39.99 -> 40.00)")
            void fronteira_obesidade_ii_iii() {
                assertThat(CalculadoraIMC.categorizar(39.99)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_II);
                assertThat(CalculadoraIMC.categorizar(40.00)).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
            }
        }
    }

    @Nested
    @DisplayName("Casos de Partição Equivalente (8 categorias)")
    class ParticoesEquivalentes {

        @ParameterizedTest(name = "{0}kg, {1}m -> {2}")
        @CsvSource({
                "43.0, 1.70, MAGREZA_GRAVE",      // IMC = 14.87
                "47.0, 1.70, MAGREZA_MODERADA",   // IMC = 16.26
                "51.0, 1.70, MAGREZA_LEVE",       // IMC = 17.65
                "70.0, 1.70, SAUDAVEL",           // IMC = 24.22
                "78.0, 1.70, SOBREPESO",          // IMC = 26.99
                "91.0, 1.70, OBESIDADE_GRAU_I",   // IMC = 31.49
                "103.0, 1.70, OBESIDADE_GRAU_II", // IMC = 35.64
                "120.0, 1.70, OBESIDADE_GRAU_III" // IMC = 41.52
        })
        void calcula_e_categoriza_corretamente(double peso, double altura, CategoriaIMC categoriaEsperada) {
            double imc = CalculadoraIMC.calcular(peso, altura);
            CategoriaIMC categoria = CalculadoraIMC.categorizar(imc);
            assertThat(categoria).isEqualTo(categoriaEsperada);
        }
    }
}
