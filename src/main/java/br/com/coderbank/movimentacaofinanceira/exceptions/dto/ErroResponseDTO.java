package br.com.coderbank.movimentacaofinanceira.exceptions.dto;

import java.time.LocalDateTime;

public record ErroResponseDTO(int status,
                              String mensagem,
                              LocalDateTime timestamp) {
}
