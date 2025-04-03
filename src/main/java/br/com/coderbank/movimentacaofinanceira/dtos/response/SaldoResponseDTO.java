package br.com.coderbank.movimentacaofinanceira.dtos.response;

import java.math.BigDecimal;

public record SaldoResponseDTO(
        String titular,
        BigDecimal saldo
) {
}
