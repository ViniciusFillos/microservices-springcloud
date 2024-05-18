package io.github.vinifillos.mscartoes.application;

import io.github.vinifillos.mscartoes.application.dto.CartaoSaveDto;
import io.github.vinifillos.mscartoes.application.dto.CartaoPorClienteDto;
import io.github.vinifillos.mscartoes.domain.Cartao;
import io.github.vinifillos.mscartoes.domain.CartaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("cartoes")
public class CartaoController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveDto cartaoDto) {
        Cartao cartao = cartaoDto.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoPorClienteDto>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<CartaoCliente> list = clienteCartaoService.listCartaoByCpf(cpf);
        List<CartaoPorClienteDto> resultList = list.stream()
                .map(CartaoPorClienteDto::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }
}
