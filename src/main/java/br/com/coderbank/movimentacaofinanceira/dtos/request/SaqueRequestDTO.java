package br.com.coderbank.movimentacaofinanceira.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SaqueRequestDTO(
        @NotNull(message = "O valor do saque deve ser informado.")
        @DecimalMin(value = "0.01", message = "O valor do saque deve ser maior que zero.")
        BigDecimal valor
) {
}
