package io.github.vinifillos.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vinifillos.mscartoes.domain.Cartao;
import io.github.vinifillos.mscartoes.domain.CartaoCliente;
import io.github.vinifillos.mscartoes.domain.dto.DadosSolicitacaoEmissaoCartaoDto;
import io.github.vinifillos.mscartoes.infra.repository.CartaoClienteRepository;
import io.github.vinifillos.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final CartaoClienteRepository cartaoClienteRepository;

    @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {

        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartaoDto dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartaoDto.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            CartaoCliente cartaoCliente = new CartaoCliente();
            cartaoCliente.setCartao(cartao);
            cartaoCliente.setCpf(dados.getCpf());
            cartaoCliente.setLimite(dados.getLimiteLiberado());
            cartaoClienteRepository.save(cartaoCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
