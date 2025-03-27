package br.com.coderbank.movimentacaofinanceira.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ContaClienteRequestDTO(
        @NotBlank(message = "O nome do titular deve ser informado.")
        @Size(min = 3, message = "O nome do titular deve ter no mínimo 3 caracteres.")
        String titular,

        @NotBlank(message = "O campo CPF é obrigatório.")
        @CPF(message = "O CPF informado não é válido.")
        String cpf

) {
}
