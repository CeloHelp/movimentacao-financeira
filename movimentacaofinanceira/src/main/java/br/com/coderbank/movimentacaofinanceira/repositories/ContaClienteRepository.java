package br.com.coderbank.movimentacaofinanceira.repositories;

import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaClienteRepository extends JpaRepository<ContaCliente, UUID> {

    boolean existsBycpf(String cpf);
}
