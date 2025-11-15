package com.saude.imc.service;

import com.saude.imc.domain.CategoriaIMC;
import com.saude.imc.dto.RespostaIMC;
import com.saude.imc.service.ports.ArmazenamentoResultado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Serviço IMC")
class ServicoIMCTest {

    @Mock
    private ArmazenamentoResultado armazenamento;

    private ServicoIMC servico;

    @BeforeEach
    void setUp() {
        servico = new ServicoIMC(armazenamento);
    }

    @Nested
    @DisplayName("Cálculo e Persistência")
    class CalculoPersistencia {

        @Test
        @DisplayName("Calcula IMC e persiste resultado corretamente")
        void calcula_e_persiste_resultado() {
            RespostaIMC resposta = servico.calcular(80.0, 1.80);

            assertThat(resposta).isNotNull();
            assertThat(resposta.getPeso()).isEqualTo(80.0);
            assertThat(resposta.getAltura()).isEqualTo(1.80);
            assertThat(resposta.getImc()).isEqualTo(24.69);
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.SAUDAVEL);

            verify(armazenamento, times(1)).salvar(80.0, 1.80, 24.69);
        }

        @Test
        @DisplayName("Persiste valores exatos calculados")
        void persiste_valores_exatos() {
            ArgumentCaptor<Double> pesoCaptor = ArgumentCaptor.forClass(Double.class);
            ArgumentCaptor<Double> alturaCaptor = ArgumentCaptor.forClass(Double.class);
            ArgumentCaptor<Double> imcCaptor = ArgumentCaptor.forClass(Double.class);

            servico.calcular(70.0, 1.75);

            verify(armazenamento).salvar(pesoCaptor.capture(), alturaCaptor.capture(), imcCaptor.capture());

            assertThat(pesoCaptor.getValue()).isEqualTo(70.0);
            assertThat(alturaCaptor.getValue()).isEqualTo(1.75);
            assertThat(imcCaptor.getValue()).isEqualTo(22.86);
        }

        @Test
        @DisplayName("Persiste mesmo quando categoria é OBESIDADE")
        void persiste_caso_obesidade() {
            servico.calcular(100.0, 1.70);

            verify(armazenamento, times(1)).salvar(eq(100.0), eq(1.70), anyDouble());
        }
    }

    @Nested
    @DisplayName("Validações")
    class Validacoes {

        @Test
        @DisplayName("Rejeita peso inválido e não persiste")
        void rejeita_peso_invalido() {
            assertThatThrownBy(() -> servico.calcular(0, 1.70))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Peso inválido");

            verify(armazenamento, never()).salvar(anyDouble(), anyDouble(), anyDouble());
        }

        @Test
        @DisplayName("Rejeita altura inválida e não persiste")
        void rejeita_altura_invalida() {
            assertThatThrownBy(() -> servico.calcular(80.0, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Altura inválida");

            verify(armazenamento, never()).salvar(anyDouble(), anyDouble(), anyDouble());
        }

        @Test
        @DisplayName("Rejeita valores negativos")
        void rejeita_valores_negativos() {
            assertThatThrownBy(() -> servico.calcular(-10, 1.70))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThatThrownBy(() -> servico.calcular(80.0, -1.70))
                    .isInstanceOf(IllegalArgumentException.class);

            verify(armazenamento, never()).salvar(anyDouble(), anyDouble(), anyDouble());
        }
    }

    @Nested
    @DisplayName("Categorização (8 categorias)")
    class Categorizacao {

        @Test
        @DisplayName("Retorna categoria MAGREZA_GRAVE corretamente")
        void retorna_magreza_grave() {
            RespostaIMC resposta = servico.calcular(45.0, 1.70); // IMC = 15.57
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.MAGREZA_GRAVE);
        }

        @Test
        @DisplayName("Retorna categoria SAUDAVEL corretamente")
        void retorna_saudavel() {
            RespostaIMC resposta = servico.calcular(70.0, 1.70); // IMC = 24.22
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.SAUDAVEL);
        }

        @Test
        @DisplayName("Retorna categoria SOBREPESO corretamente")
        void retorna_sobrepeso() {
            RespostaIMC resposta = servico.calcular(80.0, 1.70); // IMC = 27.68
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.SOBREPESO);
        }

        @Test
        @DisplayName("Retorna categoria OBESIDADE_GRAU_I corretamente")
        void retorna_obesidade_grau_i() {
            RespostaIMC resposta = servico.calcular(100.0, 1.70); // IMC = 34.60
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_I);
        }

        @Test
        @DisplayName("Retorna categoria OBESIDADE_GRAU_III corretamente")
        void retorna_obesidade_grau_iii() {
            RespostaIMC resposta = servico.calcular(120.0, 1.70); // IMC = 41.52
            assertThat(resposta.getCategoria()).isEqualTo(CategoriaIMC.OBESIDADE_GRAU_III);
        }
    }
}
