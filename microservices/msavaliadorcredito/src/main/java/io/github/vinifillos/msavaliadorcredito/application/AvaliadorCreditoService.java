package io.github.vinifillos.msavaliadorcredito.application;

import io.github.vinifillos.msavaliadorcredito.domain.dto.DadosClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.SituacaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.infra.clients.ClienteControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteControllerClient clienteControllerClient;

    public SituacaoClienteDto obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosClienteDto> dadosCliente = clienteControllerClient.dadosCliente(cpf);

        return SituacaoClienteDto
                .builder()
                .cliente(dadosCliente.getBody())
                .build();
    }
}
