package br.com.coderbank.movimentacaofinanceira.repositories;

import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.entities.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    List<Movimentacao> findByContaIdOrderByDataHora(UUID contaId);

    ContaCliente conta(ContaCliente contaCliente);



}
