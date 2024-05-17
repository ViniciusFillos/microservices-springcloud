package io.github.vinifillos.mscartoes.application;

import io.github.vinifillos.mscartoes.domain.CartaoCliente;
import io.github.vinifillos.mscartoes.infra.repository.CartaoClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final CartaoClienteRepository cartaoClienteRepository;

    @Transactional
    public List<CartaoCliente> listCartaoByCpf(String cpf) {
        return cartaoClienteRepository.findByCpf(cpf);
    }
}
