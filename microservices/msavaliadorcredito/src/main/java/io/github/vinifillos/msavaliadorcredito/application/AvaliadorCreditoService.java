package io.github.vinifillos.msavaliadorcredito.application;

import feign.FeignException;
import io.github.vinifillos.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.vinifillos.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesExceptions;
import io.github.vinifillos.msavaliadorcredito.domain.dto.*;
import io.github.vinifillos.msavaliadorcredito.infra.clients.CartoesControllerClient;
import io.github.vinifillos.msavaliadorcredito.infra.clients.ClienteControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteControllerClient clienteControllerClient;

    private final CartoesControllerClient cartoesControllerClient;

    public SituacaoClienteDto obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExceptions {
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

    public RetornoAvaliacaoClienteDto realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExceptions {
        try {
            ResponseEntity<DadosClienteDto> dadosClienteResponse = clienteControllerClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoDto>> cartoesResponse = cartoesControllerClient.getCartoesRendaAte(renda);

            List<CartaoDto> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosClienteDto dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovadoDto aprovadoDto = new CartaoAprovadoDto();
                aprovadoDto.setCartao(cartao.getNome());
                aprovadoDto.setBandeira(cartao.getBandeira());
                aprovadoDto.setLimiteAprovado(limiteAprovado);

                return aprovadoDto;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoClienteDto(listaCartoesAprovados);

        } catch (FeignException.FeignClientException ex) {
            Integer status = ex.status();
            if (HttpStatus.NOT_FOUND.value() == status) throw new DadosClienteNotFoundException();
            throw new ErroComunicacaoMicroservicesExceptions(ex.getMessage(), ex.status());
        }
    }
}
