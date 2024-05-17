package io.github.vinifillos.msavaliadorcredito.application;

import feign.FeignException;
import io.github.vinifillos.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.vinifillos.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesExceptions;
import io.github.vinifillos.msavaliadorcredito.domain.dto.CartaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.DadosClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.SituacaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.infra.clients.CartoesControllerClient;
import io.github.vinifillos.msavaliadorcredito.infra.clients.ClienteControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteControllerClient clienteControllerClient;

    private final CartoesControllerClient cartoesControllerClient;

    public SituacaoClienteDto obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExceptions{
        try {
            ResponseEntity<DadosClienteDto> dadosCliente = clienteControllerClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoClienteDto>> cartoes = cartoesControllerClient.getCartoesByCliente(cpf);

            return SituacaoClienteDto
                    .builder()
                    .cliente(dadosCliente.getBody())
                    .cartoes(cartoes.getBody())
                    .build();
        } catch (FeignException.FeignClientException ex) {
            Integer status = ex.status();
            if (HttpStatus.NOT_FOUND.value() == status) throw new DadosClienteNotFoundException();
            throw new ErroComunicacaoMicroservicesExceptions(ex.getMessage(), ex.status());
        }

    }
}
