package com.saude.imc.controller;

import com.saude.imc.dto.RespostaIMC;
import com.saude.imc.service.ServicoIMC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para cálculo de IMC.
 *
 * <p>Camada de apresentação (Controller) no padrão MVC, responsável por:</p>
 * <ul>
 *   <li>Receber requisições HTTP</li>
 *   <li>Validar parâmetros de entrada</li>
 *   <li>Delegar processamento para {@link ServicoIMC}</li>
 *   <li>Tratar exceções e retornar respostas HTTP adequadas</li>
 * </ul>
 *
 * <p><strong>Endpoint disponível:</strong></p>
 * <ul>
 *   <li>GET /api/imc?peso={peso}&altura={altura}</li>
 * </ul>
 *
 * <p><strong>Exemplo de requisição:</strong></p>
 * <pre>
 * GET http://localhost:8080/api/imc?peso=80&altura=1.80
 * </pre>
 *
 * <p><strong>Exemplo de resposta bem-sucedida (200 OK):</strong></p>
 * <pre>{@code
 * {
 *   "peso": 80.0,
 *   "altura": 1.8,
 *   "imc": 24.69,
 *   "categoria": "NORMAL"
 * }
 * }</pre>
 *
 * <p><strong>Exemplo de resposta de erro (400 Bad Request):</strong></p>
 * <pre>{@code
 * {
 *   "erro": "Peso inválido: 0.00 kg. Informe um valor > 0.0 e ≤ 500.0 kg."
 * }
 * }</pre>
 *
 * @see ServicoIMC
 * @see RespostaIMC
 */
@Slf4j
@RestController
@RequestMapping("/api/imc")
@RequiredArgsConstructor
public class ControladorIMC {

    private final ServicoIMC servico;

    /**
     * Calcula o IMC com base no peso e altura fornecidos.
     *
     * @param peso peso em quilogramas (0 {@literal <} peso ≤ 500)
     * @param altura altura em metros (0 {@literal <} altura ≤ 3.5)
     * @return resposta HTTP 200 OK com {@link RespostaIMC} contendo IMC e categoria
     * @throws IllegalArgumentException se peso ou altura forem inválidos (retorna 400 Bad Request)
     */
    @GetMapping
    public ResponseEntity<RespostaIMC> calcular(
            @RequestParam(name = "peso") double peso,
            @RequestParam(name = "altura") double altura) {

        log.info("Requisição recebida: GET /api/imc?peso={}&altura={}", peso, altura);

        RespostaIMC resposta = servico.calcular(peso, altura);

        log.info("Resposta enviada: IMC={}, Categoria={}", resposta.getImc(), resposta.getCategoria());
        return ResponseEntity.ok(resposta);
    }

    /**
     * Tratador global de exceções para validação de entrada.
     *
     * <p>Captura {@link IllegalArgumentException} lançadas pelo
     * {@link com.saude.imc.util.ValidadorEntrada} e retorna resposta
     * HTTP 400 Bad Request com mensagem de erro.</p>
     *
     * @param ex exceção capturada
     * @return resposta HTTP 400 com mensagem de erro
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(IllegalArgumentException ex) {
        log.warn("Erro de validação: {}", ex.getMessage());

        Map<String, String> erro = Map.of("erro", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    /**
     * Tratador global de exceções genéricas.
     *
     * <p>Captura quaisquer exceções não previstas e retorna
     * HTTP 500 Internal Server Error.</p>
     *
     * @param ex exceção capturada
     * @return resposta HTTP 500 com mensagem de erro genérica
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericError(Exception ex) {
        log.error("Erro inesperado: ", ex);

        Map<String, String> erro = Map.of(
                "erro", "Erro interno do servidor",
                "detalhe", ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}
