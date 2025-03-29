package br.com.coderbank.movimentacaofinanceira.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaClienteResponseDTO(
        UUID id,
        String titular,
        String cpf,
        String numeroConta,
        Integer agencia,
        BigDecimal saldo
) {
}
