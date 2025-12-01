package com.example.demo.service;

import com.example.demo.model.ClienteGranja;
import com.example.demo.repository.ClienteGranjaRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional; // Importação útil para Services

import java.util.List;

@Service
public class ClienteGranjaService {

    private final ClienteGranjaRepository repository;

    public ClienteGranjaService(ClienteGranjaRepository repository) {
        this.repository = repository;
    }

    public List<ClienteGranja> listarTodos() {
        return repository.findAll();
    }

    public List<ClienteGranja> buscarPorCpfParcial(String cpf) {
        return repository.findByCpfContaining(cpf);
    }

    public List<ClienteGranja> buscarPorCpf(String cpf) {
        List<ClienteGranja> clientes = repository.findByCpf(cpf);
        if (clientes.isEmpty()) {
            throw new RuntimeException("Nenhum pedido encontrado para o CPF/CNPJ: " + cpf);
        }
        return clientes;
    }

    // CORREÇÃO: Tratamento de erro de conversão para exibir a mensagem correta
    @Transactional
    public ClienteGranja salvar(ClienteGranja cliente) {
        try {
            return repository.save(cliente);
        } catch (DataIntegrityViolationException dive) {
            // Este catch é para unicidade/constraint failures
            throw new DataIntegrityViolationException(dive.getMessage());
        } catch (Exception ex) {
            // Intercepta o erro de CONVERSÃO DE TIPO (String para Integer na Quantidade)
            if (ex.getMessage() != null && ex.getMessage().contains("java.lang.NumberFormatException")) {
                // Lança uma exceção controlada para ser capturada pelo ApiExceptionHandler
                throw new RuntimeException("A quantidade deve ser numérica.");
            }
            throw new RuntimeException("Erro desconhecido ao salvar: " + ex.getMessage());
        }
    }

    // CORREÇÃO: Tratamento de erro de conversão para exibir a mensagem correta
    @Transactional
    public ClienteGranja atualizar(Long id, ClienteGranja clienteAtualizado) {
        ClienteGranja clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        try {
            // 1. Atualiza os dados
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setCpf(clienteAtualizado.getCpf());
            clienteExistente.setTipoProduto(clienteAtualizado.getTipoProduto());
            clienteExistente.setQuantidadeOvos(clienteAtualizado.getQuantidadeOvos());

            clienteExistente.setLogradouro(clienteAtualizado.getLogradouro());
            clienteExistente.setNumero(clienteAtualizado.getNumero());
            clienteExistente.setBairro(clienteAtualizado.getBairro());

            clienteExistente.setAnotacoes(clienteAtualizado.getAnotacoes());

            return repository.save(clienteExistente);

        } catch (Exception ex) {
            // Intercepta o erro de CONVERSÃO DE TIPO
            if (ex.getMessage() != null && ex.getMessage().contains("java.lang.NumberFormatException")) {
                throw new RuntimeException("A quantidade deve ser numérica.");
            }
            throw new RuntimeException("Erro ao atualizar pedido: " + ex.getMessage());
        }
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}