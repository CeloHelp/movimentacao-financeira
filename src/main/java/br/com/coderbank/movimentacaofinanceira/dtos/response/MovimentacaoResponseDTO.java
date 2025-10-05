package br.com.coderbank.movimentacaofinanceira.dtos.response;

import br.com.coderbank.movimentacaofinanceira.entities.entienums.TipoMovimentacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovimentacaoResponseDTO(
        LocalDateTime dataHora,
        TipoMovimentacao tipo,
        BigDecimal valor,
        UUID contaDestino

) {
}
