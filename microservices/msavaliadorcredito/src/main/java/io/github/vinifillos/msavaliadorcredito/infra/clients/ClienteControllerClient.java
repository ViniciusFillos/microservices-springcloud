package io.github.vinifillos.msavaliadorcredito.infra.clients;

import io.github.vinifillos.msavaliadorcredito.domain.dto.DadosClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteControllerClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosClienteDto> dadosCliente(@RequestParam("cpf") String cpf);

}
