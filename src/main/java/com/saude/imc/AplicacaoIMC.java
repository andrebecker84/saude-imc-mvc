package com.saude.imc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot para cálculo de IMC.
 *
 * <p>Aplicação REST que fornece endpoint para cálculo do Índice de Massa Corporal (IMC)
 * com categorização segundo padrões da OMS.</p>
 *
 * <p><strong>Arquitetura:</strong></p>
 * <ul>
 *   <li><strong>MVC:</strong> Model-View-Controller para separação de responsabilidades</li>
 *   <li><strong>Ports & Adapters:</strong> Desacoplamento de infraestrutura via DIP</li>
 *   <li><strong>SOLID:</strong> Aplicação de todos os princípios SOLID</li>
 *   <li><strong>DRY:</strong> Eliminação de duplicação de código</li>
 *   <li><strong>Clean Code:</strong> Código limpo, legível e testável</li>
 * </ul>
 *
 * <p><strong>Endpoints disponíveis:</strong></p>
 * <ul>
 *   <li>GET /api/imc?peso={peso}&altura={altura}</li>
 * </ul>
 *
 * <p><strong>Tecnologias:</strong></p>
 * <ul>
 *   <li>Java 21</li>
 *   <li>Spring Boot 3.3.4</li>
 *   <li>JUnit 5 + Jqwik (testes)</li>
 *   <li>Mockito (mocks)</li>
 *   <li>JaCoCo (cobertura)</li>
 * </ul>
 *
 * @author André Becker
 * @version 1.1.0
 * @see com.saude.imc.controller.ControladorIMC
 * @see com.saude.imc.service.ServicoIMC
 */
@SpringBootApplication
public class AplicacaoIMC {

    /**
     * Método principal que inicializa a aplicação Spring Boot.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        SpringApplication.run(AplicacaoIMC.class, args);
    }
}
