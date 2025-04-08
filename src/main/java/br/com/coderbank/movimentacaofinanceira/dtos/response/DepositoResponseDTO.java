package br.com.coderbank.movimentacaofinanceira.dtos.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositoResponseDTO(
        @NotNull(message = "Informe um valor para o depósito")
        @Positive
        BigDecimal valor
) {
}
