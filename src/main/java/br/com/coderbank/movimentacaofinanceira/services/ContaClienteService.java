package br.com.coderbank.movimentacaofinanceira.services;

import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.repositories.ContaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
public class ContaClienteService {
    private final ContaClienteRepository contaClienteRepository;

    @Autowired
    public ContaClienteService(ContaClienteRepository contaClienteRepository) {
        this.contaClienteRepository = contaClienteRepository;
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
        ContaCliente contaCliente = contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não  encontrada."));

        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor do depósito deve ser maior do que zero.");
        }
        contaCliente.setSaldo(contaCliente.getSaldo().add(valor));
        return contaClienteRepository.save(contaCliente);
    }
}
