package com.example.demo.repository;

import com.example.demo.model.ClienteGranja;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteGranjaRepository extends JpaRepository<ClienteGranja, Long> {

    // 1. Busca dinâmica (mantida)
    List<ClienteGranja> findByCpfContaining(String cpf);

    // 2. Busca exata (ATUALIZADO para retornar uma LISTA, permitindo múltiplos pedidos)
    List<ClienteGranja> findByCpf(String cpf);


}