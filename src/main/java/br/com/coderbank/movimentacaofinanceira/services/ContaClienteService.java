package br.com.coderbank.movimentacaofinanceira.services;

import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.entities.Movimentacao;
import br.com.coderbank.movimentacaofinanceira.entities.entienums.TipoMovimentacao;
import br.com.coderbank.movimentacaofinanceira.repositories.ContaClienteRepository;
import br.com.coderbank.movimentacaofinanceira.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class ContaClienteService {
    private final ContaClienteRepository contaClienteRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public ContaClienteService(ContaClienteRepository contaClienteRepository, MovimentacaoRepository movimentacaoRepository) {
        this.contaClienteRepository = contaClienteRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public ContaCliente criarConta (String titular, String cpf){
        ContaCliente contaCliente = new ContaCliente();
        contaCliente.setTitular(titular.trim());
        contaCliente.setCpf(cpf.trim());
        contaCliente.setSaldo(BigDecimal.ZERO);
        contaCliente.setNumeroConta(gerarNumeroConta());
        return contaClienteRepository.save(contaCliente);
    }

    private String gerarNumeroConta(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }


    public ContaCliente depositar(UUID idConta,  BigDecimal valor){
        ContaCliente conta = contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não  encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor do depósito deve ser maior do que zero.");
        }
        conta.setSaldo(conta.getSaldo().add(valor));

        movimentacaoRepository.save(new Movimentacao(
                conta,
                valor,
                TipoMovimentacao.DEPOSITO,
                LocalDateTime.now(),
                null
        ));
        return contaClienteRepository.save(conta);
    }


    public ContaCliente sacar(UUID idConta, BigDecimal valor) {
        ContaCliente conta = contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }

        movimentacaoRepository.save(new Movimentacao(
                conta,
                valor,
                TipoMovimentacao.SAQUE,
                LocalDateTime.now(),
                null
        ));

        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaClienteRepository.save(conta);


    }

    public ContaCliente consultarSaldo(UUID idConta) {
        return contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
    }

    public ContaCliente transferirEntreContas(UUID contaorigem, UUID contadestino, BigDecimal valor){

        ContaCliente contaOrigem = contaClienteRepository.findById(contaorigem)
                .orElseThrow(( () -> new RuntimeException("Conta Origem não encontrada.")));

        ContaCliente contaDestino = contaClienteRepository.findById(contadestino)
                .orElseThrow(( () -> new RuntimeException("Conta Destino não encontrada.")));

        if (contaOrigem.getId().equals(contaDestino.getId())) {
            throw new IllegalArgumentException("A contas devem ser diferentes.");
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para depósito.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));

        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaClienteRepository.save(contaOrigem);
        contaClienteRepository.save(contaDestino);

        movimentacaoRepository.save(new Movimentacao(
                contaOrigem,
                valor,
                TipoMovimentacao.TRANSFERENCIA_ENVIO,
                LocalDateTime.now(),
                contaDestino.getId()
        ));

        movimentacaoRepository.save(new Movimentacao(
                contaDestino,
                valor,
                TipoMovimentacao.TRANSFERENCIA_RECEBIMENTO,
                LocalDateTime.now(),
                contaOrigem.getId()
        ));



        return contaOrigem;

    }


}
