package br.com.coderbank.movimentacaofinanceira.entities;

import br.com.coderbank.movimentacaofinanceira.entities.entienums.TipoMovimentacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "conta")
    private ContaCliente conta;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private LocalDateTime dataHora;

    //preencher apenas em caso de transferência

    private UUID contaDestino;

    //Construtores

    public Movimentacao() {}

    public Movimentacao(ContaCliente conta, BigDecimal valor, TipoMovimentacao tipo, LocalDateTime dataHora, UUID contaDestino) {
        this.conta = conta;
        this.valor = valor;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.contaDestino = contaDestino;
    }

    public UUID getId() {
        return id;
    }

    public ContaCliente getConta() {
        return conta;
    }

    public void setConta(ContaCliente conta) {
        this.conta = conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public UUID getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(UUID contaDestino) {
        this.contaDestino = contaDestino;
    }
}
