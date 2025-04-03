package br.com.coderbank.movimentacaofinanceira.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositoRequestDTO(
        @NotNull(message = "O valor do Depósito deve ser informado.")
        @DecimalMin(value = "0.01", message = "O valor do depósito deve ser mairo que zero.")
        BigDecimal valor
) {
}
