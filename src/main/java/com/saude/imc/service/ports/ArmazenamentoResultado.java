package com.saude.imc.service.ports;

/**
 * Porta (Port) para armazenamento de resultados de IMC.
 *
 * <p>Interface que define o contrato para persistência dos resultados
 * do cálculo de IMC, seguindo o padrão <strong>Ports & Adapters</strong>
 * (Arquitetura Hexagonal).</p>
 *
 * <p><strong>Princípios aplicados:</strong></p>
 * <ul>
 *   <li><strong>DIP (Dependency Inversion Principle):</strong> O domínio
 *       depende desta abstração, não de implementações concretas</li>
 *   <li><strong>OCP (Open/Closed Principle):</strong> Novos adaptadores
 *       podem ser criados sem modificar o código existente</li>
 *   <li><strong>ISP (Interface Segregation Principle):</strong> Interface
 *       coesa com responsabilidade única</li>
 * </ul>
 *
 * <p><strong>Implementações disponíveis:</strong></p>
 * <ul>
 *   <li>{@link com.saude.imc.service.adapters.ArmazenamentoLog} - Persiste em logs</li>
 * </ul>
 *
 * <p><strong>Implementações futuras possíveis:</strong></p>
 * <ul>
 *   <li>ArmazenamentoBancoDados - Persiste em banco relacional</li>
 *   <li>ArmazenamentoArquivo - Persiste em arquivo CSV/JSON</li>
 *   <li>ArmazenamentoAPI - Envia para API externa</li>
 * </ul>
 *
 * @see com.saude.imc.service.adapters.ArmazenamentoLog
 */
public interface ArmazenamentoResultado {

    /**
     * Persiste o resultado de um cálculo de IMC.
     *
     * <p>Implementações desta interface devem garantir que a persistência
     * seja realizada de forma confiável e não bloqueante quando possível.</p>
     *
     * @param peso peso em quilogramas usado no cálculo
     * @param altura altura em metros usada no cálculo
     * @param imc valor do IMC calculado e arredondado
     */
    void salvar(double peso, double altura, double imc);
}
