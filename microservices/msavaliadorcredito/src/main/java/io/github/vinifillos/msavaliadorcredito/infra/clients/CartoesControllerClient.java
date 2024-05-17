package io.github.vinifillos.msavaliadorcredito.infra.clients;

import io.github.vinifillos.msavaliadorcredito.domain.dto.CartaoClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesControllerClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoClienteDto>> getCartoesByCliente(@RequestParam("cpf") String cpf);
}
