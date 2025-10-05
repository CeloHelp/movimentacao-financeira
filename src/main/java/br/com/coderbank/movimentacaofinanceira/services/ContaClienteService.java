package br.com.coderbank.movimentacaofinanceira.services;

import br.com.coderbank.movimentacaofinanceira.dtos.request.ContaClienteRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.ContaClienteResponseDTO;
import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.entities.Movimentacao;
import br.com.coderbank.movimentacaofinanceira.entities.entienums.TipoMovimentacao;
import br.com.coderbank.movimentacaofinanceira.exceptions.custom.ContaException;
import br.com.coderbank.movimentacaofinanceira.exceptions.custom.ValorInvalidoException;
import br.com.coderbank.movimentacaofinanceira.exceptions.errormensage.MensagensErro;
import br.com.coderbank.movimentacaofinanceira.repositories.ContaClienteRepository;
import br.com.coderbank.movimentacaofinanceira.repositories.MovimentacaoRepository;
import org.springframework.beans.BeanUtils;
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

    public ContaClienteResponseDTO criarConta (final ContaClienteRequestDTO contaClienteRequestDTO) {
        var conta = new ContaCliente();

        if(conta.getId() == null) {
            throw new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA);
        }

        BeanUtils.copyProperties(contaClienteRequestDTO, conta);
        contaClienteRepository.save(conta);

        return new ContaClienteResponseDTO(
                conta.getId(),
                conta.getTitular(),
                conta.getCpf(),
                conta.getNumeroConta(),
                conta.getAgencia(),
                conta.getSaldo()
        );






    }

    private String gerarNumeroConta(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }


    public ContaCliente depositar(UUID idConta, BigDecimal valor) {
        ContaCliente conta = buscarContaPorId(idConta);
        validarValorDeposito(valor);
        return atualizarSaldo(conta, valor);
    }

    private ContaCliente buscarContaPorId(UUID idConta) {
        return contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA));
    }

    private void validarValorDeposito(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(MensagensErro.VALOR_INVALIDO);
        }
    }

    private ContaCliente atualizarSaldo(ContaCliente conta, BigDecimal valor) {
        conta.setSaldo(conta.getSaldo().add(valor));
        return contaClienteRepository.save(conta);
    }


    public ContaCliente sacar(UUID idConta, BigDecimal valor) {
        ContaCliente conta = contaClienteRepository.findById(idConta)
                .orElseThrow(() -> new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(MensagensErro.VALOR_INVALIDO);
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new ValorInvalidoException(MensagensErro.SALDO_INSUFICIENTE);
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
                .orElseThrow(() -> new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA));
    }

    public ContaCliente transferirEntreContas(UUID contaorigem, UUID contadestino, BigDecimal valor){

        ContaCliente contaOrigem = contaClienteRepository.findById(contaorigem)
                .orElseThrow(( () -> new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA + "Conta Origem não encontrada.")));

        ContaCliente contaDestino = contaClienteRepository.findById(contadestino)
                .orElseThrow(( () -> new ContaException(MensagensErro.CONTA_NAO_ENCONTRADA + "Conta Destino não encontrada.")));

        if (contaOrigem.getId().equals(contaDestino.getId())) {
            throw new ContaException(MensagensErro.CONTAS_IGUAIS);
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new ValorInvalidoException("Saldo insuficiente para depósito.");
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
