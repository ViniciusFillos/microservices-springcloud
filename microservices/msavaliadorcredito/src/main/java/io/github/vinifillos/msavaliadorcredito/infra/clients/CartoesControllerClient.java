package io.github.vinifillos.msavaliadorcredito.infra.clients;

import io.github.vinifillos.msavaliadorcredito.domain.dto.CartaoClienteDto;
import io.github.vinifillos.msavaliadorcredito.domain.dto.CartaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesControllerClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoClienteDto>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<CartaoDto>> getCartoesRendaAte(@RequestParam("renda") Long renda);
}
