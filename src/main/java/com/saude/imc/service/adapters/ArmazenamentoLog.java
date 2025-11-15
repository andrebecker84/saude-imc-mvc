package com.saude.imc.service.adapters;

import com.saude.imc.service.ports.ArmazenamentoResultado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Adaptador (Adapter) para armazenamento de resultados via logging.
 *
 * <p>Implementação da porta {@link ArmazenamentoResultado} que persiste
 * os resultados do cálculo de IMC através do sistema de logs da aplicação.</p>
 *
 * <p><strong>Características:</strong></p>
 * <ul>
 *   <li>Não bloqueante</li>
 *   <li>Adequado para desenvolvimento e monitoramento</li>
 *   <li>Pode ser substituído por outros adapters em produção</li>
 * </ul>
 *
 * <p><strong>Exemplo de log gerado:</strong></p>
 * <pre>
 * INFO  - IMC calculado -> peso=80.0 kg, altura=1.8 m, imc=24.69
 * </pre>
 *
 * <p>Esta implementação aplica o padrão <strong>Adapter</strong> da arquitetura
 * Ports & Adapters, permitindo que a infraestrutura (logging) seja substituída
 * sem impactar o domínio.</p>
 *
 * @see ArmazenamentoResultado
 */
@Slf4j
@Component
public class ArmazenamentoLog implements ArmazenamentoResultado {

    /**
     * Persiste o resultado do cálculo de IMC no sistema de logs.
     *
     * <p>Registra uma entrada de log nível INFO contendo peso, altura e IMC calculado.</p>
     *
     * @param peso peso em quilogramas usado no cálculo
     * @param altura altura em metros usada no cálculo
     * @param imc valor do IMC calculado e arredondado
     */
    @Override
    public void salvar(double peso, double altura, double imc) {
        log.info("IMC calculado -> peso={} kg, altura={} m, imc={}", peso, altura, imc);
    }
}
