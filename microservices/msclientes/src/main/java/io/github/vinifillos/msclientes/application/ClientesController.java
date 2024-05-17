package io.github.vinifillos.msclientes.application;

import io.github.vinifillos.msclientes.application.dto.ClieteSaveDto;
import io.github.vinifillos.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
@Slf4j
public class ClientesController {

    private final ClienteService clienteService;

    @GetMapping
    private String status() {
        log.info("Obtendo o status de microservice de clientes");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClieteSaveDto clieteSaveDto) {
        Cliente cliente = clieteSaveDto.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
        Optional<Cliente> cliente = clienteService.getByCpf(cpf);
        if (cliente.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }
}
