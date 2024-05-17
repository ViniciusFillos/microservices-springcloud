package io.github.vinifillos.msavaliadorcredito.application;

import io.github.vinifillos.msavaliadorcredito.domain.dto.SituacaoClienteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SituacaoClienteDto> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        SituacaoClienteDto situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);
    }
}
