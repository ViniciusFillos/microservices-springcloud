package io.github.vinifillos.msavaliadorcredito.application;

import io.github.vinifillos.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.vinifillos.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesExceptions;
import io.github.vinifillos.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.github.vinifillos.msavaliadorcredito.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "/situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoClienteDto situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesExceptions e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacaoDto dados) {
        try {
            RetornoAvaliacaoClienteDto retornoAvaliacaoClienteDto = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoClienteDto);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesExceptions e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartaoDto dados) {
        try {
            ProtocoloSolicitacaoCartaoDto protocoloSolicitacaoCartaoDto = avaliadorCreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartaoDto);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
