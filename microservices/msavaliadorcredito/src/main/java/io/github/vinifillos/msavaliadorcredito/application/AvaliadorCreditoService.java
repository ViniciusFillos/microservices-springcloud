package io.github.vinifillos.msavaliadorcredito.application;

import io.github.vinifillos.msavaliadorcredito.domain.dto.CartaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.DadosClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.SituacaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.infra.clients.CartoesControllerClient;
import io.github.vinifillos.msavaliadorcredito.infra.clients.ClienteControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteControllerClient clienteControllerClient;

    private final CartoesControllerClient cartoesControllerClient;

    public SituacaoClienteDto obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosClienteDto> dadosCliente = clienteControllerClient.dadosCliente(cpf);

        ResponseEntity<List<CartaoClienteDto>> cartoes = cartoesControllerClient.getCartoesByCliente(cpf);

        return SituacaoClienteDto
                .builder()
                .cliente(dadosCliente.getBody())
                .cartoes(cartoes.getBody())
                .build();
    }
}
