package com.example.demo.controller;

import com.example.demo.model.ClienteGranja;
import com.example.demo.service.ClienteGranjaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteGranjaController {

    private final ClienteGranjaService service;

    public ClienteGranjaController(ClienteGranjaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClienteGranja> listar() {
        return service.listarTodos();
    }

    @GetMapping("/buscar-cpf-parcial")
    public List<ClienteGranja> buscarPorCpfParcial(@RequestParam String cpf) {
        return service.buscarPorCpfParcial(cpf);
    }

    @GetMapping("/buscar-cpf")
    public List<ClienteGranja> buscarPorCpf(@RequestParam String cpf) {
        return service.buscarPorCpf(cpf);
    }

    @PostMapping
    public ResponseEntity<ClienteGranja> criar(@Valid @RequestBody ClienteGranja cliente) {
        ClienteGranja salvo = service.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteGranja> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteGranja cliente) {

        ClienteGranja atualizado = service.atualizar(id, cliente);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}