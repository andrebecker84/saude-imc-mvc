package com.saude.imc.service;

import com.saude.imc.domain.CategoriaIMC;
import com.saude.imc.dto.RespostaIMC;
import com.saude.imc.service.ports.ArmazenamentoResultado;
import com.saude.imc.util.CalculadoraIMC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço de domínio para cálculo de IMC.
 *
 * <p>Camada de serviço que orquestra a lógica de negócio, aplicando o padrão MVC.
 * Responsável por coordenar o cálculo, categorização e persistência do IMC.</p>
 *
 * <p><strong>Responsabilidades:</strong></p>
 * <ul>
 *   <li>Orquestrar o cálculo do IMC delegando para {@link CalculadoraIMC}</li>
 *   <li>Persistir resultados através da porta {@link ArmazenamentoResultado}</li>
 *   <li>Construir a resposta completa com todos os dados relevantes</li>
 * </ul>
 *
 * <p>Aplica os princípios:</p>
 * <ul>
 *   <li><strong>SRP:</strong> Foco exclusivo na orquestração do cálculo de IMC</li>
 *   <li><strong>DIP:</strong> Depende de abstrações ({@link ArmazenamentoResultado}) não de implementações</li>
 *   <li><strong>OCP:</strong> Aberto para extensão (novos adapters) fechado para modificação</li>
 * </ul>
 *
 * @see CalculadoraIMC
 * @see ArmazenamentoResultado
 * @see RespostaIMC
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoIMC {

    private final ArmazenamentoResultado armazenamento;

    /**
     * Calcula o IMC, categoriza, persiste e retorna a resposta completa.
     *
     * <p>Fluxo de execução:</p>
     * <ol>
     *   <li>Delega o cálculo para {@link CalculadoraIMC#calcular(double, double)}</li>
     *   <li>Persiste o resultado através da porta {@link ArmazenamentoResultado}</li>
     *   <li>Categoriza o IMC através de {@link CalculadoraIMC#categorizar(double)}</li>
     *   <li>Constrói e retorna {@link RespostaIMC} completa</li>
     * </ol>
     *
     * @param peso peso em quilogramas (0 {@literal <} peso ≤ 500)
     * @param altura altura em metros (0 {@literal <} altura ≤ 3.5)
     * @return resposta contendo IMC calculado e categoria
     * @throws IllegalArgumentException se peso ou altura forem inválidos
     */
    public RespostaIMC calcular(double peso, double altura) {
        log.debug("Calculando IMC para peso={} kg e altura={} m", peso, altura);

        double imc = CalculadoraIMC.calcular(peso, altura);
        CategoriaIMC categoria = CalculadoraIMC.categorizar(imc);

        armazenamento.salvar(peso, altura, imc);

        RespostaIMC resposta = RespostaIMC.builder()
                .peso(peso)
                .altura(altura)
                .imc(imc)
                .categoria(categoria)
                .build();

        log.debug("IMC calculado: {} - Categoria: {}", imc, categoria);
        return resposta;
    }
}
